package net.cheto97.rpgcraftmod.util;

import java.util.Random;

public class RPGutil {
    private static final int MAX_RANK = 11;

    public static double setBonusValue(int rank) {
        double baseMultiplier = 1.0;
        double rankBonus = getRandomRankBonus(rank);
        double rankModifier = getRandomRankModifier(rank);
        double rankMultiplier = 1.25 * (rank - 1) + rankBonus * rankModifier;
        return baseMultiplier * rankMultiplier;
    }

    private static double getRandomRankBonus(int rank) {
        Random random = new Random();
        double maxBonus = (double) (MAX_RANK - rank + 1) * 2.4;
        return random.nextDouble() * maxBonus;
    }

    private static double getRandomRankModifier(int rank) {
        Random random = new Random();
        double maxModifier = (double) (MAX_RANK - rank + 1) * 0.12;
        return 1.0 + random.nextDouble() * maxModifier;
    }
}

