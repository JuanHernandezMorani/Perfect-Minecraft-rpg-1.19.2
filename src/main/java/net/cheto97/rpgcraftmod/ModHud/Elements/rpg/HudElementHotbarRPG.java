package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
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

    public static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/widgets.png");

    @Override
    public boolean checkConditions() {
        assert this.mc.player != null;
        return !this.mc.player.isSpectator();
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(checkConditions()) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            bind(WIDGETS_TEX_PATH);
            Player entityplayer = (Player) this.mc.getCameraEntity();
            assert this.mc.player != null;
            ItemStack itemstack = this.mc.player.getOffhandItem();
            int posX = 49 + this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1] - 8;
            HumanoidArm enumhandside = this.mc.player.getMainArm().getOpposite();
            int i = (scaledWidth / 2) + posX;
            gui.blit(ms, i - 91, scaledHeight - 16 - 7 - 9 + 4 + posY, 0, 0, 182, 22);
            assert entityplayer != null;
            gui.blit(ms, i - 91 + entityplayer.getInventory().selected * 20, scaledHeight - 16 - 7 - 9 + 4 + posY, 0, 22, 24, 22);
            gui.blit(ms, i - 115, scaledHeight - 16 - 7 - 9 + 4 + posY, 60, 23, 22, 22);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            for (int l = 0; l < 9; ++l) {
                int i1 = i - 91 + l * 20 + 3;
                int j1 = scaledHeight - 16 - 4 - 9 + 4 + posY;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().items.get(l));
            }

            if (itemstack != ItemStack.EMPTY) {
                int l1 = scaledHeight - 16 - 4 - 9 + posY;

                if (enumhandside == HumanoidArm .LEFT) {
                    this.renderHotbarItem(i - 91 - 26 + 5, l1 + 4, partialTicks, entityplayer, itemstack);
                } else {
                    this.renderHotbarItem(i + 91 + 10 - 4, l1 + 4, partialTicks, entityplayer, itemstack);
                }
            }

            if(!this.mc.player.isCreative()){
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