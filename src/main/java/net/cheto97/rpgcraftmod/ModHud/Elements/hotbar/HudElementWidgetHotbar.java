package net.cheto97.rpgcraftmod.ModHud.Elements.hotbar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;

public class HudElementWidgetHotbar extends HudElement {

    public HudElementWidgetHotbar() {
        super(HudType.WIDGET, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        bind(INTERFACE);
        int posX = this.settings.getPositionValue(Settings.widget_position)[0];
        int posY = scaledHeight + this.settings.getPositionValue(Settings.widget_position)[1];
        gui.blit(ms, posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 26), posY - 16 - 52 + 9, 0, 172, 251, 48);

        int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
        int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
        if (RpgcraftMod.instance.settings.getBoolValue(Settings.render_player_face)) {
            gui.blit(ms, posX + facePosX, posY - 16 - 52 + 7 + facePosY, 164, 20, 50, 52);
            if(this.mc.player !=null){
                bind(getPlayerSkin(this.mc.player));
            }
            ms.scale(0.5f, 0.5f, 0.5f);
            gui.blit(ms, posX * 2 + 34 + facePosX * 2, posY * 2 - 88 + facePosY * 2, 32, 32, 32, 32);
            gui.blit(ms, posX * 2 + 34 + facePosX * 2, posY * 2 - 88 + facePosY * 2, 160, 32, 32, 32);
            ms.scale(2f, 2f, 2f);
        } else {
            gui.blit(ms, posX, posY - 12 - 52 + 7, 214, 58, 26, 42);
        }
        bind(GuiComponent.GUI_ICONS_LOCATION);
    }
}