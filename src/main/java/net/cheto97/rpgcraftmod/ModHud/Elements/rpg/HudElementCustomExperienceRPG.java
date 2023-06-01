package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.cheto97.rpgcraftmod.providers.ExperienceProvider;
import net.minecraft.client.gui.Gui;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;

public class HudElementCustomExperienceRPG extends HudElement {
    double exp;
    double expCap;

    public HudElementCustomExperienceRPG() {
        super(HudType.RPGEXPERIENCE, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int posX = this.settings.getPositionValue(Settings.rpgexperience_position)[0];
        int posY = this.settings.getPositionValue(Settings.rpgexperience_position)[1];

        assert this.mc.player != null;
        this.mc.player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(xp -> {
            exp = xp.get();
        });
        this.mc.player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(lvl -> {
            expCap = lvl.experienceNeeded();
        });

        double full = ((scaledWidth - posX)) / expCap;

        drawRect(ms, posX, scaledHeight - 7 + posY, scaledWidth, 7, 0xB33A3A3A);
        drawRect(ms, 1 + posX, scaledHeight - 6 + posY, (int) (exp * full), 4, this.settings.getIntValue(Settings.color_rpgexperience));

        String stringExp = "";

        if (this.settings.getBoolValue(Settings.show_numbers_rpgexperience)) {
            int width2 = this.mc.font.width(stringExp) / 2;
            drawRect(ms, 1 + posX, scaledHeight - 15 + posY, width2 + 4, 8, 0xFFFFFFFF);
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms, this.mc.font, stringExp, 6 + width2 + posX * 2, (scaledHeight - 12) * 2 - 1 + posY * 2, -1);
            ms.scale(2f, 2f, 2f);
        }
    }

}