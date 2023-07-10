package net.cheto97.rpgcraftmod.ranks;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class Champion {
    private static final double EFFECT_CHANCE = 0.2;

    public static void championModify(LivingEntity entity){
        Random random = new Random();
        if (random.nextDouble() <= EFFECT_CHANCE) {
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,2));
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,1));
        }
        entity.getDimensions(entity.getPose()).scale(1.09F,1.09F);
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
