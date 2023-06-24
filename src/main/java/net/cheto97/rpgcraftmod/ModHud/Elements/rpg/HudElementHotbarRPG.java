package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;

public class HudElementHotbarRPG extends HudElement {

    public HudElementHotbarRPG() {
        super(HudType.HOTBAR, 0, 0, 0, 0, true);
    }

    public static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(this.mc.player != null) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, WIDGETS_TEX_PATH);
            Player entityplayer = (Player) this.mc.getCameraEntity();
            ItemStack itemstack = this.mc.player.getOffhandItem();
            int posX = this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = 5 + this.settings.getPositionValue(Settings.hotbar_position)[1];
            HumanoidArm enumhandside = this.mc.player.getMainArm().getOpposite();
            int i = (scaledWidth / 2) + posX;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            for (int l = 0; l < 9; ++l) {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = scaledHeight - 16 - 3 - 9 + 4 + posY;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().items.get(l));
            }

            if (itemstack != ItemStack.EMPTY) {
                int l1 = scaledHeight - 16 - 3 - 9 + posY;

                if (enumhandside == HumanoidArm .LEFT) {
                    this.renderHotbarItem(i - 91 - 26 + 5, l1 + 4, partialTicks, entityplayer, itemstack);
                } else {
                    this.renderHotbarItem(i + 91 + 10 - 4, l1 + 4, partialTicks, entityplayer, itemstack);
                }
            }

            if(this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                float f1 = this.mc.player.getAttackAnim(0.0F);

                if (f1 < 1.0F) {
                    int i2 = scaledHeight - 17 + posY;
                    int j2 = i + 91 + 6;

                    if (enumhandside == HumanoidArm .RIGHT) {
                        j2 = i - 91 - 22;
                    }

                    bind(Gui.GUI_ICONS_LOCATION);
                    int k1 = (int) (f1 * 19.0F);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    gui.blit(ms, j2, i2 - 9, 0, 94, 18, 18);
                    gui.blit(ms, j2, i2 - 9 + 18 - k1, 18, 112 - k1, 18, k1);
                }
            }

            RenderSystem.disableBlend();
        }
    }
}