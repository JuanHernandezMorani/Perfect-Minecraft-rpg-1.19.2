package net.cheto97.rpgcraftmod.util;

import java.util.Random;

public class RPGutil {
    public static double setBonusValue(int rank) {
        double baseMultiplier = 1.0;
        double rankBonus = getRandomRankBonus(rank);
        double rankModifier = getRandomRankModifier(rank);
        double rankMultiplier = 1.25 * (rank - 1) + rankBonus * rankModifier;
        return baseMultiplier * rankMultiplier;
    }

    private static double getRandomRankBonus(int rank) {
        Random random = new Random();
        double randomRankBonus = 1;
        for(int i = 0; i < rank+1;i++){
            randomRankBonus  = randomRankBonus + rank*(random.nextDouble()+1);
        }
        double maxBonus = rank * 2.4 * (randomRankBonus * rank / 2);
        return random.nextDouble() * maxBonus;
    }

    private static double getRandomRankModifier(int rank) {
        Random random = new Random();
        double randomRankBonus = 1;
        for(int i = 0; i < rank+1;i++){
            randomRankBonus  = randomRankBonus + rank*(random.nextDouble()+1);
        }

        double maxModifier = rank * 0.12 * (randomRankBonus * rank / 2);
        return 1.0 + random.nextDouble() * maxModifier;
    }
}

