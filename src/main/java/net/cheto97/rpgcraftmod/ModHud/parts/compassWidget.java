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
    private static Minecraft mc;
    private static final ResourceLocation COMPASS_BACKGROUND_TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/hud/compass_background.png");
    public compassWidget(PoseStack ms){
        drawElement(ms);
        mc = Minecraft.getInstance();
    }
    private static void drawElement(PoseStack ms) {
        if(mc == null) return;

        int rotation = Math.round(((mc.gameRenderer.getMainCamera().getYRot() % 360) / 360) * 200);

        int scaledWidth = mc.getWindow().getGuiScaledWidth();
        int scaledHeight = mc.getWindow().getGuiScaledHeight();
        int initialX = getPositionFromMid(scaledWidth,0);
        int posY = getPosition(scaledHeight,3);


        int nColor = 0xFF0008;
        int eColor = 0xFFF020;
        int sColor = 0x2522FF;
        int wColor = 0x34FF2C;

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, COMPASS_BACKGROUND_TEXTURE);
        Gui.blit(ms, initialX - 56, posY - 1, 0, 0, 112, 9, 112, posY + 8);
        RenderSystem.disableBlend();



        if (rotation < 0) rotation = 200 + rotation;
        if (rotation > 0 && rotation <= 100) {
            Gui.drawCenteredString(ms, mc.font, "W", initialX + 50 - rotation, posY, wColor);
        }
        if (rotation > 25 && rotation <= 125) {
            Gui.drawCenteredString(ms, mc.font, "■", initialX + 75 - rotation, posY, getDynamicChangedColor(wColor,nColor,20));
        }
        if (rotation > 50 && rotation <= 150) {
            Gui.drawCenteredString(ms, mc.font, "N", initialX + 100 - rotation, posY, nColor);
        }
        if (rotation > 75 && rotation <= 175) {
            Gui.drawCenteredString(ms, mc.font, "■", initialX + 125 - rotation, posY, getDynamicChangedColor(nColor,eColor,20));
        }
        if (rotation > 100 && rotation <= 200) {
            Gui.drawCenteredString(ms, mc.font, "E", initialX + 150 - rotation, posY, eColor);
        }
        if (rotation >= 125) {
            Gui.drawCenteredString(ms, mc.font, "■", initialX + 175 - rotation, posY, getDynamicChangedColor(eColor,sColor,20));
        }
        else if (rotation <= 25) {
            Gui.drawCenteredString(ms, mc.font, "■", initialX - 25 - rotation, posY, getDynamicChangedColor(eColor,sColor,20));
        }
        if (rotation >= 150) {
            Gui.drawCenteredString(ms, mc.font, "S", initialX + 200 - rotation, posY, sColor);
        }
        else if (rotation <= 50) {
            Gui.drawCenteredString(ms, mc.font, "S", initialX - rotation, posY, sColor);
        }
        if (rotation >= 175) {
            Gui.drawCenteredString(ms, mc.font, "■", initialX + 225 - rotation, posY, getDynamicChangedColor(sColor,wColor,20));
        }
        else if (rotation <= 75) {
            Gui.drawCenteredString(ms, mc.font, "■", initialX + 25 - rotation, posY, getDynamicChangedColor(sColor,wColor,20));
        }
        
        if(mc.player != null){
            int[] pos = getPlayerPosition(mc.player);
            String data = mc.fpsString;
            int spaceIndex = data.indexOf(' ');
            String fps = spaceIndex != -1 ? data.substring(0, spaceIndex) : "0";

            MutableComponent posComponent = Component.literal("X: " + pos[0] + " Y: " + pos[1] + " Z: " + pos[2]).withStyle(ChatFormatting.DARK_GRAY);
            MutableComponent fpsComponent = Component.literal("FPS: " + fps).withStyle(style -> style.withColor(getColor(fps)));

            ms.scale(0.625f, 0.625f, 0.625f);
            Gui.drawCenteredString(ms, mc.font, posComponent, (getPositionFromMid(scaledWidth, 34)), getPosition(scaledHeight,18), -1);
            Gui.drawCenteredString(ms, mc.font, fpsComponent, (getPositionFromMid(scaledWidth, 34)), getPosition(scaledHeight,24), -1);
            ms.scale(2f, 2f, 2f);
        }
    }
    private static Integer getColor(String fps){
        return ModChatFormatting.fromHexColor(getFPSColor(fps)).getColor();
    }
}
