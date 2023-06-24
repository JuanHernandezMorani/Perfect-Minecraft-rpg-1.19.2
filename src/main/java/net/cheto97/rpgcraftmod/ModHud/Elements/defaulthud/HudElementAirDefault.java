package net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.minecraft.client.gui.Gui;

import net.minecraft.world.level.material.Fluids;

public class HudElementAirDefault extends HudElement {
    public HudElementAirDefault() {
        super(HudType.AIR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        assert this.mc.player != null;
        return (this.mc.player.isEyeInFluidType(Fluids.WATER.getFluidType()) || this.mc.player.getAirSupply() < this.mc.player.getMaxAirSupply()) && !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {}
}
