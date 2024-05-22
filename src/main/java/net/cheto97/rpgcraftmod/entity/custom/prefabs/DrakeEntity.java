package net.cheto97.rpgcraftmod.entity.custom.prefabs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DrakeEntity extends RPGEntity{

    public DrakeEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
            return Monster.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, 40.0D)
                    .add(Attributes.ATTACK_DAMAGE, 12.0f)
                    .add(Attributes.ATTACK_SPEED, 2.0f)
                    .add(Attributes.ATTACK_KNOCKBACK,0.1D)
                    .add(Attributes.ARMOR,8.0D)
                    .add(Attributes.ARMOR_TOUGHNESS,3.0D)
                    .add(Attributes.LUCK,5.0D)
                    .add(Attributes.KNOCKBACK_RESISTANCE, 9.0D)
                    .add(Attributes.MOVEMENT_SPEED, 0.15D)
                    .build();
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected String getWalkAnimation() {
        return "animation.walk";
    }

    @Override
    protected String getIdleAnimation() {
        return "animation.idle";
    }

    @Override
    protected String getAttackAnimation(){return "animation.attack";}

    @Override
    protected String getDeathAnimation() {
        return "animation.sit";
    }
}
