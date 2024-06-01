package net.cheto97.rpgcraftmod.util.button;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

import org.jetbrains.annotations.NotNull;

public abstract  class CustomButton extends Button {
    protected abstract ResourceLocation getDisabledTexture();
    protected abstract ResourceLocation getHoveredTexture();
    protected abstract ResourceLocation getButtonTexture();
    protected abstract int getTextureWidth();
    protected abstract int getTextureHeight();
    protected abstract int getSourceX();
    protected abstract int getSourceY();
    protected abstract int getIconWidth();
    protected abstract int getIconHeight();


    public CustomButton(int x, int y, int width, int height, Component message, OnPress action) {
        super(x, y, width, height, message, action);
    }
    @Override
    public void renderButton(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindForSetup(getTexture(mouseX,mouseY));
        RenderSystem.setShaderTexture(0, getTexture(mouseX,mouseY));

        int textureWidth = getTextureWidth();
        int textureHeight = getTextureHeight();
        int sourceX = getSourceX();
        int sourceY = getSourceY();

        if (!this.active) {
            sourceY += textureHeight / 3;
        } else if (isMouseOver(mouseX,mouseY)) {
            sourceY += (textureHeight / 3) * 2;
        }

        GuiComponent.blit(poseStack, this.x, this.y, sourceX, sourceY, this.width, this.height, textureWidth, textureHeight);

        int iconWidth = getIconWidth();
        int iconHeight = getIconHeight();
        int iconX = this.x + (this.width - iconWidth) / 2;
        int iconY = this.y + (this.height - iconHeight) / 2;

        minecraft.getTextureManager().bindForSetup(getTexture(mouseX,mouseY));
        RenderSystem.setShaderTexture(0, getTexture(mouseX,mouseY));

        GuiComponent.blit(poseStack, iconX, iconY, 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);
    }
    public void On(){
        this.active = true;
    }
    public void Off(){
        this.active = false;
    }
    private ResourceLocation getTexture(int mouseX,int mouseY) {
        return this.isMouseOver(mouseX,mouseY) ? getHoveredTexture() : this.isActive() ? getButtonTexture() : getDisabledTexture();
    }
}