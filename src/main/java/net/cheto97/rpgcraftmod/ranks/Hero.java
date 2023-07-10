package net.cheto97.rpgcraftmod.ranks;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class Hero {
    private static final double EFFECT_CHANCE = 0.25;
    public static void heroModify(LivingEntity entity){
        Random random = new Random();
        entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,2));
        if (random.nextDouble() <= EFFECT_CHANCE) {
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,2));
            if (random.nextDouble() <= 0.1) {
                entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,1000000,1));
                entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,3));
            }
        }
        entity.getDimensions(entity.getPose()).scale(1.13F,1.13F);
    }

    private static MobEffect getRandomEffect() {
        MobEffect[] effects = {
                MobEffects.MOVEMENT_SPEED,
                MobEffects.DAMAGE_BOOST,
                MobEffects.ABSORPTION,
                MobEffects.HEALTH_BOOST,
                MobEffects.FIRE_RESISTANCE,
                MobEffects.DOLPHINS_GRACE
        };

        Random random = new Random();
        int index = random.nextInt(effects.length);
        return effects[index];
    }
}
