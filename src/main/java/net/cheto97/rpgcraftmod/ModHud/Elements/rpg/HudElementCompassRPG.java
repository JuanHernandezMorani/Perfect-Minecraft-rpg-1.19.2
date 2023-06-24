package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.Elements.vanilla.HudElementCompassVanilla;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;

public class HudElementCompassRPG extends HudElementCompassVanilla {

    public HudElementCompassRPG() {
        super();
    }

    @Override
    public boolean checkConditions() {
        return true;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int posX = getPosX(scaledWidth);
        int posY = getPosY(scaledHeight);
        int swapSides = this.settings.getBoolValue(Settings.invert_compass) ? -1 : 1;

        int rotation = Math.round(((this.mc.gameRenderer.getMainCamera().getYRot() % 360) / 360) * 200);
        if (rotation < 0)
            rotation = 200 + rotation;

        if (rotation > 0 && rotation <= 100) {
            Gui.drawCenteredString(ms, this.mc.font, "W", posX + (50 * swapSides) - (rotation * swapSides), posY + 1, -1);
        }

        if (rotation > 25 && rotation <= 125) {
            Gui.drawCenteredString(ms, this.mc.font, ".", posX + (75 * swapSides) - (rotation * swapSides), posY - 2, -1);
        }

        if (rotation > 50 && rotation <= 150) {
            Gui.drawCenteredString(ms, this.mc.font, "N", posX + (100 * swapSides) - (rotation * swapSides), posY + 1, this.settings.getBoolValue(Settings.enable_compass_color) ? 0xE60909 : -1);
        }

        if (rotation > 75 && rotation <= 175) {
            Gui.drawCenteredString(ms, this.mc.font, ".", posX + (125 * swapSides) - (rotation * swapSides), posY - 2, -1);
        }

        if (rotation > 100 && rotation <= 200) {
            Gui.drawCenteredString(ms, this.mc.font, "E", posX + (150 * swapSides) - (rotation * swapSides), posY + 1, -1);
        }

        if (rotation >= 125) {
            Gui.drawCenteredString(ms, this.mc.font, ".", posX + (175 * swapSides) - (rotation * swapSides), posY - 2, -1);
        } else if (rotation <= 25) {
            Gui.drawCenteredString(ms, this.mc.font, ".", posX - (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
        }

        if (rotation >= 150) {
            Gui.drawCenteredString(ms, this.mc.font, "S", posX + (200 * swapSides) - (rotation * swapSides), posY + 1, -1);
        } else if (rotation <= 50) {
            Gui.drawCenteredString(ms, this.mc.font, "S", posX - (rotation * swapSides), posY + 1, -1);
        }

        if (rotation >= 175) {
            Gui.drawCenteredString(ms, this.mc.font, ".", posX + (225 * swapSides) - (rotation * swapSides), posY - 2, -1);
        } else if (rotation <= 75) {
            Gui.drawCenteredString(ms, this.mc.font, ".", posX + (25 * swapSides) - (rotation * swapSides), posY - 2, -1);
        }

        if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
            int[] pos = getPlayerPos();
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawString(ms, this.mc.font, String.valueOf(pos[0]), (posX - 48) * 2, (posY + 12) * 2, -1);
            Gui.drawCenteredString(ms, this.mc.font, String.valueOf(pos[1]), posX * 2, (posY + 12) * 2, -1);
            Gui.drawString(ms, this.mc.font, String.valueOf(pos[2]), (posX + 48) * 2 - this.mc.font.width(String.valueOf(pos[2])), (posY + 12) * 2, -1);
            ms.scale(2f, 2f, 2f);
        }
    }

    @Override
    public int getPosY(int scaledHeight) {
        return (this.settings.getPositionValue(Settings.compass_position)[1]);
    }

    @Override
    public int getPosX(int scaledWidth) {
        return (scaledWidth / 2) + this.settings.getPositionValue(Settings.compass_position)[0];
    }

    public int getHeight(int scaledHeight) {
        return 6;
    }

    @Override
    public float getScale() {
        return 1;
    }

}