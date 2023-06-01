package net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;

public class HudElementWidgetDefault extends HudElement {

    public HudElementWidgetDefault() {
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
        int posY = this.settings.getPositionValue(Settings.widget_position)[1];
        gui.blit(ms,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 25), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 8 : 0), 0, 0, 114, 35);
        assert this.mc.player != null;
        if (this.mc.player.getVehicle() instanceof LivingEntity) {
            gui.blit(ms,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 51 : 31), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 39 : 30), 164, 0, 92, 20);
        }

        int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
        int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
        if (this.settings.getBoolValue(Settings.render_player_face)) {
            gui.blit(ms,posX + facePosX, posY + facePosY, 114, 0, 50, 50);
            bind(getPlayerSkin(this.mc.player));
            ms.scale(0.5f, 0.5f, 0.5f);
            gui.blit(ms,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 32, 32, 32, 32);
            gui.blit(ms,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 160, 32, 32, 32);
            ms.scale(2f, 2f, 2f);
        } else {
            gui.blit(ms,posX, posY + (this.settings.getBoolValue(Settings.render_player_face) ? 11 : 3), 114, 50, 25, 29);
        }
        bind(GUI_ICONS_LOCATION);
    }

}