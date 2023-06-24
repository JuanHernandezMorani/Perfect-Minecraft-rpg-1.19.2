package net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.providers.LifeProvider;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;

public class HudElementMountLifeDefault extends HudElement {
    double life;
    double lifeMax;

    public HudElementMountLifeDefault() {
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
        assert this.mc.player != null;
        LivingEntity mount = (LivingEntity) this.mc.player.getVehicle();
        if(mount != null){
            mount.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(hp ->{
                life = hp.get();
                lifeMax = hp.getMax();
            });
            if(life > lifeMax) life = lifeMax;
            int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 53 : 33) + this.settings.getPositionValue(Settings.mount_life_position)[0];
            int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 40) + this.settings.getPositionValue(Settings.mount_life_position)[1];
            String stringlife = this.settings.getBoolValue(Settings.mount_life_percentage) ? Math.floor(life / lifeMax * 100) + "%" : life + "/" + lifeMax;

            if (this.settings.getBoolValue(Settings.show_numbers_life)) {
                ms.scale(0.5f, 0.5f, 0.5f);
                Gui.drawCenteredString(ms,this.mc.font, stringlife, posX * 2 + 88, posY * 2 + 4, -1);
                ms.scale(2.0f, 2.0f, 2.0f);
            }
        }
    }
}