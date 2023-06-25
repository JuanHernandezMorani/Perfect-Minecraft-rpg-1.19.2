package net.cheto97.rpgcraftmod.util;

import java.util.Random;

public class CriticalDamageModifierCalculator {
    private static final double MAX_AGILITY = 25000000.0;
    private static final float MIN_MODIFIER = 1.5f;
    private static final float MAX_MODIFIER = 10.0f;
    private static final float MIN_VALUE = 1.5f;
    private static final float MAX_VALUE = 8.5f;

    public static float calculateCriticalDamageModifier(double agility) {
        if (agility >= MAX_AGILITY) {
            return MAX_MODIFIER;
        }
        Random random = new Random();

            float modifierRange = generateRandomFloat();
            return (float)(MIN_MODIFIER + modifierRange * random.nextDouble());
    }
    public static float generateRandomFloat() {
        Random random = new Random();
        return MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextFloat();
    }
}
