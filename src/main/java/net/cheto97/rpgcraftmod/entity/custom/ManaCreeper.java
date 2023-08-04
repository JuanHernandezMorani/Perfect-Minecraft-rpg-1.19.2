package net.cheto97.rpgcraftmod.entity.custom;

import net.cheto97.rpgcraftmod.util.AM.AMEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class ManaCreeper extends Creeper {
    public ManaCreeper(EntityType<? extends Creeper> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
    }


    @Override
    public boolean canDropMobsSkull() {
        return true;
    }

    @Override
    public void tick() {
        if(this.isIgnited() && this.isDeadOrDying() && !this.level.isClientSide()){
            summonManaVortex();
        }
    }

    protected void summonManaVortex() {
        ManaVortex entity = Objects.requireNonNull(AMEntities.MANA_VORTEX.get().create(level));
        entity.moveTo(position());
        level.addFreshEntity(entity);
    }

}
