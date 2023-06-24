package net.cheto97.rpgcraftmod.ModHud.Elements.vanilla;

import static net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.INVENTORY_LOCATION;

import java.util.Collection;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import net.cheto97.rpgcraftmod.ModHud.settings.Settings;

public class HudElementMobEffectsVanilla extends HudElement {

    public HudElementMobEffectsVanilla() {
        super(HudType.STATUS_EFFECTS, 0, 0, 0, 0, true);
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float na, float partialTicks, int scaledWidth, int scaledHeight) {
        float scale = getScale();
        ms.scale(scale, scale, scale);
        assert this.mc.player != null;
        Collection<MobEffectInstance> collection = this.mc.player.getActiveEffects();
        if(!collection.isEmpty()) {
            RenderSystem.enableBlend();
            int i = 0;
            int j = 0;
            MobEffectTextureManager potionspriteuploader = this.mc.getMobEffectTextures();
            bind(INVENTORY_LOCATION);

            for(MobEffectInstance effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
                MobEffect effect = effectinstance.getEffect();
                bind(INVENTORY_LOCATION);
                if(effectinstance.showIcon()) {
                    int k = getPosX(scaledWidth);
                    int l = getPosY(scaledHeight);
                    if(this.mc.isDemo()) {
                        l += 15;
                    }

                    if(effect.isBeneficial()) {
                        ++i;
                        if(rpgHud.settings.getBoolValue(Settings.status_vertical)) {
                            k -= 25;
                            l += 25 * (i - 1);
                        } else {
                            k -= 25 * i;
                        }


                    } else {
                        ++j;
                        if(rpgHud.settings.getBoolValue(Settings.status_vertical)) {
                            k -= 50;
                            l += 25 * (j - 1);

                        } else {
                            k -= 25 * j;
                            l += 25;
                        }

                    }
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    float f = 1.0F;
                    if(effectinstance.isAmbient()) {
                        gui.blit(ms, k, l, 165, 166, 24, 24);
                    } else {
                        gui.blit(ms, k, l, 141, 166, 24, 24);
                        if(effectinstance.getDuration() <= 200) {
                            int i1 = 10 - effectinstance.getDuration() / 20;
                            f = Mth.clamp((float) effectinstance.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                                    + Mth.cos((float) effectinstance.getDuration() * (float) Math.PI / 5.0F)
                                    * Mth.clamp((float) i1 / 10.0F * 0.25F, 0.0F, 0.25F);
                        }
                    }
                    TextureAtlasSprite textureatlassprite = potionspriteuploader.get(effect);
                    bind(textureatlassprite.atlas().location());
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f);
                    Gui.blit(ms, k + 3, l + 3, gui.getBlitOffset(), 18, 18, textureatlassprite);
                    // Main
                    if(rpgHud.settings.getBoolValue(Settings.status_time) && !effectinstance.isAmbient()) {
                        int duration = effectinstance.getDuration()/20;
                        String s = "*:**";
                        if(duration < 600) s = duration / 60 + ":" + (duration % 60 < 10 ? "0" + (duration % 60) : (duration % 60));
                        k -= mc.font.width(s)/2;
                        this.drawStringWithBackground(ms, s, k +12, l +14);
                    }
                }
            }
        }
    }

    @Override
    public int getPosX(int scaledWidth) {
        return (int) (scaledWidth * getInvertedScale() + this.settings.getPositionValue(Settings.status_position)[0]);
    }

    @Override
    public int getPosY(int scaledHeight) {
        return 1 + (this.settings.getPositionValue(Settings.status_position)[1]);
    }

    @Override
    public float getScale() {
        return (float) this.settings.getDoubleValue(Settings.status_scale);
    }
}