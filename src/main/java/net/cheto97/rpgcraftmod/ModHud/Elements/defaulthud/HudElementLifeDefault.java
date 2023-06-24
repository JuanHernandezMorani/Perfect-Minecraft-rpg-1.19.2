package net.cheto97.rpgcraftmod.ModHud.Elements.defaulthud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.providers.LifeProvider;
import net.minecraft.client.gui.Gui;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.util.Mth;

public class HudElementLifeDefault extends HudElement {
    private double life;
    private double lifeMax;

    public HudElementLifeDefault() {
        super(HudType.LIFE, 0, 0, 0, 0, true);
        parent = HudType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledHeight, int scaledWidth) {
        if(this.mc.player != null){
            this.mc.player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(hp -> {
                life = Mth.ceil(hp.get());
                lifeMax = Mth.ceil(hp.getMax());
            });
        }
        int absorption = Mth.ceil(this.mc.player.getAbsorptionAmount());
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 24) + this.settings.getPositionValue(Settings.life_position)[0];
        int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 13 : 5) + this.settings.getPositionValue(Settings.life_position)[1];

        String stringLife = this.settings.getBoolValue(Settings.life_percentage) ? Math.floor( life /  lifeMax * 100) + "%" : (life + absorption) + "/" + lifeMax;
        if (this.settings.getBoolValue(Settings.show_numbers_life))
            Gui.drawCenteredString(ms,this.mc.font, stringLife, posX + 55, posY + 2, -1);
    }
}
