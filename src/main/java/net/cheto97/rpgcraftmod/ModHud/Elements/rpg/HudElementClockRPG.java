package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.Elements.vanilla.HudElementClockVanilla;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HudElementClockRPG extends HudElementClockVanilla {

    public HudElementClockRPG() {
        super();
        this.posX = 0;
        this.posY = 0;
        this.elementWidth = 0;
        this.elementHeight = 0;
        this.moveable = true;
    }

    @Override
    public boolean checkConditions() {
        if (!this.settings.getBoolValue(Settings.enable_clock) || this.mc.options.renderDebug) return false;
        if (!this.settings.getBoolValue(Settings.enable_immersive_clock)) return true;
        assert this.mc.player != null;
        return this.mc.player.getInventory().contains(new ItemStack(Items.CLOCK));
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int clockColor = 0xFFFFFF;
        if (this.settings.getBoolValue(Settings.enable_clock_color)) {
            clockColor = getClockColor();
        }
        if (this.settings.getBoolValue(Settings.reduce_size))
            ms.scale(0.5f, 0.5f, 0.5f);
        this.mc.font.draw(ms, getTime(), (this.settings.getBoolValue(Settings.reduce_size) ? 8 : 4) + this.settings.getPositionValue(Settings.clock_position)[0], (this.settings.getBoolValue(Settings.reduce_size) ? 124 : 62) + this.settings.getPositionValue(Settings.clock_position)[1], clockColor);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.settings.getBoolValue(Settings.reduce_size))
            ms.scale(2f, 2f, 2f);
    }
}