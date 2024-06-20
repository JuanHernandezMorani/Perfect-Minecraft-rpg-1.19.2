package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.Elements.vanilla.HudElementCompassVanilla;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.util.ModChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static net.cheto97.rpgcraftmod.util.HudRpgUtils.*;

public class HudElementCompassRPG extends HudElementCompassVanilla {
    private final ResourceLocation COMPASS_BACKGROUND_TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/compass_background.png");

    public HudElementCompassRPG() {
        super();
    }

    @Override
    public boolean checkConditions() {
        return true;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int posY = getPositionY(scaledHeight,3);

        int initialX = getPositionX(scaledWidth,-20);
        int initialY = getPositionY(scaledHeight,1);
        int endX = getPositionX(scaledWidth,100);
        int endY = getPositionY(scaledHeight,6);

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, COMPASS_BACKGROUND_TEXTURE);
        Gui.blit(ms, initialX, initialY, 0, 0, endX, endY, endX, endY);
        RenderSystem.disableBlend();

        int rotation = Math.round(((this.mc.gameRenderer.getMainCamera().getYRot() % 360) / 360) * 200);

        if (rotation < 0) rotation = 200 + rotation;
        if (rotation > 0 && rotation <= 100) {
            Gui.drawCenteredString(ms, this.mc.font, "W", getPositionX(scaledWidth, 20) - rotation, posY, -1);
        }
        if (rotation > 25 && rotation <= 125) {
            Gui.drawCenteredString(ms, this.mc.font, "NW", getPositionX(scaledWidth, 30) - rotation, posY, -1);
        }
        if (rotation > 50 && rotation <= 150) {
            Gui.drawCenteredString(ms, this.mc.font, "N", getPositionX(scaledWidth, 40) - rotation, posY, 0xE60909);
        }
        if (rotation > 75 && rotation <= 175) {
            Gui.drawCenteredString(ms, this.mc.font, "NE", getPositionX(scaledWidth, 50) - rotation, posY, -1);
        }
        if (rotation > 100 && rotation <= 200) {
            Gui.drawCenteredString(ms, this.mc.font, "E", getPositionX(scaledWidth, 60) - rotation, posY, -1);
        }
        if (rotation >= 125) {
            Gui.drawCenteredString(ms, this.mc.font, "SE", getPosition(scaledWidth, 70) - rotation, posY, -1);
        }
        else if (rotation <= 25) {
            Gui.drawCenteredString(ms, this.mc.font, "SE", getPositionX(scaledWidth, -10) - rotation, posY, -1);
        }
        if (rotation >= 150) {
            Gui.drawCenteredString(ms, this.mc.font, "S", getPosition(scaledWidth, 80) - rotation, posY, -1);
        }
        else if (rotation <= 50) {
            Gui.drawCenteredString(ms, this.mc.font, "S", getPositionX(scaledWidth, 0) - rotation, posY, -1);
        }
        if (rotation >= 175) {
            Gui.drawCenteredString(ms, this.mc.font, "SW", getPosition(scaledWidth, 90) - rotation, posY, -1);
        }
        else if (rotation <= 75) {
            Gui.drawCenteredString(ms, this.mc.font, "SW", getPositionX(scaledWidth, 10) - rotation, posY, -1);
        }

        if (this.settings.getBoolValue(Settings.enable_compass_coordinates)) {
            int[] pos = getPlayerPos();
            String data = Minecraft.getInstance().fpsString;
            int spaceIndex = data.indexOf(' ');
            String fps = spaceIndex != -1 ? data.substring(0, spaceIndex) : "0";

            MutableComponent xComponent = Component.literal("X: " + pos[0]);
            MutableComponent yComponent = Component.literal("Y: " + pos[1]);
            MutableComponent zComponent = Component.literal("Z: " + pos[2]);
            MutableComponent fpsComponent = Component.literal("FPS: " + fps).withStyle(style -> style.withColor(getColor(fps)));

            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawString(ms, this.mc.font, xComponent, getPositionX(scaledWidth, -3) - this.mc.font.width(String.valueOf(pos[0])), getPositionY(scaledHeight,14), -1);
            Gui.drawCenteredString(ms, this.mc.font, yComponent, getPositionX(scaledWidth, 0), getPositionY(scaledHeight,14), -1);
            Gui.drawString(ms, this.mc.font, zComponent, getPositionX(scaledWidth, 6) - this.mc.font.width(String.valueOf(pos[2])), getPositionY(scaledHeight,14), -1);
            Gui.drawCenteredString(ms, this.mc.font, fpsComponent, getPositionX(scaledWidth, 0), getPositionY(scaledHeight,20), -1);
            ms.scale(2f, 2f, 2f);
        }
    }

    @Override
    public int getPosY(int scaledHeight) {
        return (this.settings.getPositionValue(Settings.compass_position)[1]);
    }

    @Override
    public int getPosX(int scaledWidth) {
        return (scaledWidth / 2) + this.settings.getPositionValue(Settings.compass_position)[0];
    }

    public int getHeight(int scaledHeight) {
        return 6;
    }

    @Override
    public float getScale() {
        return 1;
    }

    private Integer getColor(String fps){
        return ModChatFormatting.fromHexColor(getFPSColor(fps)).getColor();
    }
}