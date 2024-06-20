package net.cheto97.rpgcraftmod.ModHud.parts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.util.ModChatFormatting;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static net.cheto97.rpgcraftmod.util.HudRpgUtils.*;

public class compassWidget {
    private static final ResourceLocation COMPASS_BACKGROUND_TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/compass_background.png");
    public compassWidget(PoseStack ms){
        drawElement(ms);
    }
    private static void drawElement(PoseStack ms) {
        Minecraft mc = Minecraft.getInstance();
        int rotation = Math.round(((mc.gameRenderer.getMainCamera().getYRot() % 360) / 360) * 200);
        int scaledWidth = mc.getWindow().getGuiScaledWidth();
        int scaledHeight = mc.getWindow().getGuiScaledHeight();
        int initialX = getPositionFromMid(scaledWidth,-14);
        int initialY = getPosition(scaledHeight,1);
        int endX = getPositionFromMid(scaledWidth,-28);
        int endY = getPosition(scaledHeight,7);
        int posY = getPosition(scaledHeight,3);


        int nColor = 0xFF0008;
        int eColor = 0xFFF020;
        int sColor = 0x2522FF;
        int wColor = 0x34FF2C;

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, COMPASS_BACKGROUND_TEXTURE);
        Gui.blit(ms, initialX, initialY, 0, 0, endX, endY, endX, endY);
        RenderSystem.disableBlend();



        if (rotation < 0) rotation = 200 + rotation;
        if (rotation > 0 && rotation <= 100) {
            Gui.drawCenteredString(ms, mc.font, "W", getPositionFromMid(scaledWidth, 8) - rotation, posY, wColor);
        }
        if (rotation > 25 && rotation <= 125) {
            Gui.drawCenteredString(ms, mc.font, ".", getPositionFromMid(scaledWidth, 12) - rotation + 1, posY, getDynamicChangedColor(wColor,nColor,15));
        }
        if (rotation > 50 && rotation <= 150) {
            Gui.drawCenteredString(ms, mc.font, "N", getPositionFromMid(scaledWidth, 16) - rotation + 1, posY, nColor);
        }
        if (rotation > 75 && rotation <= 175) {
            Gui.drawCenteredString(ms, mc.font, ".", getPositionFromMid(scaledWidth, 20) - rotation + 2, posY, getDynamicChangedColor(nColor,eColor,15));
        }
        if (rotation > 100 && rotation <= 200) {
            Gui.drawCenteredString(ms, mc.font, "E", getPositionFromMid(scaledWidth, 25) - rotation, posY, eColor);
        }
        if (rotation >= 125) {
            Gui.drawCenteredString(ms, mc.font, ".", getPositionFromMid(scaledWidth, 29) - rotation, posY, getDynamicChangedColor(eColor,sColor,15));
        }
        else if (rotation <= 25) {
            Gui.drawCenteredString(ms, mc.font, ".", getPositionFromMid(scaledWidth, -4) - rotation - 1, posY, getDynamicChangedColor(eColor,sColor,15));
        }
        if (rotation >= 150) {
            Gui.drawCenteredString(ms, mc.font, "S", getPositionFromMid(scaledWidth, 33) - rotation + 1, posY, sColor);
        }
        else if (rotation <= 50) {
            Gui.drawCenteredString(ms, mc.font, "S", getPositionFromMid(scaledWidth, 0) - rotation, posY, sColor);
        }
        if (rotation >= 175) {
            Gui.drawCenteredString(ms, mc.font, ".", getPositionFromMid(scaledWidth, 37) - rotation, posY, getDynamicChangedColor(sColor,wColor,15));
        }
        else if (rotation <= 75) {
            Gui.drawCenteredString(ms, mc.font, ".", getPositionFromMid(scaledWidth, 4) - rotation - 1, posY, getDynamicChangedColor(sColor,wColor,15));
        }
        
        if(mc.player != null){
            int[] pos = getPlayerPosition(mc.player);
            String data = mc.fpsString;
            int spaceIndex = data.indexOf(' ');
            String fps = spaceIndex != -1 ? data.substring(0, spaceIndex) : "0";

            MutableComponent xComponent = Component.literal("X: " + pos[0]).withStyle(ChatFormatting.GREEN);
            MutableComponent yComponent = Component.literal("Y: " + pos[1]).withStyle(ChatFormatting.GREEN);
            MutableComponent zComponent = Component.literal("Z: " + pos[2]).withStyle(ChatFormatting.GREEN);
            MutableComponent fpsComponent = Component.literal("FPS: " + fps).withStyle(style -> style.withColor(getColor(fps)));

            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms, mc.font, xComponent, getPositionFromMid(scaledWidth, 50), getPosition(scaledHeight,18), -1);
            Gui.drawCenteredString(ms, mc.font, yComponent, getPositionFromMid(scaledWidth, 60), getPosition(scaledHeight,18), -1);
            Gui.drawCenteredString(ms, mc.font, zComponent, getPositionFromMid(scaledWidth, 70), getPosition(scaledHeight,18), -1);
            Gui.drawCenteredString(ms, mc.font, fpsComponent, getPositionFromMid(scaledWidth, 60), getPosition(scaledHeight,24), -1);
            ms.scale(2f, 2f, 2f);
        }
    }
    private static Integer getColor(String fps){
        return ModChatFormatting.fromHexColor(getFPSColor(fps)).getColor();
    }
}
