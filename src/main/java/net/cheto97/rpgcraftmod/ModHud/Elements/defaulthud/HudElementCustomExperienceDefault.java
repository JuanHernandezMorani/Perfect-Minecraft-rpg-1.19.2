package net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.cheto97.rpgcraftmod.providers.ExperienceProvider;
import net.minecraft.client.gui.Gui;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.util.Mth;

public class HudElementCustomExperienceDefault extends HudElement {
    double exp;
    double expCap;
    public HudElementCustomExperienceDefault() {
        super(HudType.RPGEXPERIENCE, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        assert this.mc.player != null;
        this.mc.player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(lvl ->{
            expCap = lvl.experienceNeeded();
        });
        this.mc.player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(xp -> {
            exp = xp.get();
        });
        double full = 100D / expCap;
        int posX = this.settings.getPositionValue(Settings.rpgexperience_position)[0];
        int posY = this.settings.getPositionValue(Settings.rpgexperience_position)[1];
        drawCustomBar(ms, posX, scaledHeight - 10 + posY, scaledWidth, 10, exp * full, this.settings.getIntValue(Settings.color_rpgexperience), offsetColorPercent(this.settings.getIntValue(Settings.color_rpgexperience), 25));

        String stringExp =  this.settings.getBoolValue(Settings.rpgexperience_percentage) ? (int) Math.floor((double) exp / (double) expCap * 100) + "%" : exp + "/" + expCap;

        int var7 = scaledWidth / 2;
        if (this.settings.getBoolValue(Settings.show_numbers_rpgexperience))
            Gui.drawCenteredString(ms,this.mc.font, stringExp, var7 + posX, scaledHeight - 9 + posY, -1);
    }

}
