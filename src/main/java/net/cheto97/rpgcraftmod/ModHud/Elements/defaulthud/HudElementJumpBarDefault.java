package net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;

public class HudElementJumpBarDefault  extends HudElement {

    public HudElementJumpBarDefault() {
        super(HudType.JUMP_BAR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        assert this.mc.player != null;
        return this.mc.player.getVehicle() instanceof LivingEntity && (!this.settings.getBoolValue(Settings.limit_jump_bar) || this.mc.player.getJumpRidingScale() > 0F);
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        }

}