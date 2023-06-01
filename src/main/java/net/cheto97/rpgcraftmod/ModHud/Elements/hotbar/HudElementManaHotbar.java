package net.cheto97.rpgcraftmod.ModHud.Elements.hotbar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.providers.ManaProvider;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class HudElementManaHotbar extends HudElement {
    double mana;
    double manaMax;

    public HudElementManaHotbar() {
        super(HudType.MANA, 0, 0, 0, 0, true);
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

        int height = scaledHeight + this.settings.getPositionValue(Settings.mana_position)[1];
        assert this.mc.player != null;
        int absorption = Mth.ceil(this.mc.player.getAbsorptionAmount());
        this.mc.player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(hp ->{
            mana = hp.get();
            manaMax = hp.getMax();
        });
        if(mana > manaMax) mana = manaMax;
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.mana_position)[0];

        if (absorption > 1)
            drawCustomBar(ms, posX, height - 56, 200, 10,  (mana + absorption) /  (manaMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_absorption), offsetColorPercent(this.settings.getIntValue(Settings.color_absorption), OFFSET_PERCENT));

        if (this.mc.player.hasEffect(MobEffects.POISON)) {
            drawCustomBar(ms, posX, height - 56, 200, 10,  mana /  (manaMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_poison), offsetColorPercent(this.settings.getIntValue(Settings.color_poison), OFFSET_PERCENT));
        } else if (this.mc.player.hasEffect(MobEffects.WITHER)) {
            drawCustomBar(ms, posX, height - 56, 200, 10,  mana /  (manaMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_wither), offsetColorPercent(this.settings.getIntValue(Settings.color_wither), OFFSET_PERCENT));
        } else {
            drawCustomBar(ms, posX, height - 56, 200, 10,  mana /  (manaMax + absorption) * 100D, -1, -1, this.settings.getIntValue(Settings.color_mana), offsetColorPercent(this.settings.getIntValue(Settings.color_mana), OFFSET_PERCENT));
        }

        String stringMana = this.settings.getBoolValue(Settings.mana_percentage) ? Math.floor( mana /  manaMax * 100) + "%" : (mana + absorption) + "/" + manaMax;
        if (this.settings.getBoolValue(Settings.show_numbers_mana))
            Gui.drawCenteredString(ms, this.mc.font, stringMana, posX + 100, height - 55, -1);
    }
}