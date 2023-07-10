package net.cheto97.rpgcraftmod.client.mixing;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.util.SlowFallEntity;
import net.cheto97.rpgcraftmod.util.WingHelper;
import net.cheto97.rpgcraftmod.util.levelConfig.utils.WingConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements SlowFallEntity {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Unique public boolean slowFalling = false;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        if(RpgcraftMod.WINGS.apply(this).canFly()) {
            if(isFallFlying()) {
                if(flyingSpeed > 0 && getBlockY() - getAverageHeight() <= 64)
                    WingHelper.applySpeed((Player) (Object) this);
                if((WingConfig.canSlowFall && isCrouching()) || isUnderWater())
                    WingHelper.stopFlying((Player) (Object) this);
            }
            else {
                if(isOnGround() || isInFluidType())
                    setSlowFalling(false);

                if(isSlowFalling()) {
                    fallDistance = 0F;
                    setDeltaMovement(getDeltaMovement().x, -0.4, getDeltaMovement().z);
                }
            }
        }
    }

    @Override
    public void setSlowFalling(boolean slowFalling) {
        this.slowFalling = slowFalling;
    }

    @Override
    public boolean isSlowFalling() {
        return slowFalling;
    }

    @Unique
    public int getAverageHeight() {
        int averageHeight = 0;
        int radius = 2;
        int diameter = (radius * 2) + 1;

        for(int x = -radius; x <= radius; x++)
            for(int z = -radius; z <= radius; z++)
                averageHeight += level.getHeight(Heightmap.Types.MOTION_BLOCKING, getBlockX() + x, getBlockZ() + z);

        return averageHeight / (diameter * diameter);
    }
}