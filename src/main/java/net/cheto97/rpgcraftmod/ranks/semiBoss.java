package net.cheto97.rpgcraftmod.ranks;

import net.cheto97.rpgcraftmod.modsystem.Customlevel;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class semiBoss {
    public static void semiBossModify(LivingEntity entity){
        Random random = new Random();
        float absortion = random.nextFloat()+(random.nextFloat())*random.nextFloat()*10+(float)(entity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1)/30);
        float check = absortion < 1 ? 1.0f : absortion;

        entity.setAbsorptionAmount(check+5.0f);

        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,1000000,1));
        entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,8));
        if (random.nextDouble() <= 0.93456) {
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,8));
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,8));
            entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,8));
            if (random.nextDouble() <= 0.45) {
                entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,10));
                entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,10));
                entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,10));
                entity.addEffect(new MobEffectInstance(getRandomEffect(),1000000,10));
            }
        }
        entity.getDimensions(entity.getPose()).scale(2.0F,2.0F);
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
