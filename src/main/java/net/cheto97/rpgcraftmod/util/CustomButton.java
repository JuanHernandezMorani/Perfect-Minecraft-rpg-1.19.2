package net.cheto97.rpgcraftmod.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import org.jetbrains.annotations.NotNull;

public class CustomButton extends Button {
    public static final ResourceLocation DISABLED_TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/button/disabled.png");
    public static final ResourceLocation HOVERED_TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/button/hovered.png");
    public static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/button/icon.png");
    private static boolean BUTTON_STATUS = true;
    public CustomButton(int x, int y, int width, int height, Component message, OnPress action) {
        super(x, y, width, height, message, action);
    }
    @Override
    public void renderButton(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindForSetup(getTexture(mouseX, mouseY));
        RenderSystem.setShaderTexture(0, getTexture(mouseX, mouseY));

        int textureWidth = 32;
        int textureHeight = 32;
        int sourceX = 0;
        int sourceY = 0;

        if (!this.active) {
            sourceY += textureHeight / 3;
        } else if (isMouseOver(mouseX,mouseY)) {
            sourceY += (textureHeight / 3) * 2;
        }

        GuiComponent.blit(poseStack, this.x, this.y, sourceX, sourceY, this.width, this.height, textureWidth, textureHeight);

        int iconWidth = 16;
        int iconHeight = 16;
        int iconX = this.x + (this.width - iconWidth) / 2;
        int iconY = this.y + (this.height - iconHeight) / 2;

        minecraft.getTextureManager().bindForSetup(BUTTON_TEXTURE);
        RenderSystem.setShaderTexture(0, BUTTON_TEXTURE);

        GuiComponent.blit(poseStack, iconX, iconY, 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);
    }
    public void On(){
        BUTTON_STATUS = true;
        this.active = true;
    }
    public void Off(){
        BUTTON_STATUS = false;
        this.active = false;
    }
    private ResourceLocation getTexture(int mouseX, int mouseY) {
        return isMouseOver(mouseX,mouseY) ? HOVERED_TEXTURE : BUTTON_STATUS ? BUTTON_TEXTURE : DISABLED_TEXTURE;
    }
}