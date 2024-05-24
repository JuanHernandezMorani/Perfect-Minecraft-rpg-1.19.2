package net.cheto97.rpgcraftmod.entity.custom;

import net.cheto97.rpgcraftmod.entity.custom.prefabs.RPGEntityAgro;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class KoboldWarriorEntity extends RPGEntityAgro {
    public KoboldWarriorEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 0.75f)
                .add(Attributes.ATTACK_KNOCKBACK,0.75D)
                .add(Attributes.ARMOR,8.0D)
                .add(Attributes.ARMOR_TOUGHNESS,5.0D)
                .add(Attributes.LUCK,1.68D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.06D)
                .build();
    }

    @Override
    protected String getWalkAnimation() {
        return "walk";
    }

    @Override
    protected String getIdleAnimation() {
        return "idle";
    }

    @Override
    protected String getDeathAnimation() {
        return null;
    }

    @Override
    protected String getAttackAnimation() {
        return "attack";
    }
}
