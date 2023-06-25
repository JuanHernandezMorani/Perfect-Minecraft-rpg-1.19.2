package net.cheto97.rpgcraftmod.util;

import java.util.Random;

public class TierSelector {
    public static int selectTier(double inversionFactor) {
        double[] tierPercentages = {0.4, 0.25, 0.15, 0.09, 0.05, 0.03, 0.02, 0.01, 0.005, 0.0005, 0.00005};

        double totalPercentage = 0.0;
        for (double percentage : tierPercentages) {
            totalPercentage += percentage;
        }

        double maxInversionFactor = 10000.0;
        double invertedPercentage = totalPercentage * (maxInversionFactor - inversionFactor) / maxInversionFactor;

        Random random = new Random();
        double randomNumber = random.nextDouble() * (totalPercentage + invertedPercentage);

        double cumulativePercentage = 0.0;
        int selectedTier = 1;
        for (int i = 0; i < tierPercentages.length; i++) {
            cumulativePercentage += tierPercentages[i];
            if (randomNumber < cumulativePercentage) {
                selectedTier = i + 1;
                break;
            }
        }
        return selectedTier;
    }
}
