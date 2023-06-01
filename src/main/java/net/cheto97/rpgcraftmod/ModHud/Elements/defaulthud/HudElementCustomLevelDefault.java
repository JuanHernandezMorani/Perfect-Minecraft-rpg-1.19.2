package net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;

public class HudElementCustomLevelDefault extends HudElement {

    public HudElementCustomLevelDefault() {
        super(HudType.RPGLEVEL, 0, 0, 0, 0, true);
        parent = HudType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        RenderSystem.enableBlend();
        String level = String.valueOf(this.mc.player.experienceLevel);
        Gui.drawString(ms, this.mc.font, level, (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 12) - (this.mc.font.width(level) / 2) + this.settings.getPositionValue(Settings.rpglevel_position)[0], (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 14) + this.settings.getPositionValue(Settings.rpglevel_position)[1], 0x80FF20);
        RenderSystem.disableBlend();
    }

}