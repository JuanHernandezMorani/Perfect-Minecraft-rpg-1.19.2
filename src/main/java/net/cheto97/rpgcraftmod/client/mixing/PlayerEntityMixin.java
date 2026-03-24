package net.cheto97.rpgcraftmod.client.mixing;

import net.cheto97.rpgcraftmod.util.SlowFallEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements SlowFallEntity {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Unique
    public boolean slowFalling = false;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void setSlowFalling(boolean slowFalling) {
        this.slowFalling = slowFalling;
    }

    @Override
    public boolean isSlowFalling() {
        return slowFalling;
    }
}
