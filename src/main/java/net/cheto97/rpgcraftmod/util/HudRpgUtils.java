package net.cheto97.rpgcraftmod.util;

import net.minecraft.world.entity.player.Player;

import java.util.Locale;

public class HudRpgUtils {
    private static final int FPS_MIN_COLOR = 0xA90011;
    private static final int FPS_MID_COLOR = 0xC9C908;
    private static final int FPS_MAX_COLOR = 0x01A905;

    public static int getPosition(double direction, int position) {
        return (int) Math.floor((direction / 100) * position);
    }

    public static int getPositionX(int direction, int position) {
        return (direction / 2) + ((direction / 100) * position);
    }
    public static int getPositionY(int direction, int position) {
        return (direction / 100) * position;
    }

    public static int[] getPlayerPosition(Player player) {
        int[] pos = new int[]{0,0,0};
        if(player != null){
            pos[0] = (int) player.getX();
            pos[1] = (int) player.getY();
            pos[2] = (int) player.getZ();
        }
        return pos;
    }

    public static int getFPSColor(String fps){
        int fpsAmount = Math.min(Math.max(stringToInt(fps),0), 60);

        return calculateColor(fpsAmount);
    }
    private static int interpolateColor(int color1, int color2, int ratio) {
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        int r = r1 + (r2 - r1) * ratio;
        int g = g1 + (g2 - g1) * ratio;
        int b = b1 + (b2 - b1) * ratio;

        return (r << 16) | (g << 8) | b;
    }
    private static int calculateColor(int fps) {
        if (fps <= 0) {
            return FPS_MIN_COLOR;
        } else if (fps <= 30) {
            int ratio = fps / 30;
            return interpolateColor(FPS_MIN_COLOR, FPS_MID_COLOR, ratio);
        } else if (fps <= 60) {
            int ratio = (fps - 30) / 30;
            return interpolateColor(FPS_MID_COLOR, FPS_MAX_COLOR, ratio);
        } else {
            return FPS_MAX_COLOR;
        }
    }
    private static int stringToInt(String fps){
        try {
            return Integer.parseInt(fps);
        }
        catch (NumberFormatException e) {
            System.err.println("Error: invalid numeric string.");
            return -1;
        }
    }
    public static String getHexString(int value){
       String hex = Integer.toHexString(value);
       return "0x" + hex.toUpperCase(Locale.ROOT);
    }
    public int getLinealColors(int position){
        int fpsColor = 0xFFFFFF;
        switch (position){
            case 0 ->  fpsColor = 0xA90011;
            case 1 ->  fpsColor = 0xAD060F;
            case 2 ->  fpsColor = 0xB10C0D;
            case 3 ->  fpsColor = 0xB5120B;
            case 4 ->  fpsColor = 0xB91809;
            case 5 ->  fpsColor = 0xBD1E07;
            case 6 ->  fpsColor = 0xC12405;
            case 7 ->  fpsColor = 0xC52A03;
            case 8 ->  fpsColor = 0xC93001;
            case 9 ->  fpsColor = 0xCC3500;
            case 10 ->  fpsColor = 0xCD3C00;
            case 11 ->  fpsColor = 0xCD4300;
            case 12 ->  fpsColor = 0xCD4A00;
            case 13 ->  fpsColor = 0xCD5100;
            case 14 ->  fpsColor = 0xCD5800;
            case 15 ->  fpsColor = 0xCD5F00;
            case 16 ->  fpsColor = 0xCD6600;
            case 17 ->  fpsColor = 0xCD6D00;
            case 18 ->  fpsColor = 0xCD7400;
            case 19 ->  fpsColor = 0xCD7B00;
            case 20 ->  fpsColor = 0xCD8200;
            case 21 ->  fpsColor = 0xCD8900;
            case 22 ->  fpsColor = 0xCD9000;
            case 23 ->  fpsColor = 0xCD9700;
            case 24 ->  fpsColor = 0xCD9E00;
            case 25 ->  fpsColor = 0xCDA500;
            case 26 ->  fpsColor = 0xCDAC00;
            case 27 ->  fpsColor = 0xCDB300;
            case 28 ->  fpsColor = 0xCDBA00;
            case 29 ->  fpsColor = 0xCDC100;
            case 30 ->  fpsColor = 0xC9C908;
            case 31 ->  fpsColor = 0xC5C808;
            case 32 ->  fpsColor = 0xC0C708;
            case 33 ->  fpsColor = 0xBBC608;
            case 34 ->  fpsColor = 0xB7C508;
            case 35 ->  fpsColor = 0xB2C408;
            case 36 ->  fpsColor = 0xADC308;
            case 37 ->  fpsColor = 0xA8C208;
            case 38 ->  fpsColor = 0xA4C108;
            case 39 ->  fpsColor = 0x9FC008;
            case 40 ->  fpsColor = 0x9ABF08;
            case 41 ->  fpsColor = 0x96BE08;
            case 42 ->  fpsColor = 0x91BD08;
            case 43 ->  fpsColor = 0x8CBC08;
            case 44 ->  fpsColor = 0x87BB08;
            case 45 ->  fpsColor = 0x83BA08;
            case 46 ->  fpsColor = 0x7EB908;
            case 47 ->  fpsColor = 0x79B808;
            case 48 ->  fpsColor = 0x75B708;
            case 49 ->  fpsColor = 0x70B608;
            case 50 ->  fpsColor = 0x6BB508;
            case 51 ->  fpsColor = 0x66B408;
            case 52 ->  fpsColor = 0x62B308;
            case 53 ->  fpsColor = 0x5DB208;
            case 54 ->  fpsColor = 0x58B108;
            case 55 ->  fpsColor = 0x54B008;
            case 56 ->  fpsColor = 0x4FAF08;
            case 57 ->  fpsColor = 0x4AAE08;
            case 58 ->  fpsColor = 0x45AD08;
            case 59 ->  fpsColor = 0x41AC08;
            case 60 ->  fpsColor = 0x01A905;
        }
        return fpsColor;
    }
}