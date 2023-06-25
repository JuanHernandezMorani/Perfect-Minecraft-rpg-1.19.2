package net.cheto97.rpgcraftmod.util;

import java.util.Random;

public class CriticalHitCalculator {
    private static final double MAX_LUCK = 200_000.0;

    public static boolean calculateCriticalHit(double luck) {
        if (luck >= MAX_LUCK) {
            return true;
        }

        double criticalChance = luck / MAX_LUCK;

        Random random = new Random();
        double randomValue = random.nextDouble();

        return randomValue < criticalChance;
    }
}
