package net.cheto97.rpgcraftmod.ModHud.Elements.hotbar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.providers.LifeProvider;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class HudElementLifeHotbar extends HudElement {
    double life;
    double lifeMax;

    public HudElementLifeHotbar() {
        super(HudType.LIFE, 0, 0, 0, 0, true);
        parent = HudType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        if (this.mc.options.hideGui) return false;
        assert this.mc.player != null;
        return !(this.mc.player.getVehicle() instanceof LivingEntity);
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {

        int height = scaledHeight + this.settings.getPositionValue(Settings.life_position)[1];
        assert this.mc.player != null;
        int absorption = Mth.ceil(this.mc.player.getAbsorptionAmount());
        this.mc.player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(hp ->{
            life = hp.get();
            lifeMax = hp.getMax();
        });
        if(life > lifeMax) life = lifeMax;
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.life_position)[0];

        if (absorption > 1)
            drawCustomBar(ms, posX, height - 56, 200, 10,  (life + absorption) /  (lifeMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT));

        if (this.mc.player.hasEffect(MobEffects.POISON)) {
            drawCustomBar(ms, posX, height - 56, 200, 10,  life /  (lifeMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_poison), offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT));
        } else if (this.mc.player.hasEffect(MobEffects.WITHER)) {
            drawCustomBar(ms, posX, height - 56, 200, 10,  life /  (lifeMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_wither), offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT));
        } else {
            drawCustomBar(ms, posX, height - 56, 200, 10,  life /  (lifeMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_life), offsetColorPercent(this.settings.getIntValue(Settings.color_life), OFFSET_PERCENT));
        }

        String stringLife = this.settings.getBoolValue(Settings.life_percentage) ? Math.floor( life /  lifeMax * 100) + "%" : (life + absorption) + "/" + lifeMax;
        if (this.settings.getBoolValue(Settings.show_numbers_life))
            Gui.drawCenteredString(ms, this.mc.font, stringLife, posX + 100, height - 55, -1);
    }
}