package net.cheto97.rpgcraftmod.ModHud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class HudElement {
    public static final int COLOR_RED = 0xC10000;
    public static final int COLOR_WHITE = 0xF2F2F2;
    public static final int COLOR_ORANGE = 0xFF8400;
    public static final int COLOR_GREEN = 0x005BC2;
    public static final int COLOR_BLUE = 0x005BC2;
    public static final int COLOR_BLACK = 0x292929;
    public static final int COLOR_GREY = 0x8A8A8A;
    public static final int COLOR_YELLOW = 0xEEEE00;
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
        if(checkConditions()){
            this.drawElement(gui, ms, zLevel, partialTicks, scaledWidth, scaledHeight);
        }
    }
    public abstract void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight);
    public int getPosX(int scaledWidth) {
        return this.posX;
    }
    public int getPosY(int scaledHeight) {
        return this.posY;
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
    public HudType getType() {
        return this.type;
    }
    public boolean checkConditions() {
        return  this.mc.player != null;
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

        colorOffset = colorPart << 16;
        colorPart = (color >> 8 & 255);
        colorPart += (offset >> 8 & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;

        colorOffset += colorPart << 8;
        colorPart = (color & 255);
        colorPart += (offset & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
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
        assert player != null;
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
    protected void drawStringWithBackground(PoseStack ms, String text, int posX, int posY) {
        this.mc.font.draw(ms, text, posX + 1, posY, 0);
        this.mc.font.draw(ms, text, posX - 1, posY, 0);
        this.mc.font.draw(ms, text, posX, posY + 1, 0);
        this.mc.font.draw(ms, text, posX, posY - 1, 0);
        this.mc.font.draw(ms, text, posX, posY, -1);
        RenderSystem.enableBlend();
    }
}
