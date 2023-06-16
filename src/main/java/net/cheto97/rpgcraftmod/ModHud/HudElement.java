package net.cheto97.rpgcraftmod.ModHud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class HudElement {
    public static final int COLOR_RED = 0xC10000;
    public static final int COLOR_PINK = 0xFF69B4;
    public static final int COLOR_BROWN = 0x8b4513;
    public static final int COLOR_WHITE = 0xF2F2F2;
    public static final int COLOR_ORANGE = 0xFF8400;
    public static final int COLOR_GREEN = 0x005BC2;
    public static final int COLOR_PURPLE = 0xA400F0;
    public static final int COLOR_BLUE = 0x005BC2;
    public static final int COLOR_AQUA = 0x00FFFF;
    public static final int COLOR_BLACK = 0x292929;
    public static final int COLOR_GREY = 0x8A8A8A;
    public static final int COLOR_YELLOW = 0xEEEE00;
    public static final int[] COLOR_DEFAULT = {0x4C4C4C, 0x3D3D3D};
    protected static final ResourceLocation INTERFACE = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/player_hud.png");

    public static final int OFFSET_PERCENT = 25;

    public static final int OFFSET_PREVIEW = 0x5A5A5A;
    protected int posX;
    protected int posY;
    protected final int defaultPosX;
    protected final int defaultPosY;
    protected int elementWidth;
    protected int elementHeight;
    protected boolean moveable;
    protected HudType type;
    protected Minecraft mc;
    protected RpgcraftMod rpgHud;
    protected Settings settings;
    protected float scale;
    protected float scaleInverted;
    public HudType parent;
    public HudElement(HudType type, int posX, int posY, int width, int height, boolean moveable) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.defaultPosX = posX;
        this.defaultPosY = posY;
        this.elementWidth = width;
        this.elementHeight = height;
        this.moveable = moveable;
        this.mc = Minecraft.getInstance();
        this.rpgHud = RpgcraftMod.instance;
        this.settings = this.rpgHud.settings;
        this.scale = 1f;
        this.scaleInverted = 1f / this.scale;
        this.parent = type;
    }
    public void draw(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        this.drawElement(gui, ms, zLevel, partialTicks, scaledWidth, scaledHeight);
    }
    public abstract void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight);
    public int getPosX(int scaledWidth) {
        return this.posX;
    }
    public int getPosY(int scaledHeight) {
        return this.posY;
    }
    public int getWidth(int scaledWidth) {
        return this.elementWidth;
    }
    public int getHeight(int scaledHeight) {
        return this.elementHeight;
    }
    public float getScale() {
        return 1f;
    }
    public float getInvertedScale() {
        return 1f / getScale();
    }
    public boolean isMoveable() {
        return this.moveable;
    }
    public HudType getType() {
        return this.type;
    }
    public boolean setPos(int posX, int posY) {
        boolean xValid = false;
        boolean yValid = false;
        if (posX >= 0 && posX < (this.mc.getWindow().getGuiScaledWidth() - this.elementWidth)) {
            xValid = true;
        }
        if (posY >= 0 && posY < (this.mc.getWindow().getGuiScaledHeight() - this.elementHeight)) {
            yValid = true;
        }
        if (xValid && yValid) {
            this.posX = posX;
            this.posY = posY;
        }
        return xValid && yValid;
    }
    public void setPositionToDefault() {
        this.posX = this.defaultPosX;
        this.posY = this.defaultPosY;
    }
    public boolean checkConditions() {
        return true;
    }
    public static void drawRect(PoseStack ms, int posX, int posY, int width, int height, int color) {
        if (color == -1)
            return;
        float f3;
        if (color <= 0xFFFFFF && color >= 0)
            f3 = 1.0F;
        else
            f3 = (color >> 24 & 255) / 255.0F;
        float f = (color >> 16 & 255) / 255.0F;
        float f1 = (color >> 8 & 255) / 255.0F;
        float f2 = (color & 255) / 255.0F;
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableDepthTest();

        BufferBuilder vertexbuffer = Tesselator.getInstance().getBuilder();
        vertexbuffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        vertexbuffer.vertex(ms.last().pose(), posX, posY + height, 0).color(f, f1, f2, f3).endVertex();
        vertexbuffer.vertex(ms.last().pose(), posX + width, posY + height, 0).color(f, f1, f2, f3).endVertex();
        vertexbuffer.vertex(ms.last().pose(), posX + width, posY, 0).color(f, f1, f2, f3).endVertex();
        vertexbuffer.vertex(ms.last().pose(), posX, posY, 0).color(f, f1, f2, f3).endVertex();
        vertexbuffer.end();

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }
    protected static void drawOutline(PoseStack ms, int x, int y, int width, int height, int color) {
        drawRect(ms, x, y, width, 1, color);
        drawRect(ms, x, y + 1, 1, height - 2, color);
        drawRect(ms, x + width - 1, y + 1, 1, height - 2, color);
        drawRect(ms, x, y + height - 1, width, 1, color);
    }
    public static void drawCustomBar(PoseStack ms, int x, int y, int width, int height, double value, int colorBarLight, int colorBarDark) {
        drawCustomBar(ms, x, y, width, height, value, HudElement.COLOR_DEFAULT[0], HudElement.COLOR_DEFAULT[1], colorBarLight, colorBarDark, true, 0x000000);
    }
    public static void drawCustomBar(PoseStack ms, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark) {
        drawCustomBar(ms, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, 0x000000);
    }
    public static void drawCustomBar(PoseStack ms, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined) {
        drawCustomBar(ms, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, outlined, 0x000000);
    }
    public static void drawCustomBar(PoseStack ms, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, int colorOutline) {
        drawCustomBar(ms, x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, colorOutline);
    }
    public static void drawCustomBar(PoseStack ms, int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined, int colorOutline) {
        if (value < 0.0D) {
            value = 0.0D;
        } else if (value > 100D) {
            value = 100D;
        }

        int offset = 0;
        if (outlined)
            offset = 1;

        int filledWidth;
        filledWidth = width - (offset * 2);
        if (filledWidth < 0)
            filledWidth = 0;
        int filledHeight;
        filledHeight = height - (offset * 2);
        if (filledHeight < 0)
            filledHeight = 0;

        int percentFilled = (int) Math.round(value / 100.0D * filledWidth);

        if (outlined)
            drawOutline(ms, x, y, width, height, colorOutline);
        int halfedFilledHeight = filledHeight / 2;

        drawRect(ms, x + offset, y + offset, percentFilled, halfedFilledHeight, colorBarLight);
        drawRect(ms, x + offset, y + offset + halfedFilledHeight, percentFilled, filledHeight - halfedFilledHeight, colorBarDark);

        if (filledWidth - percentFilled > 0) {
            drawRect(ms, x + offset + percentFilled, y + offset, filledWidth - percentFilled, halfedFilledHeight, colorGroundLight);
            drawRect(ms, x + offset + percentFilled, y + offset + halfedFilledHeight, filledWidth - percentFilled, filledHeight - halfedFilledHeight, colorGroundDark);
        }
    }
    public void drawTetragon(int posX1, int posX2, int posY1, int posY2, int width1, int width2, int height1, int height2, int color) {
        if (color == -1)
            return;
        if (width1 < 0) width1 = 0;
        if (width2 < 0) width2 = 0;
        float f3;
        if (color <= 0xFFFFFF && color >= 0)
            f3 = 1.0F;
        else
            f3 = (color >> 24 & 255) / 255.0F;
        float f = (color >> 16 & 255) / 255.0F;
        float f1 = (color >> 8 & 255) / 255.0F;
        float f2 = (color & 255) / 255.0F;
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableDepthTest();
        BufferBuilder vertexbuffer = Tesselator.getInstance().getBuilder();
        vertexbuffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        vertexbuffer.vertex(posX1, (double) posY1 + height1, 0.0D).color(f, f1, f2, f3).endVertex();
        vertexbuffer.vertex((double) posX2 + width2, (double) posY2 + height2, 0.0D).color(f, f1, f2, f3).endVertex();
        vertexbuffer.vertex((double) posX1 + width1, posY2, 0.0D).color(f, f1, f2, f3).endVertex();
        vertexbuffer.vertex(posX2, posY1, 0.0D).color(f, f1, f2, f3).endVertex();
        vertexbuffer.end();

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }
    public static int offsetColorPercent(int color, int offsetPercent) {
        int colorOffset;

        int colorPart = (color >> 16 & 255);
        colorPart -= colorPart / (100 / offsetPercent);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset = colorPart << 16;
        colorPart = (color >> 8 & 255);
        colorPart -= colorPart / (100 / offsetPercent);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset += colorPart << 8;
        colorPart = (color & 255);
        colorPart -= colorPart / (100 / offsetPercent);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;
        colorOffset += colorPart;
        return colorOffset;
    }
    public static int offsetColor(int color, int offset) {
        int colorOffset;

        int colorPart = (color >> 16 & 255);
        colorPart += (offset >> 16 & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset = colorPart << 16;
        colorPart = (color >> 8 & 255);
        colorPart += (offset >> 8 & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset += colorPart << 8;
        colorPart = (color & 255);
        colorPart += (offset & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;
        colorOffset += colorPart;
        return colorOffset;
    }
    protected void bind(ResourceLocation res) {
        RenderSystem.setShaderTexture(0, res);
    }
    protected static ResourceLocation getPlayerSkin(LocalPlayer player) {
        return player.getSkinTextureLocation();
    }
    protected void renderHotbarItem(int x, int y, float partialTicks, Player player, ItemStack item) {
        if (!item.isEmpty()) {
            PoseStack PoseStack = RenderSystem.getModelViewStack();
            float f = (float) item.getPopTime() - partialTicks;

            if (f > 0.0F) {
                PoseStack.pushPose();
                float f1 = 1.0F + f / 5.0F;
                PoseStack.translate(x + 8, y + 12, 0.0F);
                PoseStack.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                PoseStack.translate((-(x + 8)), (-(y + 12)), 0.0F);
            }

            this.mc.getItemRenderer().renderGuiItem(item, x, y);

            if (f > 0.0F) {
                PoseStack.popPose();
            }

            this.mc.getItemRenderer().renderGuiItemDecorations(this.mc.font, item, x, y);
        }
    }
    protected void drawStringWithBackground(PoseStack ms, String text, int posX, int posY, int colorMain, int colorBackground) {
        this.mc.font.draw(ms, text, posX + 1, posY, colorBackground);
        this.mc.font.draw(ms, text, posX - 1, posY, colorBackground);
        this.mc.font.draw(ms, text, posX, posY + 1, colorBackground);
        this.mc.font.draw(ms, text, posX, posY - 1, colorBackground);
        this.mc.font.draw(ms, text, posX, posY, colorMain);
        RenderSystem.enableBlend();
    }
    public boolean isChatOpen() {
        return this.mc.screen instanceof ChatScreen;
    }
}
