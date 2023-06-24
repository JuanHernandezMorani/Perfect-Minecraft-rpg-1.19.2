package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;

public class HudElementWidgetRPG extends HudElement {

    public HudElementWidgetRPG() {
        super(HudType.WIDGET, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui && RpgcraftMod.instance.settings.getBoolValue(Settings.render_player_face);
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        bind(INTERFACE);
        int posY = this.settings.getPositionValue(Settings.widget_position)[1];
        gui.blit(ms,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 26), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 4 : 0), 0, 35, 114, 44);
        assert this.mc.player != null;
        if (this.mc.player.getVehicle() instanceof LivingEntity) {
            gui.blit(ms,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 51 : 23), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 44 : 39), 164, 0, 92, 20);
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
            bind(GUI_ICONS_LOCATION);
        } else {
            gui.blit(ms,posX, 3, 214, 20, 26, 38);
            bind(GUI_ICONS_LOCATION);
        }
    }
}