package net.cheto97.rpgcraftmod.util;

import java.util.Random;

import static net.cheto97.rpgcraftmod.util.NumberUtils.randomDouble;

public class AuraDropChances {
    public static boolean willDropAura(double probability) {
        double probabilityDecimal = probability / 100.0;

        Random random = new Random();

        double randomValue1 = random.nextDouble();
        double randomValue2 = random.nextDouble();
        double randomValue3 = random.nextDouble();

        double additionalComplexity = randomValue1 * randomValue2 * randomValue3;

        return (probabilityDecimal + additionalComplexity) <= probabilityDecimal + 0.15;
    }

    public static double generateRandomValue(int number) {

        Random random = new Random();
        double randomValue = random.nextDouble();

        double result;
        if (number >= 11) {
            result = randomDouble((0.0000001*randomValue*3*(randomValue*0.25+(45*randomDouble(randomValue,randomValue*0.143)))),0.0000001);
        } else {
            double factor = 1.0 - (number / 11.0);
            result = factor * 35.0;
        }

        if (number > 7 && result > 7.552) {
            result = randomDouble(7.552,0.001);
        }

        return result;
    }
}
