package net.cheto97.rpgcraftmod.ModHud.Elements.hotbar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.providers.LifeProvider;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;

public class HudElementMountLifeHotbar extends HudElement {
    double life;
    double lifeMax;

    public HudElementMountLifeHotbar() {
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
        int height = scaledHeight + this.settings.getPositionValue(Settings.mount_life_position)[1];
        assert this.mc.player != null;
        LivingEntity mount = (LivingEntity) this.mc.player.getVehicle();
        assert mount != null;
        mount.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(hp ->{
            life = hp.get();
            lifeMax = hp.getMax();
        });
        if(life > lifeMax) life = lifeMax;
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.mount_life_position)[0];
        String stringLife = this.settings.getBoolValue(Settings.mount_life_percentage) ? Math.floor( life / lifeMax * 100) + "%" : life + "/" + lifeMax;

        if (this.settings.getBoolValue(Settings.show_numbers_life))
            Gui.drawCenteredString(ms, this.mc.font, stringLife, posX + 100, height - 55, -1);
    }

}