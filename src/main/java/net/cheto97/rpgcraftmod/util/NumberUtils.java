package net.cheto97.rpgcraftmod.util;

import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtils {
    public static String doubleToString(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(0);
        decimalFormat.setGroupingUsed(false);

        return decimalFormat.format(number).replace(".", ",");
    }

    public static int randomInt(int maxValue,int minValue){

        Random random = new Random();

        int amount = minValue;

        double rankFactor = (double)(maxValue - minValue) / maxValue;

        double[] probabilities = new double[maxValue];
        for (int i = 0; i < maxValue; i++) {
            double baseProbability = (double) (i + 1) / (maxValue-1);
            probabilities[i] = baseProbability * rankFactor;
        }

        double totalProbability = 0.0;
        for (double probability : probabilities) {
            totalProbability += probability;
        }

        double randomNumber = random.nextDouble() * totalProbability;

        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomNumber < cumulativeProbability) {
                amount = i;
                break;
            }
        }

        return amount;

    }
}
