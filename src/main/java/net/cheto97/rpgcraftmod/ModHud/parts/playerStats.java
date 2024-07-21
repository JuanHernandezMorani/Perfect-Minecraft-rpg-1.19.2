package net.cheto97.rpgcraftmod.ModHud.parts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.util.NumberUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class playerStats {
    private static Minecraft mc;
    public playerStats(PoseStack ms){
        draw(ms);
        mc = Minecraft.getInstance();
    }

    private static final ResourceLocation bg = setTexture("gui/bc/player_bg");
    private static final ResourceLocation mana_bar = setTexture("gui/hud/mana_bar");
    private static final ResourceLocation food_bar = setTexture("gui/hud/food_bar");
    private static final ResourceLocation hunger_bar = setTexture("gui/hud/hunger_bar");
    private static final ResourceLocation health_bar = setTexture("gui/hud/health_bar");
    private static final ResourceLocation absorption_health_bar = setTexture("gui/hud/absorption_health_bar");
    private static final ResourceLocation poison_bar = setTexture("gui/hud/poison_health_bar");
    private static final ResourceLocation frozen_bar = setTexture("gui/hud/frozen_health_bar");
    private static final ResourceLocation wither_bar = setTexture("gui/hud/wither_health_bar");
    private static final ResourceLocation levelContainer = setTexture("gui/hud/level_display");
    private static final ResourceLocation oxygen_bar = setTexture("gui/hud/oxygen_bar");
    private static final ResourceLocation empty_bar = setTexture("gui/hud/empty_bar");

    private static boolean activateOxygen(Player player){
        return player.getAirSupply() < player.getMaxAirSupply();
    }
    private static void draw(PoseStack ms){
        if(mc == null) return;
        if(mc.player != null && mc.player.getId() == PlayerData.getPlayerId() && !mc.player.isCreative() && !mc.player.isSpectator()){
            int scaledWidth = mc.getWindow().getGuiScaledWidth();
            int scaledHeight = mc.getWindow().getGuiScaledHeight();

            double life = PlayerData.getPlayerLife();
            double maxLife = PlayerData.getPlayerLifeMax();

            double mana = PlayerData.getPlayerMana();
            double maxMana = PlayerData.getPlayerManaMax();

            double food = mc.player.getFoodData().getFoodLevel();
            double maxFood = 20D;

            int playerClass = PlayerData.getPlayerClass();
            ResourceLocation class_bar;

            switch (playerClass){
                case 1 -> class_bar = setTexture("gui/hud/archer");
                case 2 -> class_bar = setTexture("gui/hud/mage");
                case 3 -> class_bar = setTexture("gui/hud/warrior");
                case 4 -> class_bar = setTexture("gui/hud/assassin");
                case 5 -> class_bar = setTexture("gui/hud/balanced");
                case 6 -> class_bar = setTexture("gui/hud/beast_tamer");
                case 7 -> class_bar = setTexture("gui/hud/priest");
                case 8 -> class_bar = setTexture("gui/hud/knight");
                default -> class_bar = setTexture("gui/hud/player_class");
            }

            int oxygen = mc.player.getAirSupply();
            int maxOxygen = mc.player.getMaxAirSupply();

            int playerLevel = PlayerData.getPlayerLevel();

            int bgX = getPosition(scaledWidth,1);
            int bgY = getPosition(scaledHeight,1);

            drawElement(ms, bg, bgX, bgY, 122, 34);

            drawElement(ms, levelContainer,bgX + 40, bgY + 32,levelWidth(playerLevel),5);
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms,mc.font,""+playerLevel,(((bgX + 40)+((levelWidth(playerLevel))/2)) * 2) + 1, ((bgY + 32) * 2) + 1,-1);
            ms.scale(2.0f, 2.0f, 2.0f);

            drawLifeBar(mc.player, ms, bgX + 41, bgY + 6, life, maxLife);
            Component lifeBarNumbers = Component.literal(getLocalString(life,maxLife));
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms,mc.font,lifeBarNumbers,(((bgX + 41)+((statWidth((life+"")))/2)) * 2)+23,((bgY + 6) * 2) + 1,0x118024);
            ms.scale(2f, 2f, 2f);


            drawManaBar(mc.player, ms, bgX + 41, bgY + 14, mana, maxMana);
            Component manaBarNumbers = Component.literal(getLocalString(mana,maxMana));
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms,mc.font,manaBarNumbers,(((bgX + 41)+((statWidth((mana +"")))/2)) * 2)+23,((bgY + 14) * 2) + 1,0x118024);
            ms.scale(2f, 2f, 2f);


            drawFoodBar(mc.player, ms, bgX + 41, bgY + 22,food,maxFood);
            Component foodBarNumbers = Component.literal(getLocalString(food,maxFood));
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms,mc.font,foodBarNumbers,(((bgX + 41)+((statWidth((food +"")))/2)) * 2)+23,((bgY + 22) * 2) + 1,0x118024);
            ms.scale(2f, 2f, 2f);

            drawElement(ms, class_bar, bgX + 2, bgY + 1, 32, 32);

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
        return mc.font.width("-" + level + "-");
    }
    private static int statWidth(String stat){
        return mc.font.width(stat);
    }
    private static void drawManaBar(Player player, PoseStack ms, int x, int y, double mana, double maxMana){
        if(player != null){
            drawBar(mana_bar,ms,x,y,mana, maxMana);
        }
    }
    private static void drawFoodBar(Player player, PoseStack ms, int x, int y, double food, double maxFood){
        ResourceLocation texture;
        food = Math.min(Math.max(food, 0),maxFood);
        boolean[] isHunger = new boolean[1];
        if(player != null){
            player.getActiveEffects().forEach(state -> {
                isHunger[0] = state.getEffect() == MobEffects.HUNGER;
            });

            texture = isHunger[0] ? hunger_bar : food_bar;
            drawBar(texture,ms,x,y,food,maxFood);
        }
    }
    private static void drawLifeBar(Player player, PoseStack ms, int x, int y, double life, double maxLife){
        ResourceLocation texture;
        int[] container = new int[4];
        if(player != null){

            player.getActiveEffects().forEach(state -> {
                container[0] = state.getEffect() == MobEffects.ABSORPTION ? 1 : -1;
                container[1] = state.getEffect() == MobEffects.POISON ? 1 : -1;
                container[2] = state.getEffect() == MobEffects.WITHER ? 1 : -1;
            });
            texture = player.isFreezing() || player.isFullyFrozen() ? frozen_bar : container[2] > 0 ? wither_bar : container[1] > 0 ? poison_bar : container[0] > 1 ? absorption_health_bar : health_bar;

            drawBar(texture,ms,x,y,life,maxLife);
        }
    }
    private static void drawBar(ResourceLocation texture, PoseStack ms, int x, int y, double current, double max){
        int width = 72;
        int widthSlice = (int)((width * current) / max);
        int height = 6;


        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, empty_bar);
        Gui.blit(ms, x, y, 0, 0, width, height, width, height);
        RenderSystem.disableBlend();

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, texture);
        Gui.blit(ms, x, y, 0, 0, widthSlice, height, widthSlice, height);
        RenderSystem.disableBlend();
    }
    private static String getLocalString(double current, double max){
        return NumberUtils.doubleToString(current) +" / "+ NumberUtils.doubleToString(max);
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
