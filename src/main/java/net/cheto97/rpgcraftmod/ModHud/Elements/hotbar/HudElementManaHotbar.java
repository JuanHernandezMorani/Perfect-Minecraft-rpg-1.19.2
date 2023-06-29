package net.cheto97.rpgcraftmod.ModHud.Elements.hotbar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.providers.ManaProvider;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Mth;
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
            mana = PlayerData.getPlayerMana();
            manaMax = PlayerData.getPlayerManaMax();

        if(mana > manaMax) mana = manaMax;
        int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.mana_position)[0];


        String stringMana = this.settings.getBoolValue(Settings.mana_percentage) ? Math.floor( mana /  manaMax * 100) + "%" : mana + "/" + manaMax;
        if (this.settings.getBoolValue(Settings.show_numbers_mana))
            Gui.drawCenteredString(ms, this.mc.font, stringMana, posX + 100, height - 55, -1);
    }
}