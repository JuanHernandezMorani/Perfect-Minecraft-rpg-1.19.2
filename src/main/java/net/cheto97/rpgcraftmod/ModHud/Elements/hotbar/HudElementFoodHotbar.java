package net.cheto97.rpgcraftmod.ModHud.Elements.hotbar;

import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

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
        ItemStack itemMain = this.mc.player.getMainHandItem();
        ItemStack itemSec = this.mc.player.getOffhandItem();

        if (stats.needsFood() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
            float value = 0;
            if (itemMain != ItemStack.EMPTY && itemMain.getItem().isEdible()) {
                value = Objects.requireNonNull(itemMain.getItem().getFoodProperties(itemMain, null)).getNutrition();
            } else if (itemSec != ItemStack.EMPTY && itemMain.getItem().isEdible()) {
                value = Objects.requireNonNull(itemSec.getItem().getFoodProperties(itemMain, null)).getNutrition();
            }
            if (value > 0) {
                int bonusHunger = (int) (value + stamina);
                if (bonusHunger > staminaMax)
                    bonusHunger = staminaMax;
                int colorPreview = offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW);
                drawCustomBar(ms, posX, height - 26, 200, 10, bonusHunger / (double) staminaMax * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT));
            }
        }

        if (this.mc.player.hasEffect(MobEffects.HUNGER)) {
            drawCustomBar(ms, posX, height - 26, 200, 10, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_hunger), offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT));
        } else {
            drawCustomBar(ms, posX, height - 26, 200, 10, stamina / (double) staminaMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_food), offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT));
        }

        String staminaString = this.settings.getBoolValue(Settings.hunger_percentage) == true ? (int) Math.floor((double) stamina / (double) staminaMax * 100) + "%" : stamina + "/" + staminaMax;
        if (this.settings.getBoolValue(Settings.show_numbers_food))
            Gui.drawCenteredString(ms, this.mc.font, staminaString, posX + 100, height - 25, -1);
    }

}