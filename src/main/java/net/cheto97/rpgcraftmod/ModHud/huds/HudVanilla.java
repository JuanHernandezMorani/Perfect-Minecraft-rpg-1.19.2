package net.cheto97.rpgcraftmod.ModHud.huds;

import net.cheto97.rpgcraftmod.ModHud.Elements.rpg.HudElementCustomExperienceRPG;
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
        return new HudElementEmpty();
    }

    @Override
    public HudElement setElementLife() {
        return new HudElementEmpty();
    }

    @Override
    public HudElement setElementMana() {
        return new HudElementEmpty();
    }

    @Override
    public HudElement setElementFood() {
        return new HudElementEmpty();
    }

    @Override
    public HudElement setElementArmor() {
        return new HudElementEmpty();
    }

    @Override
    public HudElement setElementAir() {
        return new HudElementEmpty();
    }

    @Override
    public HudElement setElementCustomExperience() {
        return new HudElementCustomExperienceRPG();
    }

    @Override
    public HudElement setElementCustomLevel() {
        return new HudElementEmpty();
    }
    @Override
    protected HudElement setElementView() {
        return new HudElementViewVanilla();
    }
    @Override
    public HudElement setElementJumpBar() {
        return new HudElementEmpty();
    }

    @Override
    public HudElement setElementMountLife() {
        return new HudElementEmpty();
    }
    @Override
    public HudElement setElementWidget() {
        return new HudElementEmpty();
    }

    @Override
    protected HudElement setElementCompass() {
        return new HudElementEmpty();
    }

    @Override
    protected HudElement setElementMobEffects() {
        return new HudElementMobEffectsVanilla();
    }
}