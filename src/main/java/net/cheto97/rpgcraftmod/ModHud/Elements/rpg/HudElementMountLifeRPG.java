package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.networking.data.EntityData;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.providers.LifeProvider;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;


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
            Player player = this.mc.player;
            LivingEntity mount = (LivingEntity) this.mc.player.getVehicle();
            /*
            if(player.getId() == PlayerData.getPlayerId()){
                if(mount.getId() == EntityData.getEntityId()){

                }
            }

             */
            bind(INTERFACE);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

            assert mount != null;
            mount.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> {
                double health = life.get();
                double healthMax =  life.getMax();

                if(health > healthMax) health = healthMax;
                int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 53 : 25) + this.settings.getPositionValue(Settings.mount_life_position)[0];
                int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 54 : 49) + this.settings.getPositionValue(Settings.mount_life_position)[1];

                gui.blit(ms, posX, posY, 0, 124, (int) (88.0D * (health / healthMax)), 8);

                String stringHealth = doubleToString(health) +" / "+ doubleToString(healthMax);

                if (this.settings.getBoolValue(Settings.show_numbers_life)) {
                    ms.scale(0.5f, 0.5f, 0.5f);
                    Gui.drawCenteredString(ms, this.mc.font, stringHealth, posX * 2 + 88, posY * 2 + 4, -1);
                    ms.scale(2f, 2f, 2f);
                }
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                bind(GuiComponent.GUI_ICONS_LOCATION);
            });

        }
    }
}