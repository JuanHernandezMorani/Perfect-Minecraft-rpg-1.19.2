package net.cheto97.rpgcraftmod.ModHud.huds;

import net.cheto97.rpgcraftmod.ModHud.Elements.rpg.*;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.minecraft.client.Minecraft;

public class RPGHud extends HudDefault {
    
    private int posX = 0;

    public RPGHud(Minecraft mc, String hudKey, String hudName) {
        super(mc, hudKey, hudName);
    }
    public int getPosX() {
        return this.posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public HudElement setElementAir() {
        return new HudElementAirRPG();
    }

    @Override
    public HudElement setElementArmor() {
        return new HudElementArmorRPG();
    }

    @Override
    public HudElement setElementClock() {
        return new HudElementClockRPG();
    }

    @Override
    public HudElement setElementDetails() {
        return new HudElementDetailsRPG();
    }

    @Override
    public HudElement setElementCustomExperience() {
        return new HudElementCustomExperienceRPG();
    }

    @Override
    public HudElement setElementFood() {
        return new HudElementFoodRPG();
    }

    @Override
    public HudElement setElementLife() {
        return new HudElementLifeRPG();
    }
    @Override
    public HudElement setElementMana() {
        return new HudElementManaRPG();
    }

    @Override
    public HudElement setElementMountLife() {
        return new HudElementMountLifeRPG();
    }

    @Override
    public HudElementHotbarRPG setElementHotbar() {
        return new HudElementHotbarRPG();
    }

    @Override
    public HudElement setElementJumpBar() {
        return new HudElementJumpBarRPG();
    }

    @Override
    public HudElement setElementCustomLevel() {
        return new HudElementCustomLevelRPG();
    }

    @Override
    public HudElement setElementWidget() {
        return new HudElementWidgetRPG();
    }

    @Override
    protected HudElement setElementCompass() {
        return new HudElementCompassRPG();
    }

}