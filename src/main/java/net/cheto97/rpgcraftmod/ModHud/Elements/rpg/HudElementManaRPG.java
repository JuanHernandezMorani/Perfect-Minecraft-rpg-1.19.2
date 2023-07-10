package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.customstats.Mana;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerDataSyncPacket;
import net.cheto97.rpgcraftmod.providers.ManaProvider;
import net.minecraft.client.gui.Gui;

import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;


public class HudElementManaRPG extends HudElement {
    public HudElementManaRPG() {
        super(HudType.MANA, 0, 0, 0, 0, true);
        parent = HudType.WIDGET;
    }
    @Override
    public boolean checkConditions() {
        assert this.mc.player != null;
        return !this.mc.player.isCreative();
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.player != null && checkConditions()){
            double mana = 0;
            double manaMax = 0;

            if(PlayerData.getPlayerId() == this.mc.player.getId()){
                mana = PlayerData.getPlayerMana();
                manaMax = PlayerData.getPlayerManaMax();
            }

            bind(INTERFACE);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

            int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
            int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];

            gui.blit(ms, posX, posY, 110, 100, (int) (110.0D * (mana / manaMax)), 12);

            String manaString = doubleToString(mana) + " / " + doubleToString(manaMax);
            if (this.settings.getBoolValue(Settings.show_numbers_mana))
                Gui.drawCenteredString(ms, this.mc.font, manaString, posX + 57, posY + 2, -1);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            bind(Gui.GUI_ICONS_LOCATION);
        }
    }
}