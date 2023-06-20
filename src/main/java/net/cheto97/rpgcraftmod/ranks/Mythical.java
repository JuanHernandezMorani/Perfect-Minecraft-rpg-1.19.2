package net.cheto97.rpgcraftmod.ranks;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class Mythical {
    private static final double EFFECT_CHANCE = 0.65;
    public static void mythicalModify(LivingEntity entity){
        Random random = new Random();
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,1000000,1));
        entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,5));
        if (random.nextDouble() <= EFFECT_CHANCE) {
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,4));
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,4));
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,4));
            if (random.nextDouble() <= 0.45) {
                entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,6));
                entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,4));
            }
        }
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
