package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;

public class HudElementArmorRPG extends HudElement {

    public HudElementArmorRPG() {
        super(HudType.ARMOR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        float scale = getScale();
        ms.scale(scale, scale, scale);
        int left = getPosX(scaledWidth);
        int top = getPosY(scaledHeight);

        assert this.mc.player != null;
        int level = this.mc.player.getArmorValue();
        if (level > 0) {
            int height = getHeight(scaledHeight);
            int width2 = 1 + 9 + 2 + this.mc.font.width(String.valueOf(level)) + 2;
            drawRect(ms, left, top, width2, height, 0xA0000000);
            this.mc.font.draw(ms,"", left + 12, top + 2, -1);
        }

        scale = getInvertedScale();
        ms.scale(scale, scale, scale);
    }

    @Override
    public int getPosX(int scaledWidth) {
        return (int) ((scaledWidth / 2 - 91)*getInvertedScale() + this.settings.getPositionValue(Settings.armor_position)[0]);
    }

    @Override
    public int getPosY(int scaledHeight) {
        return (int) ((scaledHeight - 29 + 2)*getInvertedScale() - getHeight(scaledHeight) + this.settings.getPositionValue(Settings.armor_position)[1]);
    }

    @Override
    public int getWidth(int scaledWidth) {
        return 144;
    }

    public int getHeight(int scaledHeight) {
        return 10;
    }

    @Override
    public float getScale() {
        return 1;
    }

}