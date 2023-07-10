package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;


public class HudElementFoodRPG extends HudElement {
    public HudElementFoodRPG() {
        super(HudType.FOOD, 0, 0, 0, 0, true);
        parent = HudType.WIDGET;
    }
    @Override
    public boolean checkConditions() {
        return  this.mc.player != null && !this.mc.player.isCreative() && !this.mc.player.isSpectator();
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(checkConditions()){
            bind(INTERFACE);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            assert this.mc.player != null;
            int stamina = this.mc.player.getFoodData().getFoodLevel();
            int staminaMax = 20;
            int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.rpgexperience_position)[0];
            int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 35 : 31) + this.settings.getPositionValue(Settings.rpgexperience_position)[1];

            int staminaWidth = Math.max((int) (88.0D * (stamina / (double) staminaMax)), 1);

            gui.blit(ms, posX, posY, 0, 132, staminaWidth, 8);

            String stringExp = stamina + " / " + staminaMax;

            if (this.settings.getBoolValue(Settings.show_numbers_rpgexperience)) {
                ms.scale(0.5f, 0.5f, 0.5f);
                Gui.drawCenteredString(ms, this.mc.font, stringExp, posX * 2 + 88, posY * 2 + 4, -1);
                ms.scale(2f, 2f, 2f);
            }
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            bind(Gui.GUI_ICONS_LOCATION);
        }

    }
}