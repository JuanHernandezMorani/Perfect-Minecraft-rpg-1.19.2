package net.cheto97.rpgcraftmod.ModHud.Elements.hotbar;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;

import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;

public class HudElementArmorHotbar extends HudElement {

    public HudElementArmorHotbar() {
        super(HudType.ARMOR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        bind(GUI_ICONS_LOCATION);
        int left = (this.settings.getBoolValue(Settings.render_player_face) ? 46 : 22) + this.settings.getPositionValue(Settings.armor_position)[0];
        int top = scaledHeight - 64 + this.settings.getPositionValue(Settings.armor_position)[1];
        assert this.mc.player != null;
        int level = this.mc.player.getArmorValue();
        for (int i = 1; level > 0 && i < 20; i += 2) {
            if (i < level) {
                gui.blit(ms,left + 62, top - 2, 34, 9, 9, 9);
            } else if (i == level) {
                gui.blit(ms,left + 62, top - 2, 25, 9, 9, 9);
            } else if (i > level) {
                gui.blit(ms,left + 62, top - 2, 16, 9, 9, 9);
            }
            left += 8;
        }
    }

}