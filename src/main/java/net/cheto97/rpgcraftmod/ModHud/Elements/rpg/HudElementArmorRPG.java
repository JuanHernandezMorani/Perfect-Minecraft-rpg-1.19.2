package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.customstats.Defense;
import net.cheto97.rpgcraftmod.customstats.MagicDefense;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.providers.DefenseProvider;
import net.cheto97.rpgcraftmod.providers.MagicDefenseProvider;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;

import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;

public class HudElementArmorRPG extends HudElement {

    public HudElementArmorRPG() {
        super(HudType.ARMOR, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        float scale = getScale();
        ms.scale(scale, scale, scale);
        int left = getPosX(scaledWidth);
        int top = getPosY(scaledHeight);
        Player player = this.mc.player;

        if(player != null && player.getId() == PlayerData.getPlayerId()){
            double defense = PlayerData.getPlayerDefense();
            double magicDefense = PlayerData.getPlayerMagicDefense();
            double rendererDefense = defense + magicDefense;
            if (rendererDefense > 0) {
                this.mc.font.draw(ms,doubleToString(rendererDefense), left + 12, top + 2, -1);
            }

            scale = getInvertedScale();
            ms.scale(scale, scale, scale);
        }
    }

    @Override
    public int getPosX(int scaledWidth) {
        return (int) ((scaledWidth / 2 - 91)*getInvertedScale() + this.settings.getPositionValue(Settings.armor_position)[0]);
    }

    @Override
    public int getPosY(int scaledHeight) {
        return (int) ((scaledHeight - 29 + 2)*getInvertedScale() - getHeight(scaledHeight) + this.settings.getPositionValue(Settings.armor_position)[1]);
    }

    public int getHeight(int scaledHeight) {
        return 10;
    }

    @Override
    public float getScale() {
        return 1;
    }

}