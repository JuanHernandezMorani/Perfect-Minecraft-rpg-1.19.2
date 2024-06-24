package net.cheto97.rpgcraftmod.ModHud;
/*
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;

public class HudElementJumpBarRPG extends HudElement {

    public HudElementJumpBarRPG() {
        super(HudType.JUMP_BAR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return  this.mc.player != null && this.mc.player.getVehicle() != null && !this.mc.player.isCreative() && this.mc.player.getVehicle() instanceof LivingEntity && (!this.settings.getBoolValue(Settings.limit_jump_bar) || this.mc.player.getJumpRidingScale() > 0F);
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(checkConditions()){
            bind(INTERFACE);
            int height = scaledHeight + this.settings.getPositionValue(Settings.jump_bar_position)[1];
            int adjustedWidth = (scaledWidth / 2) + this.settings.getPositionValue(Settings.jump_bar_position)[0];
            assert this.mc.player != null;
            float var14 = this.mc.player.getJumpRidingScale();
            int color = (int) (var14 * 100.0F);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            gui.blit(ms, adjustedWidth - 71, height - 80, 0, 160, 141, 10);
            gui.blit(ms, adjustedWidth - 71, height - 80, 0, 150, (int) (141.0D * (color / 100.0D)), 10);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            bind(Gui.GUI_ICONS_LOCATION);
        }
    }

}*/