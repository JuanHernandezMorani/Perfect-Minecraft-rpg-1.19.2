package net.cheto97.rpgcraftmod.ranks;

import net.cheto97.rpgcraftmod.modsystem.Customlevel;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class Boss {
    public static void bossModify(LivingEntity entity){
        Random random = new Random();
        float absortion = random.nextFloat()+(random.nextFloat())*random.nextFloat()*10+(float)(entity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1)/30);
        float check = absortion < 1 ? 1.0f : absortion;

        entity.setAbsorptionAmount(check+20.0f);

        MobEffect[] effects = {
                MobEffects.MOVEMENT_SPEED,
                MobEffects.DAMAGE_BOOST,
                MobEffects.ABSORPTION,
                MobEffects.HEALTH_BOOST,
                MobEffects.FIRE_RESISTANCE,
                MobEffects.DOLPHINS_GRACE,
                MobEffects.DAMAGE_RESISTANCE
        };

        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,1000000,1));
        entity.addEffect(new MobEffectInstance(effects[0],getRandomEffectDuration(),getRandomEffectAmplified()));
        entity.addEffect(new MobEffectInstance(effects[1],getRandomEffectDuration(),getRandomEffectAmplified()));
        entity.addEffect(new MobEffectInstance(effects[2],getRandomEffectDuration(),getRandomEffectAmplified()));
        entity.addEffect(new MobEffectInstance(effects[3],getRandomEffectDuration(),getRandomEffectAmplified()));
        entity.addEffect(new MobEffectInstance(effects[4],getRandomEffectDuration(),getRandomEffectAmplified()));
        entity.addEffect(new MobEffectInstance(effects[5],getRandomEffectDuration(),getRandomEffectAmplified()));
        entity.addEffect(new MobEffectInstance(effects[6],getRandomEffectDuration(),getRandomEffectAmplified()));

        entity.getDimensions(entity.getPose()).scale(3.5F,3.5F);
    }

    private static int getRandomEffectDuration(){
        return new Random().nextInt(1000000) + 1;
    }

    private static int getRandomEffectAmplified(){
        return new Random().nextInt(89) + 12;
    }
}
