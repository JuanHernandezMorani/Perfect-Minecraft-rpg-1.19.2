package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.level.material.Fluids;

public class HudElementAirRPG extends HudElement {

    public HudElementAirRPG() {
        super(HudType.AIR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        assert this.mc.player != null;
        return (this.mc.player.isEyeInFluidType(Fluids.WATER.getFluidType()) || this.mc.player.getAirSupply() < this.mc.player.getMaxAirSupply()) && !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.player != null && this.mc.player.isInFluidType(Fluids.WATER.getFluidType()) && this.mc.player.getAirSupply() < this.mc.player.getMaxAirSupply()){
            bind(INTERFACE);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
            int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.air_position)[0];
            int airAmount = this.mc.player.getAirSupply();
            double maxAir = this.mc.player.getMaxAirSupply();
            gui.blit(ms, adjustedWidth - 70, height - 50, 0, 160, 141, 10);
            gui.blit(ms, adjustedWidth - 70, height - 50, 0, 140, (int) (141.0D * (airAmount / maxAir)), 10);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            bind(Gui.GUI_ICONS_LOCATION);
        }
    }
}