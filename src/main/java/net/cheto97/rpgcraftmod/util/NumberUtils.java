package net.cheto97.rpgcraftmod.util;

import java.text.DecimalFormat;

public class NumberUtils {
    public static String doubleToString(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(0);
        decimalFormat.setGroupingUsed(false);

        return decimalFormat.format(number).replace(".", ",");
    }
}
