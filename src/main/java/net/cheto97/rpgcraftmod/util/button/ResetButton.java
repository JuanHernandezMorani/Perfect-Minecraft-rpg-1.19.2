package net.cheto97.rpgcraftmod.util.button;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ResetButton extends CustomButton{
    public ResetButton(int x, int y, int width, int height,int iconWidth, int iconHeight, Component message, OnPress action) {
        super(x, y, width, height, message, action);
        this.width = iconWidth;
        this.height = iconHeight;
    }

    @Override
    protected ResourceLocation getDisabledTexture() {
        return new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/button/reset_disabled.png");
    }

    @Override
    protected ResourceLocation getHoveredTexture() {
        return new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/button/reset_hovered.png");
    }

    @Override
    protected ResourceLocation getButtonTexture() {
        return new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/button/reset_icon.png");
    }

    @Override
    protected int getTextureWidth() {
        return 32;
    }

    @Override
    protected int getTextureHeight() {
        return 32;
    }

    @Override
    protected int getSourceX() {
        return 0;
    }

    @Override
    protected int getSourceY() {
        return 0;
    }

    @Override
    protected int getIconWidth() {
        return this.width;
    }

    @Override
    protected int getIconHeight() {
        return this.height;
    }
}
