package net.cheto97.rpgcraftmod.util;

public class MouseUtil {
    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y) {
        return isMouseOver(mouseX, mouseY, x, y, 16);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int size) {
        return isMouseOver(mouseX, mouseY, x, y, size, size);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int xMin, int yMin, int xMax, int yMax) {
        return (mouseX >= xMin && mouseX <= xMin + xMax) && (mouseY >= yMin && mouseY <= yMin + yMax);
    }
}
