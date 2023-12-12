package net.cheto97.rpgcraftmod.entity.custom;

import net.cheto97.rpgcraftmod.entity.custom.prefabs.DrakeEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DrakeV2Entity extends DrakeEntity {

    public DrakeV2Entity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
}
