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
    public static String doubleToIntString(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        decimalFormat.setMaximumFractionDigits(0);
        decimalFormat.setMinimumFractionDigits(0);
        decimalFormat.setGroupingUsed(false);
        return decimalFormat.format(number).replace(".", ",");
    }
    public static String defTogether(double magicArmor, double physicalArmor) {
        double sumaTotal = magicArmor + physicalArmor;
        double percentageMagic = (magicArmor / sumaTotal) * 100;
        double percentagePhysic = (physicalArmor / sumaTotal) * 100;
        return doubleToIntString(sumaTotal)+" ("+ doubleToString(percentagePhysic)+"% phy/"+ doubleToString(percentageMagic)+"% mag)";
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
    public static int randomIntWithLuck(int maxValue, int minValue, double luck) {
        Random random = new Random();
        int amount = minValue;
        double rankFactor = (double) (maxValue - minValue) / maxValue;
        double[] probabilities = new double[maxValue];
        for (int i = 0; i < maxValue; i++) {
            double baseProbability = (double) (i + 1) / (maxValue - 1);
            double luckModifier = Math.pow(luck, (double) (maxValue - i) / maxValue);
            probabilities[i] = baseProbability * rankFactor * luckModifier;
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
    public static double randomDoubleWithLuck(double maxValue, double minValue, double luck) {
        Random random = new Random();
        double amount = minValue;
        double rankFactor = (maxValue - minValue) / maxValue;
        double[] probabilities = new double[(int) maxValue];
        for (int i = 0; i < maxValue; i++) {
            double baseProbability = (i + 1) / (maxValue - 1);
            double luckModifier = Math.pow(luck, (maxValue - i) / maxValue);
            probabilities[i] = baseProbability * rankFactor * luckModifier;
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
    public static double randomDouble(double maxValue,double minValue){
        Random random = new Random();
        double amount = minValue;
        double rankFactor = (maxValue - minValue) / maxValue;
        int max = (int) Math.round(maxValue) + 1;
        double[] probabilities = new double[max];
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
    public static int convertDoubleToInt(double number) {
        return (int) number;
    }
    public static double randomValueWithConstraints(double rankMultiplier, int rank) {
        double minValue = rank * 0.65;
        double maxValue = (((rank * rank) / rankMultiplier) + 1) / 3;
        double equationResult = randomEquation(rank);
        double result = equationResult * rankMultiplier * rank + 1.001 * rank;
        result = Math.max(result, minValue);
        result = Math.min(result, maxValue);
        return result;
    }
    private static double randomEquation(int rank) {
        Random random = new Random();
        double a = random.nextDouble() * 2 - 1;
        double b = random.nextDouble() * 2 - 1;
        double c = random.nextDouble() * 2 - 1;
        double x = random.nextDouble() * rank;
        return a * Math.pow(x, 2) + b * x + c;
    }
}
