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
    private static final Minecraft mc = Minecraft.getInstance();
    private static final int scaledWidth = mc.getWindow().getGuiScaledWidth();
    private static final int scaledHeight = mc.getWindow().getGuiScaledHeight();
    public compassWidget(PoseStack ms){
        drawElement(ms);
    }
    private static void drawElement(PoseStack ms) {
        int initialX = getPositionX(scaledWidth,-35);
        int initialY = getPositionY(scaledHeight,1);
        int endX = getPositionX(scaledWidth,15);
        int endY = getPositionY(scaledHeight,6);

        int posY = getPositionY(scaledHeight,8);

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, COMPASS_BACKGROUND_TEXTURE);
        Gui.blit(ms, initialX, initialY, 0, 0, endX, endY, endX, endY);
        RenderSystem.disableBlend();

        int rotation = Math.round(((mc.gameRenderer.getMainCamera().getYRot() % 360) / 360) * 200);

        if (rotation < 0) rotation = 200 + rotation;
        if (rotation > 0 && rotation <= 100) {
            Gui.drawCenteredString(ms, mc.font, "W", getPositionX(scaledWidth, -7) - rotation, posY, -1);
        }
        if (rotation > 25 && rotation <= 125) {
            Gui.drawCenteredString(ms, mc.font, "NW", getPositionX(scaledWidth, -3) - rotation, posY, -1);
        }
        if (rotation > 50 && rotation <= 150) {
            Gui.drawCenteredString(ms, mc.font, "N", getPositionX(scaledWidth, 2) - rotation, posY, 0xE60909);
        }
        if (rotation > 75 && rotation <= 175) {
            Gui.drawCenteredString(ms, mc.font, "NE", getPositionX(scaledWidth, 6) - rotation, posY, -1);
        }
        if (rotation > 100 && rotation <= 200) {
            Gui.drawCenteredString(ms, mc.font, "E", getPositionX(scaledWidth, 11) - rotation, posY, -1);
        }
        if (rotation >= 125) {
            Gui.drawCenteredString(ms, mc.font, "SE", getPositionX(scaledWidth, 16) - rotation, posY, -1);
        }
        else if (rotation <= 25) {
            Gui.drawCenteredString(ms, mc.font, "SE", getPositionX(scaledWidth, -20) - rotation, posY, -1);
        }
        if (rotation >= 150) {
            Gui.drawCenteredString(ms, mc.font, "S", getPositionX(scaledWidth, 21) - rotation, posY, -1);
        }
        else if (rotation <= 50) {
            Gui.drawCenteredString(ms, mc.font, "S", getPositionX(scaledWidth, -25) - rotation, posY, -1);
        }
        if (rotation >= 175) {
            Gui.drawCenteredString(ms, mc.font, "SW", getPositionX(scaledWidth, 25) - rotation, posY, -1);
        }
        else if (rotation <= 75) {
            Gui.drawCenteredString(ms, mc.font, "SW", getPositionX(scaledWidth, -30) - rotation, posY, -1);
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
            Gui.drawString(ms, mc.font, xComponent, getPositionX(scaledWidth, -3), getPositionY(scaledHeight,14), -1);
            Gui.drawCenteredString(ms, mc.font, yComponent, getPositionX(scaledWidth, 0), getPositionY(scaledHeight,14), -1);
            Gui.drawString(ms, mc.font, zComponent, getPositionX(scaledWidth, 6) - mc.font.width(String.valueOf(pos[2])), getPositionY(scaledHeight,14), -1);
            Gui.drawCenteredString(ms, mc.font, fpsComponent, getPositionX(scaledWidth, 0), getPositionY(scaledHeight,20), -1);
            ms.scale(2f, 2f, 2f);
        }
    }
    private static Integer getColor(String fps){
        return ModChatFormatting.fromHexColor(getFPSColor(fps)).getColor();
    }
}
