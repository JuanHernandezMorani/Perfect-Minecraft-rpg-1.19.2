package net.cheto97.rpgcraftmod.ModHud.huds;

import net.cheto97.rpgcraftmod.ModHud.Elements.hotbar.*;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.minecraft.client.Minecraft;

public class HudHotbarWidget extends HudDefault {

    public HudHotbarWidget(Minecraft mc, String hudKey, String hudName) {
        super(mc, hudKey, hudName);
        this.chatOffset = -22;
    }

    @Override
    public HudElement setElementArmor() {
        return new HudElementArmorHotbar();
    }

    @Override
    public HudElement setElementFood() {
        return new HudElementFoodHotbar();
    }

    @Override
    public HudElement setElementLife() {
        return new HudElementLifeHotbar();
    }
    @Override
    public HudElement setElementMana(){
        return new HudElementManaHotbar();
    }
    @Override
    public HudElement setElementMountLife() {
        return new HudElementMountLifeHotbar();
    }
    @Override
    public HudElement setElementCustomLevel() {
        return new HudElementCustomLevelHotbar();
    }
    @Override
    public HudElement setElementWidget() {
        return new HudElementWidgetHotbar();
    }

    @Override
    public HudElement setElementHotbar() {
        return new HudElementHotbarHotbar();
    }
}