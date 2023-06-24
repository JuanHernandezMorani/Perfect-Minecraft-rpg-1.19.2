package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.modsystem.Experience;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.cheto97.rpgcraftmod.providers.ExperienceProvider;
import net.minecraft.client.gui.Gui;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

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
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int posX = this.settings.getPositionValue(Settings.rpgexperience_position)[0];
        int posY = this.settings.getPositionValue(Settings.rpgexperience_position)[1];
        bind(EMPTY_EXP_BAR);
        Player player = this.mc.player;

        if(player != null && player.getId() == PlayerData.getPlayerId()){
            exp = PlayerData.getPlayerExperience();
            player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(exp ->{
                expCap = exp.experienceNeeded();
            });
            double full = ((scaledWidth - posX)) / expCap;

            gui.blit(ms, posX, scaledHeight - 7 + posY, scaledWidth, 0, 0,0);

            bind(FILLED_EXP_BAR);
            gui.blit(ms, 1 + posX, scaledHeight - 6 + posY, (int) (exp * full), 4,0,0);

            String stringExp = "";

            int width2 = this.mc.font.width(stringExp) / 2;
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms, this.mc.font, stringExp, 6 + width2 + posX * 2, (scaledHeight - 12) * 2 - 1 + posY * 2, -1);
            ms.scale(2f, 2f, 2f);
        }
    }
}