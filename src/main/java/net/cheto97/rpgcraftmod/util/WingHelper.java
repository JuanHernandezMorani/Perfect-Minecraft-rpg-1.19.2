package net.cheto97.rpgcraftmod.util;

import net.cheto97.rpgcraftmod.item.prefabs.AbstractWingCurioItem;
import net.cheto97.rpgcraftmod.item.prefabs.WingDefinition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class WingHelper {
    private static final String WING_DATA = "RpgcraftWingData";
    private static final String LOOP_TICKS = "LoopTicks";
    private static final String WAS_DIVING = "WasDiving";

    public static void tickWingFlight(Player player, ItemStack stack, WingDefinition definition, SlotContext slotContext) {
        if (!(stack.getItem() instanceof AbstractWingCurioItem wingItem) || !wingItem.isUsable(stack, player)) {
            stopFlying(player, stack);
            return;
        }

        if (isHardStopCondition(player)) {
            stopFlying(player, stack);
            return;
        }

        if (!player.isFallFlying() && canStartFlight(player)) {
            player.startFallFlying();
        }

        if (player.isFallFlying()) {
            applyControlledFlight(player, stack, definition.flightTuning());
            damageWing(player, stack, definition.flightTuning(), slotContext);
        } else if (!player.isOnGround()) {
            applySlowFalling(player);
            stack.getOrCreateTag().putString(AbstractWingCurioItem.ANIM_STATE_TAG, "idle");
        }
    }

    private static void applyControlledFlight(Player player, ItemStack stack, WingDefinition.FlightTuning tuning) {
        if (player.hasEffect(MobEffects.SLOW_FALLING)) {
            player.removeEffect(MobEffects.SLOW_FALLING);
        }

        Vec3 look = player.getLookAngle();
        Vec3 horizontalLook = new Vec3(look.x, 0.0D, look.z);
        if (horizontalLook.lengthSqr() < 1.0E-4D) {
            horizontalLook = new Vec3(0, 0, 1);
        }
        horizontalLook = horizontalLook.normalize();

        Vec3 velocity = player.getDeltaMovement();
        double horizontalSpeed = new Vec3(velocity.x, 0, velocity.z).length();
        double desiredHorizontalSpeed = Math.min(tuning.targetHorizontalSpeed(), tuning.maxHorizontalSpeed());

        boolean boosting = player.isSprinting() && player.getXRot() < -10;
        if (boosting) {
            desiredHorizontalSpeed = Math.min(tuning.maxHorizontalSpeed(), desiredHorizontalSpeed + 0.08D);
        }

        Vec3 target = horizontalLook.scale(desiredHorizontalSpeed);
        double targetY = clamp(-look.y * tuning.verticalControl(), -0.16D, 0.20D);

        Vec3 adjusted = new Vec3(
                lerp(velocity.x, target.x, tuning.acceleration() + (boosting ? tuning.boostAcceleration() : 0)),
                lerp(velocity.y, targetY, 0.12D),
                lerp(velocity.z, target.z, tuning.acceleration() + (boosting ? tuning.boostAcceleration() : 0))
        ).scale(tuning.drag());

        int loopTicks = updateLoopState(player, tuning.loopTicks(), horizontalSpeed);
        if (loopTicks > 0) {
            adjusted = adjusted.add(0, 0.11D, 0);
            stack.getOrCreateTag().putString(AbstractWingCurioItem.ANIM_STATE_TAG, "loop");
        } else if (boosting) {
            stack.getOrCreateTag().putString(AbstractWingCurioItem.ANIM_STATE_TAG, "boost");
        } else {
            stack.getOrCreateTag().putString(AbstractWingCurioItem.ANIM_STATE_TAG, "flight");
        }

        double cappedHorizontal = Math.min(tuning.maxHorizontalSpeed(), new Vec3(adjusted.x, 0, adjusted.z).length());
        Vec3 cappedHorizontalVec = new Vec3(adjusted.x, 0, adjusted.z).normalize().scale(cappedHorizontal);
        if (Double.isNaN(cappedHorizontalVec.x)) {
            cappedHorizontalVec = Vec3.ZERO;
        }

        player.setDeltaMovement(cappedHorizontalVec.x, clamp(adjusted.y, -0.55D, 0.38D), cappedHorizontalVec.z);
        player.fallDistance = 0;
    }

    private static int updateLoopState(Player player, int defaultLoopTicks, double horizontalSpeed) {
        CompoundTag data = player.getPersistentData().getCompound(WING_DATA);
        int loopTicks = data.getInt(LOOP_TICKS);

        boolean wasDiving = data.getBoolean(WAS_DIVING);
        float pitch = player.getXRot();
        if (pitch > 55) {
            wasDiving = true;
        }

        if (wasDiving && pitch < -55 && horizontalSpeed > 0.28D && loopTicks <= 0) {
            loopTicks = defaultLoopTicks;
            wasDiving = false;
        }

        if (loopTicks > 0) {
            loopTicks--;
        }

        data.putInt(LOOP_TICKS, loopTicks);
        data.putBoolean(WAS_DIVING, wasDiving);
        player.getPersistentData().put(WING_DATA, data);
        return loopTicks;
    }

    private static void damageWing(Player player, ItemStack stack, WingDefinition.FlightTuning tuning, SlotContext slotContext) {
        if (player.level.isClientSide || player.tickCount % tuning.durabilityTickInterval() != 0) {
            return;
        }

        stack.hurtAndBreak(1, player, p -> {
            if (p instanceof ServerPlayer serverPlayer) {
                serverPlayer.broadcastBreakEvent(serverPlayer.getUsedItemHand());
            }
        });

        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            stopFlying(player, stack);
        }
    }

    private static void applySlowFalling(Player player) {
        if (!player.hasEffect(MobEffects.SLOW_FALLING)) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 10, 0, false, false, false));
        }
    }

    public static void stopFlying(Player player, ItemStack stack) {
        if (player.hasEffect(MobEffects.SLOW_FALLING) && player.isOnGround()) {
            player.removeEffect(MobEffects.SLOW_FALLING);
        }

        if (player.isFallFlying()) {
            player.stopFallFlying();
        }

        stack.getOrCreateTag().putString(AbstractWingCurioItem.ANIM_STATE_TAG, "idle");

        CompoundTag data = player.getPersistentData().getCompound(WING_DATA);
        data.putInt(LOOP_TICKS, 0);
        data.putBoolean(WAS_DIVING, false);
        player.getPersistentData().put(WING_DATA, data);
    }

    private static boolean isHardStopCondition(Player player) {
        return player.isOnGround() || player.isInWater() || player.isUnderWater() || player.hasEffect(MobEffects.LEVITATION) || player.isPassenger();
    }

    private static boolean canStartFlight(Player player) {
        return !player.isOnGround()
                && !player.isInWater()
                && !player.hasEffect(MobEffects.LEVITATION)
                && player.fallDistance > 0.08F;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static double lerp(double from, double to, double amount) {
        return from + (to - from) * amount;
    }
}
