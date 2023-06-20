package net.cheto97.rpgcraftmod.ModHud.huds;

import net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud.*;
import net.cheto97.rpgcraftmod.ModHud.Elements.rpg.HudElementCustomExperienceRPG;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.minecraft.client.Minecraft;

public class HudDefault extends HudVanilla{
    public HudDefault(Minecraft mc, String hudKey, String hudName) {
        super(mc, hudKey, hudName);
    }

    @Override
    public HudElement setElementAir() {
        return new HudElementAirDefault();
    }

    @Override
    public HudElement setElementArmor() {
        return new HudElementArmorDefault();
    }

    @Override
    public HudElement setElementCustomExperience() {
        return new HudElementCustomExperienceRPG();
    }

    @Override
    public HudElement setElementCustomLevel() {
        return new HudElementCustomLevelDefault();
    }

    @Override
    public HudElement setElementFood() {
        return new HudElementFoodDefault();
    }

    @Override
    public HudElement setElementLife() {
        return new HudElementLifeDefault();
    }

    @Override
    public HudElement setElementMountLife() {
        return new HudElementMountLifeDefault();
    }

    @Override
    public HudElement setElementJumpBar() {
        return new HudElementJumpBarDefault();
    }

    @Override
    public HudElement setElementHotbar() {
        return new HudElementHotbarDefault();
    }

    @Override
    public HudElement setElementWidget() {
        return new HudElementWidgetDefault();
    }
}
