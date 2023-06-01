package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.LivingEntity;


public class HudElementMountLifeRPG extends HudElement {
    

    public HudElementMountLifeRPG() {
        super(HudType.MOUNT_LIFE, 0, 0, 0, 0, false);
        parent = HudType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        assert this.mc.player != null;
        return this.mc.player.getVehicle() instanceof LivingEntity && !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.player != null && this.mc.player.getVehicle() != null){
            bind(INTERFACE);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            LivingEntity mount = (LivingEntity) this.mc.player.getVehicle();
            int health = (int) Math.ceil(mount.getHealth());
            int healthMax = (int) mount.getMaxHealth();
            if(health > healthMax) health = healthMax;
            int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 53 : 25) + this.settings.getPositionValue(Settings.mount_life_position)[0];
            int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 54 : 49) + this.settings.getPositionValue(Settings.mount_life_position)[1];

            gui.blit(ms, posX, posY, 0, 124, (int) (88.0D * ((double) health / (double) healthMax)), 8);

            String stringHealth = this.settings.getBoolValue(Settings.mount_life_percentage) ? (int) Math.floor((double) health / (double) healthMax * 100) + "%" : health + "/" + healthMax;

            if (this.settings.getBoolValue(Settings.show_numbers_life)) {
                ms.scale(0.5f, 0.5f, 0.5f);
                Gui.drawCenteredString(ms, this.mc.font, stringHealth, posX * 2 + 88, posY * 2 + 4, -1);
                ms.scale(2f, 2f, 2f);
            }
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            bind(GuiComponent.GUI_ICONS_LOCATION);
        }
    }
}