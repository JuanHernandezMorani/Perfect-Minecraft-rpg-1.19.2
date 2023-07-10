package net.cheto97.rpgcraftmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.menu.PlayerClassSelectMenu;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerClassSelectPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PlayerClassSelectScreen extends AbstractContainerScreen<PlayerClassSelectMenu> {
    public PlayerClassSelectScreen(PlayerClassSelectMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        double scale = Minecraft.getInstance().getWindow().getGuiScale();
        this.imageWidth = (int) (800/ scale);
        this.imageHeight = (int) (615/ scale);
    }

    private static final ResourceLocation texture = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/class_background.png");


    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, texture);
        blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void init() {
        super.init();
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);


        this.addRenderableWidget(new Button(this.leftPos +30, this.topPos + 85, 32, 22, Component.literal("Warrior").withStyle(ChatFormatting.DARK_AQUA), e ->{
            ModMessages.sendToServer(new PlayerClassSelectPacket("warrior"));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));
        this.addRenderableWidget(new Button(this.leftPos +90, this.topPos + 85, 32, 20, Component.literal("Priest").withStyle(ChatFormatting.DARK_AQUA), e ->{
            ModMessages.sendToServer(new PlayerClassSelectPacket("priest"));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));
        this.addRenderableWidget(new Button(this.leftPos +150, this.topPos + 85, 32, 20, Component.literal("Knight").withStyle(ChatFormatting.DARK_AQUA), e ->{
            ModMessages.sendToServer(new PlayerClassSelectPacket("knight"));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));
        this.addRenderableWidget(new Button(this.leftPos +210, this.topPos + 85, 32, 20, Component.literal("Mage").withStyle(ChatFormatting.DARK_AQUA), e ->{
            ModMessages.sendToServer(new PlayerClassSelectPacket("mage"));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));


        this.addRenderableWidget(new Button(this.leftPos +30, this.topPos + 165, 32, 20, Component.literal("Tamer").withStyle(ChatFormatting.DARK_AQUA), e ->{
            ModMessages.sendToServer(new PlayerClassSelectPacket("beast tamer"));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));
        this.addRenderableWidget(new Button(this.leftPos +135, this.topPos + 165, 32, 20, Component.literal("Killer").withStyle(ChatFormatting.DARK_AQUA), e ->{
            ModMessages.sendToServer(new PlayerClassSelectPacket("assassin"));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));
        this.addRenderableWidget(new Button(this.leftPos +225, this.topPos + 165, 32, 20, Component.literal("Archer").withStyle(ChatFormatting.DARK_AQUA), e ->{
            ModMessages.sendToServer(new PlayerClassSelectPacket("archer"));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));


        this.addRenderableWidget(new Button(this.leftPos +75, this.topPos + 140, 32, 20, Component.literal("Common").withStyle(ChatFormatting.DARK_AQUA), e ->{
            ModMessages.sendToServer(new PlayerClassSelectPacket("balanced"));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));
        this.addRenderableWidget(new Button(this.leftPos +180, this.topPos + 140, 32, 20, Component.literal("Random").withStyle(ChatFormatting.DARK_AQUA), e ->{
            ModMessages.sendToServer(new PlayerClassSelectPacket("random"));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));
    }
}
