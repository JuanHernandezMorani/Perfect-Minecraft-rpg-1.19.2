package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.modsystem.Experience;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.cheto97.rpgcraftmod.providers.ExperienceProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;
import static net.minecraft.client.gui.GuiComponent.blit;

public class HudElementCustomExperienceRPG extends HudElement {
    double exp;
    double expCap;

    protected static final ResourceLocation EMPTY_EXP_BAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/empty_exp_bar.png");
    protected static final ResourceLocation FILLED_EXP_BAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/exp_bar.png");

    public HudElementCustomExperienceRPG() {
        super(HudType.RPGEXPERIENCE, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        assert this.mc.player != null;
        return !this.mc.player.isCreative() && !this.mc.player.isSpectator();
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
        bind(EMPTY_EXP_BAR);
        Player player = this.mc.player;
        if(player != null && player.getId() == PlayerData.getPlayerId()){
            exp = PlayerData.getPlayerExperience();
            expCap = PlayerData.getExpNeed();

            double full = ((double) (scaledWidth - 2)) / expCap;

            gui.blit(ms, 0, scaledHeight - 11 + posY, 0, 0, scaledWidth,16);

            bind(FILLED_EXP_BAR);
            gui.blit(ms, 0, scaledHeight - 11 + posY, 0,0,(int)(exp * full),16);

            String stringExp = (PlayerData.getPlayerExperience() <= 0 ? "0" : doubleToString(PlayerData.getPlayerExperience())) + " / " + PlayerData.getExpNeed();

            int width2 = this.mc.font.width(stringExp) / 2;
            int textPosY = (scaledHeight - 12) * 2 - 12 + posY * 2;
            int backgroundColor = 0x05CCCCCC;
            int backgroundWidth = this.mc.font.width(stringExp) + 4;
            int backgroundHeight = this.mc.font.lineHeight + 2;
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.fill(ms, width2 - 4, textPosY - 2, width2 + backgroundWidth, textPosY + backgroundHeight, backgroundColor);
            Gui.drawString(ms, this.mc.font, Component.literal(stringExp).withStyle(ChatFormatting.DARK_AQUA), width2, (scaledHeight - 12) * 2 - 12 + posY * 2, -1);
            ms.scale(2f, 2f, 2f);
        }
    }
}