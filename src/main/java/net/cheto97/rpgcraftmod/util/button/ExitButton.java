package net.cheto97.rpgcraftmod.util.button;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ExitButton extends CustomButton{
    public ExitButton(int x, int y, int width, int height, Component message, OnPress action) {
        super(x, y, width, height, message, action);
    }

    @Override
    protected ResourceLocation getDisabledTexture() {
        return new ResourceLocation(RpgcraftMod.MOD_ID,"textures/button/exit_disabled.png");
    }

    @Override
    protected ResourceLocation getHoveredTexture() {
        return new ResourceLocation(RpgcraftMod.MOD_ID,"textures/button/exit_hovered.png");
    }

    @Override
    protected ResourceLocation getButtonTexture() {
        return new ResourceLocation(RpgcraftMod.MOD_ID,"textures/button/exit_icon.png");
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
        return 12;
    }

    @Override
    protected int getIconHeight() {
        return 12;
    }
}
