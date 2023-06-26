package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.minecraft.client.gui.Gui;

import static net.cheto97.rpgcraftmod.util.IntToString.formatearNumero;

public class HudElementCustomLevelRPG extends HudElement {
    public HudElementCustomLevelRPG() {
        super(HudType.RPGLEVEL, 0, 0, 0, 0, true);
        parent = HudType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.player != null){
            RenderSystem.disableBlend();
            String level = formatearNumero(PlayerData.getPlayerLevel());
            Gui.drawString(ms, this.mc.font, level, (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 13) + this.settings.getPositionValue(Settings.rpglevel_position)[0] - this.mc.font.width(level) / 2, (this.settings.getBoolValue(Settings.render_player_face) ? 38 : 18) + this.settings.getPositionValue(Settings.rpglevel_position)[1], 0x80FF20);
            RenderSystem.enableBlend();
        }
    }

}