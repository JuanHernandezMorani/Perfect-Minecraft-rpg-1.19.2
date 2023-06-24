package net.cheto97.rpgcraftmod.ModHud.Elements.hotbar;

import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.food.FoodData;


public class HudElementFoodHotbar extends HudElement {

    public HudElementFoodHotbar() {
        super(HudType.FOOD, 0, 0, 0, 0, true);
        parent = HudType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        assert this.mc.player != null;
        FoodData stats = this.mc.player.getFoodData();
        int stamina = stats.getFoodLevel();
        int staminaMax = 20;
        int height = scaledHeight + this.settings.getPositionValue(Settings.hunger_position)[1];
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];

        String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
        if (this.settings.getBoolValue(Settings.show_numbers_food))
            Gui.drawCenteredString(ms, this.mc.font, staminaString, posX + 100, height - 25, -1);
    }

}