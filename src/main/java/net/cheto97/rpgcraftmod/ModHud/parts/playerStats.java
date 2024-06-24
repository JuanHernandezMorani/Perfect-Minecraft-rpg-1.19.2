package net.cheto97.rpgcraftmod.ModHud.parts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class playerStats {
    private static Minecraft mc;
    public playerStats(PoseStack ms, Minecraft mcInstance){
        draw(ms);
        mc = mcInstance;
    }

    private static final ResourceLocation bg = setTexture("gui/bc/player_bg");
    private static final ResourceLocation mana_bar = setTexture("gui/hud/mana_bar");
    private static final ResourceLocation food_bar = setTexture("gui/hud/food_bar");
    private static final ResourceLocation health_bar = setTexture("gui/hud/health_bar");
    private static final ResourceLocation absorption_health_bar = setTexture("gui/hud/absorption_health_bar");
    private static final ResourceLocation poison_bar = setTexture("gui/hud/poison_bar");
    private static final ResourceLocation frozen_bar = setTexture("gui/hud/frozen_bar");
    private static final ResourceLocation wither_bar = setTexture("gui/hud/wither_bar");
    private static final ResourceLocation class_bar = setTexture("gui/hud/player_class");
    private static final ResourceLocation levelContainer = setTexture("gui/hud/level_display");
    private static final ResourceLocation oxygen_bar = setTexture("gui/hud/oxygen_bar");
    private static final ResourceLocation empty_bar = setTexture("gui/hud/empty_bar");


    private static boolean show() {
        return  mc.player != null && !mc.player.isCreative() && !mc.player.isSpectator();
    }
    private static boolean activateOxygen(Player player){
        return player.getAirSupply() < player.getMaxAirSupply();
    }
    private static void draw(PoseStack ms){
        if(mc.player != null && mc.player.getId() == PlayerData.getPlayerId() && show()){
            int scaledWidth = mc.getWindow().getGuiScaledWidth();
            int scaledHeight = mc.getWindow().getGuiScaledHeight();

            double life = PlayerData.getPlayerLife();
            double maxLife = PlayerData.getPlayerLifeMax();

            double mana = PlayerData.getPlayerMana();
            double maxMana = PlayerData.getPlayerManaMax();

            int food = mc.player.getFoodData().getFoodLevel();

            int playerClass = PlayerData.getPlayerClass();

            int oxygen = mc.player.getAirSupply();
            int maxOxygen = mc.player.getMaxAirSupply();

            int playerLevel = PlayerData.getPlayerLevel();

            drawElement(ms, bg, getPosition(scaledWidth,2), getPosition(scaledHeight,2), 100, 50);
            //drawElement(ms, levelContainer,getPosition(scaledWidth,5), getPosition(scaledHeight,12),levelWidth(playerLevel),10);
            //drawLifeBar(mc.player, ms, getPosition(scaledWidth,5), getPosition(scaledHeight,4), life, maxLife);
            //drawElement(ms, class_bar,getPosition(scaledWidth,3), getPosition(scaledHeight,7),getPosition(scaledWidth,5), getPosition(scaledHeight,3));

            if(activateOxygen(mc.player)){
                int oxygenPositionX = getPosition(scaledWidth,50);
                int oxygenPositionY = getPosition(scaledHeight,50);
                
                
                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.enableBlend();
                RenderSystem.setShaderTexture(0, empty_bar);
                Gui.blit(ms, oxygenPositionX, oxygenPositionY - 50, 0, 0, 141, 10, 141, 10);
                RenderSystem.disableBlend();

                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.enableBlend();
                RenderSystem.setShaderTexture(0, oxygen_bar);
                Gui.blit(ms, oxygenPositionX, oxygenPositionY - 50, 0, 0, 141, 10, (int)(141D * (oxygen / maxOxygen)), 10);
                RenderSystem.disableBlend();
            }
        }
    }
    private static int levelWidth(int level){
        return mc.font.width("---" + level + "---");
    }
    private static void drawLifeBar(Player player, PoseStack ms, int x, int y, double life, double maxLife){
        ResourceLocation texture;
        int[] container = new int[4];
        if(player != null){
            int scaledWidth = mc.getWindow().getGuiScaledWidth();

            int width = (int)(100 * (life / maxLife));
            int height = 10;

            player.getActiveEffects().forEach(state -> {
                container[0] = state.getEffect() == MobEffects.ABSORPTION ? 1 : -1;
                container[1] = state.getEffect() == MobEffects.POISON ? 1 : -1;
                container[2] = state.getEffect() == MobEffects.WITHER ? 1 : -1;
            });
            texture = player.isFreezing() || player.isFullyFrozen() ? frozen_bar : container[2] > 0 ? wither_bar : container[1] > 0 ? poison_bar : container[0] > 1 ? absorption_health_bar : health_bar;

            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.enableBlend();
            RenderSystem.setShaderTexture(0, texture);
            Gui.blit(ms, x, y, 0, 0, width, height, width, height);
            RenderSystem.disableBlend();
        }
    }
    private static void drawElement(PoseStack ms, ResourceLocation texture, int x, int y, int width, int height){
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, texture);
        Gui.blit(ms, x, y, 0, 0, width, height, width, height);
        RenderSystem.disableBlend();
    }
    private static ResourceLocation setTexture(String name){
        return new ResourceLocation(RpgcraftMod.MOD_ID,"textures/" + name + ".png");
    }
    private static int getPosition(int scaled, int position){
        return Math.round((float)((scaled / 100) * position));
    }
}
