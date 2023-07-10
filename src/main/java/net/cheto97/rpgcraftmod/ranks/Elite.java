package net.cheto97.rpgcraftmod.ranks;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.Random;

public class Elite {
    private static final double ELITE_EFFECT_CHANCE = 0.05;

    public static void eliteModify(LivingEntity entity) {
        Random random = new Random();
        if (random.nextDouble() <= ELITE_EFFECT_CHANCE) {
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,1));
        }
        entity.getDimensions(entity.getPose()).scale(1.03F,1.03F);
    }

    private static MobEffect getRandomEffect() {
        MobEffect[] effects = {
                MobEffects.MOVEMENT_SPEED,
                MobEffects.DAMAGE_BOOST,
                MobEffects.ABSORPTION,
                MobEffects.HEALTH_BOOST
        };

        Random random = new Random();
        int index = random.nextInt(effects.length);
        return effects[index];
    }
}
