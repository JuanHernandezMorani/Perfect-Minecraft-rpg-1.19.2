package net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
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
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int height = scaledHeight + this.settings.getPositionValue(Settings.air_position)[1];
        int adjustedWidth = scaledWidth / 2 - 91 + this.settings.getPositionValue(Settings.air_position)[0];
        int airAmount = this.mc.player.getAirSupply();
        double maxAir = this.mc.player.getMaxAirSupply();
        drawCustomBar(ms, adjustedWidth + 21, height - 80, 141, 10, airAmount / maxAir * 100.0D, this.settings.getIntValue(Settings.color_air), offsetColorPercent(this.settings.getIntValue(Settings.color_air), OFFSET_PERCENT));
    }
}
