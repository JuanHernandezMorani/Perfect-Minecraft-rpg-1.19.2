package net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.minecraft.client.gui.Gui;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.world.food.FoodData;


public class HudElementFoodDefault extends HudElement {
    public HudElementFoodDefault() {
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
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 24) + this.settings.getPositionValue(Settings.hunger_position)[0];
        int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 26 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];

        String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
        if (this.settings.getBoolValue(Settings.show_numbers_food))
            Gui.drawCenteredString(ms,this.mc.font, staminaString, posX + 55, posY + 2, -1);
    }
}
