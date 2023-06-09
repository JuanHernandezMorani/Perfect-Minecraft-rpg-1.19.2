package net.cheto97.rpgcraftmod.ModHud.Elements.hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public class HudElementHotbarHotbar extends HudElement {

    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/widgets.png");

    public HudElementHotbarHotbar() {
        super(HudType.HOTBAR, 0, 0, 0, 0, true);
        parent = HudType.WIDGET;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        assert this.mc.gameMode != null;
        if(this.mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            this.mc.gui.getSpectatorGui().renderHotbar(ms);
        } else if (this.mc.getCameraEntity() instanceof Player entityplayer) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            bind(WIDGETS_TEX_PATH);
            assert this.mc.player != null;
            ItemStack itemstack = this.mc.player.getOffhandItem();
            int i = scaledWidth / 2;
            int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hotbar_position)[0];
            int posY = this.settings.getPositionValue(Settings.hotbar_position)[1];
            gui.blit(ms, posX, scaledHeight - 47 + posY, 0, 0, 182, 22);
            gui.blit(ms, posX + entityplayer.getInventory().selected * 20, scaledHeight - 47 - 1 + posY, 0, 22, 24, 22);

            gui.blit(ms, posX + 181, scaledHeight - 47 + posY, 60, 23, 22, 22);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            for (int l = 0; l < 9; ++l) {
                int i1 = posX + 1 + l * 20 + 2;
                int j1 = scaledHeight - 16 - 19 - 9 + posY;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, this.mc.player.getInventory().items.get(l));
            }

            int l1 = scaledHeight - 47 + 3 + posY;
            this.renderHotbarItem(posX + 184, l1, partialTicks, entityplayer, itemstack);

            if(this.mc.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
                float f1 = this.mc.player.getAttackAnim(0.0F);

                if (f1 < 1.0F) {
                    int i2 = scaledHeight - 36 + posY;
                    int j2 = i + 40 + this.settings.getPositionValue(Settings.hotbar_position)[0];

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