package net.cheto97.rpgcraftmod.ModHud.huds;

import net.cheto97.rpgcraftmod.ModHud.Elements.vanilla.*;
import net.cheto97.rpgcraftmod.ModHud.Hud;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.minecraft.client.Minecraft;

public class HudVanilla extends Hud {

    public HudVanilla(Minecraft mc, String hudKey, String hudName) {
        super(mc, hudKey, hudName);
    }

    @Override
    public HudElement setElementHotbar() {
        return null;
    }

    @Override
    public HudElement setElementLife() {
        return null;
    }

    @Override
    public HudElement setElementMana() {
        return null;
    }

    @Override
    public HudElement setElementFood() {
        return null;
    }

    @Override
    public HudElement setElementArmor() {
        return null;
    }

    @Override
    public HudElement setElementAir() {
        return null;
    }

    @Override
    public HudElement setElementCustomExperience() {
        return null;
    }

    @Override
    public HudElement setElementCustomLevel() {
        return null;
    }

    @Override
    public HudElement setElementJumpBar() {
        return null;
    }

    @Override
    public HudElement setElementMountLife() {
        return null;
    }
    @Override
    public HudElement setElementWidget() {
        return new HudElementEmpty();
    }

    @Override
    protected HudElement setElementCompass() {
        return new HudElementCompassVanilla();
    }

    @Override
    protected HudElement setElementMobEffects() {
        return new HudElementMobEffectsVanilla();
    }
}