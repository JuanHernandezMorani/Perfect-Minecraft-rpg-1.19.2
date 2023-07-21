package net.cheto97.rpgcraftmod.util.Effects;

import net.minecraft.world.effect.MobEffectInstance;

public class Helper {
    public static double calculateValue(MobEffectInstance effectInstance, double statValue,String str){
        int amplifier = effectInstance.isAmbient() ? effectInstance.getAmplifier() + 1 : effectInstance.getAmplifier();

        return str.equalsIgnoreCase("add") ? (statValue * 0.0045) * amplifier : (statValue * 0.003901960784313725) * amplifier;
    }
    public static double calculateDamageAndReduce(MobEffectInstance damageBoost,MobEffectInstance damageReduce, double statValue){
        int amplifierBoost = damageBoost.getAmplifier();
        int amplifierReduce = damageReduce.getAmplifier();

        return ((statValue * 0.0045) * amplifierBoost) - ((statValue * 0.003901960784313725) * amplifierReduce);
    }
}
