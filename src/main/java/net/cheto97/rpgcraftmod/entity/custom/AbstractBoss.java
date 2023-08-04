package net.cheto97.rpgcraftmod.entity.custom;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBoss extends Monster implements IAnimatable {
    private final AnimationFactory factory;
    private final ServerBossEvent bossEvent;
    private int ticksInAction = 0;
    private int ticksSinceLastPlayerScan = 0;
    private Action action = Action.IDLE;

    public AbstractBoss(EntityType<? extends AbstractBoss> type, Level level) {
        this(type, level, BossEvent.BossBarColor.PINK);
    }

    public AbstractBoss(EntityType<? extends AbstractBoss> type, Level level, BossEvent.BossBarColor color) {
        super(type, level);
        noCulling = true;
        factory = new AnimationFactory(this);
        bossEvent = new ServerBossEvent(getType().getDescription(), color, BossEvent.BossBarOverlay.PROGRESS);
        if (!level.isClientSide()) {
            for (ServerPlayer player : ((ServerLevel) level).getPlayers(EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.withinDistance(0, 128, 0, 192.0D)))) {
                bossEvent.addPlayer(player);
            }
        }
    }
    public AnimationController<? extends AbstractBoss> createBaseAnimationController(String registryName) {
        return new AnimationController<>(this, "base_controller", 2, e -> {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + registryName + ".base"));
            return PlayState.CONTINUE;
        });
    }

    public AnimationController<? extends AbstractBoss> createActionAnimationController(String registryName, String name, Action action) {
        return new AnimationController<>(this, action.name() + "_controller", 2, e -> {
            if (getAction() == action) {
                e.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + registryName + "." + name));
                return PlayState.CONTINUE;
            } else {
                e.getController().clearAnimationCache();
                return PlayState.STOP;
            }
        });
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (hasCustomName()) {
            bossEvent.setName(getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
        bossEvent.setName(getDisplayName());
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canCollideWith(Entity pEntity) {
        return isAlive();
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    @Override
    public boolean canRide(Entity pEntity) {
        return false;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public float getStepHeight() {
        return 1.02f;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1, 1));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8F));
        goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (bossEvent != null) {
            bossEvent.setProgress(getHealth() / getMaxHealth());
            if (!level.isClientSide()) {
                bossEvent.setVisible(isAlive());
                if (ticksSinceLastPlayerScan++ >= 20) {
                    updatePlayers();
                    ticksSinceLastPlayerScan = 0;
                }
            }
        }
        ticksInAction++;
    }

    @Override
    public void handleEntityEvent(byte pId) {
        for (Action a : Action.values()) {
            if (a.id == pId) {
                action = a;
            }
        }
        super.handleEntityEvent(pId);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof AbstractBoss) return false;
        if (pSource == DamageSource.IN_WALL) {
            if (!level.isClientSide()) {
                int width = Math.round(getBbWidth());
                int height = Math.round(getBbHeight());
                for (int x = -width; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        for (int z = -width; z < width; z++) {
                            level.destroyBlock(new BlockPos(getX() + x, getY() + y, getZ() + z), true, this);
                        }
                    }
                }
            }
            return false;
        }
        SoundEvent sound = getHurtSound(pSource);
        if (sound != null) {
            level.playSound(null, this, sound, SoundSource.HOSTILE, 1f, 0.5f + random.nextFloat() * 0.5f);
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void kill() {
        hurtTime = 10;
        remove(Entity.RemovalReason.KILLED);
        bossEvent.setProgress(0);
        bossEvent.setVisible(false);
        bossEvent.removeAllPlayers();
        updatePlayers();
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    public SoundEvent getAttackSound() {
        return null;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
        ticksInAction = 0;
        level.broadcastEntityEvent(this, action.id);
    }

    public int getTicksInAction() {
        return ticksInAction;
    }

    private void updatePlayers() {
        if (!level.isClientSide()) {
            Set<ServerPlayer> newSet = new HashSet<>();
            for (ServerPlayer player : ((ServerLevel) level).getPlayers(EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.withinDistance(0, 128, 0, 192)))) {
                bossEvent.addPlayer(player);
                startSeenByPlayer(player);
                newSet.add(player);
            }
            Set<ServerPlayer> oldSet = Sets.newHashSet(bossEvent.getPlayers());
            oldSet.removeAll(newSet);
            for (ServerPlayer player : oldSet) {
                bossEvent.removePlayer(player);
                stopSeenByPlayer(player);
            }
        }
    }

    public enum Action {
        IDLE(-1), CAST(-2), LONG_CAST(-3), SMASH(-4), SPIN(-5), STRIKE(-6), THROW(-7);

        public final byte id;

        Action(int id) {
            this.id = (byte) id;
        }
    }
}
