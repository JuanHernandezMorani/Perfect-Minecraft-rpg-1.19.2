package net.cheto97.rpgcraftmod.ranks;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class Demon {
    private static final double EFFECT_CHANCE = 0.39;
    public static void demonModify(LivingEntity entity){
        Random random = new Random();
        entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,3));
        if (random.nextDouble() <= EFFECT_CHANCE) {
            entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,1000000,1));
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,2));
            if (random.nextDouble() <= 0.15) {
                entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,4));
            }
        }
        entity.getDimensions(entity.getPose()).scale(1.19F,1.19F);
    }

    private static MobEffect getRandomEffect() {
        MobEffect[] effects = {
                MobEffects.MOVEMENT_SPEED,
                MobEffects.DAMAGE_BOOST,
                MobEffects.ABSORPTION,
                MobEffects.HEALTH_BOOST,
                MobEffects.FIRE_RESISTANCE,
                MobEffects.DOLPHINS_GRACE,
                MobEffects.DAMAGE_RESISTANCE
        };

        Random random = new Random();
        int index = random.nextInt(effects.length);
        return effects[index];
    }

}
