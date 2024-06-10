package net.cheto97.rpgcraftmod.util;

import java.util.HashMap;
import java.util.Map;

public class ColorUtil {

    private static final Map<Integer, String> colorNameMap = new HashMap<>();

    static {
        colorNameMap.put(0x000000, "Black");
        colorNameMap.put(0x0000FF, "Blue");
        colorNameMap.put(0x3357FF, "Pale Blue");
        colorNameMap.put(0xA52A2A, "Brown");
        colorNameMap.put(0x00FFFF, "Cyan");
        colorNameMap.put(0x008000, "Green");
        colorNameMap.put(0xB22222, "Firebrick");
        colorNameMap.put(0xFFD700, "Gold");
        colorNameMap.put(0x808080, "Gray");
        colorNameMap.put(0x00FF00, "Smooth Green");
        colorNameMap.put(0x33FF57, "Green Lime");
        colorNameMap.put(0xFFFFE0, "Light Yellow");
        colorNameMap.put(0xFF00FF, "Magenta");
        colorNameMap.put(0x800000, "Maroon");
        colorNameMap.put(0x000080, "Navy");
        colorNameMap.put(0x808000, "Olive");
        colorNameMap.put(0xFFA500, "Orange");
        colorNameMap.put(0xFF4500, "Orange Red");
        colorNameMap.put(0xDA70D6, "Orchid");
        colorNameMap.put(0xEEE8AA, "Pale Goldenrod");
        colorNameMap.put(0x98FB98, "Pale Green");
        colorNameMap.put(0xAFEEEE, "Pale Turquoise");
        colorNameMap.put(0xDB7093, "Pale Violet Red");
        colorNameMap.put(0xFFEFD5, "Papaya Whip");
        colorNameMap.put(0xFFDAB9, "Peach Puff");
        colorNameMap.put(0xCD853F, "Peru");
        colorNameMap.put(0xFFC0CB, "Pink");
        colorNameMap.put(0xDDA0DD, "Plum");
        colorNameMap.put(0xB0E0E6, "Powder Blue");
        colorNameMap.put(0x800080, "Purple");
        colorNameMap.put(0xFF0000, "Red");
        colorNameMap.put(0xFF5733, "Red Orange");
        colorNameMap.put(0xBC8F8F, "Rosy Brown");
        colorNameMap.put(0x4169E1, "Royal Blue");
        colorNameMap.put(0x8B4513, "Saddle Brown");
        colorNameMap.put(0xFA8072, "Salmon");
        colorNameMap.put(0xF4A460, "Sandy Brown");
        colorNameMap.put(0x2E8B57, "Sea Green");
        colorNameMap.put(0xFFF5EE, "Seashell");
        colorNameMap.put(0xA0522D, "Sienna");
        colorNameMap.put(0xC0C0C0, "Silver");
        colorNameMap.put(0x87CEEB, "Sky Blue");
        colorNameMap.put(0x6A5ACD, "Slate Blue");
        colorNameMap.put(0x708090, "Slate Gray");
        colorNameMap.put(0xFFFAFA, "Snow");
        colorNameMap.put(0x00FF7F, "Spring Green");
        colorNameMap.put(0x4682B4, "Steel Blue");
        colorNameMap.put(0xD2B48C, "Tan");
        colorNameMap.put(0x008080, "Teal");
        colorNameMap.put(0xD8BFD8, "Thistle");
        colorNameMap.put(0xFF6347, "Tomato");
        colorNameMap.put(0x40E0D0, "Turquoise");
        colorNameMap.put(0xEE82EE, "Violet");
        colorNameMap.put(0xF5DEB3, "Wheat");
        colorNameMap.put(0xF5F5F5, "White Smoke");
        colorNameMap.put(0xFFFFFF, "White");
        colorNameMap.put(0xFFFF00, "Yellow");
        colorNameMap.put(0x9ACD32, "Yellow Green");
    }

    public static String getColorName(Integer color) {
        return colorNameMap.getOrDefault(color, "Other Color");
    }
}