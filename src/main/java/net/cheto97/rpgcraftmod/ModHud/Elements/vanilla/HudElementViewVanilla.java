package net.cheto97.rpgcraftmod.ModHud.Elements.vanilla;

import static net.cheto97.rpgcraftmod.util.IntToString.formatearNumero;
import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;
import static net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.INVENTORY_LOCATION;

import java.util.Collection;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.customstats.Defense;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.data.EntityData;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.networking.packet.EntitySyncPacket;
import net.cheto97.rpgcraftmod.providers.*;
import net.cheto97.rpgcraftmod.util.RayTrace;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;

public class HudElementViewVanilla extends HudElement {
    static private boolean infinite = false;
    static private final RayTrace TRACE = new RayTrace();
    static private LivingEntity entityData;
    static private Player playerData;
    static public double focusedLife;
    static public double focusedMaxLife;
    static public int focusedId;
    static public double focusedArmor;
    static public double focusedMana;
    static public double focusedMaxMana;
    static public int playerLevel;
    static public int entityLevel;
    static public int rank;
    protected static final ResourceLocation DAMAGE_INDICATOR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/view_hud.png");
    protected static final ResourceLocation HEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/health_bar.png");
    protected static final ResourceLocation FROZENHEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/frozen_health_bar.png");
    protected static final ResourceLocation WITHERHEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/wither_health_bar.png");
    protected static final ResourceLocation POISONHEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/poison_health_bar.png");
    protected static final ResourceLocation ABSORTIONHEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/absortion_health_bar.png");


    @Override
    public boolean checkConditions() {
        return this.settings.getBoolValue(Settings.enable_view);
    }
    public HudElementViewVanilla() {
        super(HudType.VIEW, 0, 0, 0, 0, true);
    }
    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        assert this.mc.player != null;
        LivingEntity focused = TRACE.getEntityInCrosshair(1.0f,32);
        Player player = this.mc.player;
        if(focused != null && player != null) {
            setIdData(focused.getId());
            String focusedRank;

                ChatFormatting rankColor = setColor(rank);
                switch (rank){
                    case 1 -> focusedRank = "Common";
                    case 2 -> focusedRank = "Elite";
                    case 3 -> focusedRank = "Brutal";
                    case 4 -> focusedRank = "Champion";
                    case 5 -> focusedRank = "Hero";
                    case 6 -> focusedRank = "Demon";
                    case 7 -> focusedRank = "Legendary";
                    case 8 -> focusedRank = "Mythical";
                    case 9 -> focusedRank = "Unique";
                    case 10 -> focusedRank = "Semi Boss";
                    case 11 -> focusedRank = "Boss";
                    default -> focusedRank = "NULL";
                }

                ChatFormatting color;

                if(playerLevel > entityLevel){
                    if(playerLevel - (playerLevel * 0.05) <= entityLevel){
                        color = ChatFormatting.AQUA;
                    }else if(playerLevel - (playerLevel * 0.12) <= entityLevel && playerLevel - (playerLevel * 0.05) >= entityLevel){
                        color = ChatFormatting.DARK_AQUA;
                    }else if(playerLevel - (playerLevel * 0.3) <= entityLevel && playerLevel - (playerLevel * 0.12) >= entityLevel){
                        color = ChatFormatting.GREEN;
                    }else{
                        color = ChatFormatting.DARK_GREEN;
                    }
                }
                else if(playerLevel == entityLevel){
                    color = ChatFormatting.GRAY;
                }else {
                    if(playerLevel - (playerLevel * 0.05) >= entityLevel){
                        color = ChatFormatting.DARK_GRAY;
                    }else if(playerLevel - (playerLevel * 0.12) >= entityLevel && playerLevel - (playerLevel * 0.05) <= entityLevel){
                        color = ChatFormatting.LIGHT_PURPLE;
                    }else if(playerLevel - (playerLevel * 0.3) >= entityLevel && playerLevel - (playerLevel * 0.12) <= entityLevel){
                        color = ChatFormatting.RED;
                    }else{
                        color = ChatFormatting.DARK_RED;
                    }
                }
                Component focusedName = Component.literal("{lvl "+formatearNumero(entityLevel)+"} "+focused.getName().getString()).withStyle(color);

                int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
                int posY = 20 + this.settings.getPositionValue(Settings.inspector_position)[1];
                bind(DAMAGE_INDICATOR);
                gui.blit(ms, posX - 62, 20 + posY, 0, 0, 128, 36);

                if(focusedLife > focusedMaxLife) focusedLife = focusedMaxLife;

                ResourceLocation select = HEALTHBAR;


                if(focused.hasEffect(MobEffects.ABSORPTION)){
                    select = ABSORTIONHEALTHBAR;
                }else if(focused.isFreezing() || focused.isFullyFrozen() || focused.isInPowderSnow){
                    select = FROZENHEALTHBAR;
                }else if(focused.hasEffect(MobEffects.POISON)){
                    select = POISONHEALTHBAR;
                }else if(focused.hasEffect(MobEffects.WITHER)){
                    select = WITHERHEALTHBAR;
                }

                bind(select);

                gui.blit(ms,posX - 28, 26 + posY,0,128,(int)(92D *(focusedLife /  focusedMaxLife)),36);

                String stringLife = doubleToString(focusedLife) + " / " + doubleToString(focusedMaxLife);
                ms.scale(0.5f, 0.5f, 0.5f);
                Gui.drawCenteredString(ms, this.mc.font, stringLife, (posX - 27 + 44) * 2, (36 + posY) * 2, -1);
                ms.scale(2f, 2f, 2f);

                int x = (posX - 29 + 44 - (focused.getName().getString().length() / 2));
                int y = 25 + posY;

                Component entityRank = Component.literal("["+focusedRank+"]").withStyle(rankColor);

                Gui.drawCenteredString(ms,this.mc.font,entityRank,x+10,y-10,-1);
                Gui.drawCenteredString(ms,this.mc.font, focusedName,x+10,y,-1);

                drawEntityOnScreen(posX - 44, 49 + posY, focused);
                if(settings.getBoolValue(Settings.show_entity_armor)) {
                    if(focusedArmor > 0) {
                        String value = doubleToString(focusedArmor);
                        bind(DAMAGE_INDICATOR);
                        gui.blit(ms, posX - 26, posY+44, 0, 36, 19, 8);
                        bind(Gui.GUI_ICONS_LOCATION);
                        ms.scale(0.5f, 0.5f, 0.5f);
                        gui.blit(ms, (posX - 24) * 2 - 4, (posY + 45) * 2, 34, 9, 9, 9);
                        Gui.drawCenteredString(ms,this.mc.font,value,(posX - 18) * 2 + 4, (posY + 45) * 2 + 1, -1);
                        ms.scale(2f, 2f, 2f);
                    }
                }

                Collection<MobEffectInstance> collection = focused.getActiveEffects();

                if(!collection.isEmpty()) {
                    ms.scale(0.5f, 0.5f, 0.5f);
                    RenderSystem.enableBlend();
                    int i = 0;
                    int j = 0;
                    MobEffectTextureManager potionspriteuploader = this.mc.getMobEffectTextures();
                    bind(INVENTORY_LOCATION);

                    for(MobEffectInstance effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
                        MobEffect effect = effectinstance.getEffect();
                        bind(INVENTORY_LOCATION);
                        if(effectinstance.showIcon()) {
                            int k = (posX - 28)*2-80;
                            int l = (posY + 45)*2+15;
                            if(this.mc.isDemo()) {
                                l += 15;
                            }

                            if(effect.isBeneficial()) {
                                ++i;
                                k += 25 * i;
                            } else {
                                ++j;
                                k += 25 * j;
                                l += 25;
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

                            if(rpgHud.settings.getBoolValue(Settings.status_time) && !effectinstance.isAmbient()) {
                                int duration = effectinstance.getDuration() / 20;
                                String s;
                                if (effectinstance.getDuration() < 999500 && !infinite) {
                                    s = "*:**";
                                    if (duration < 600)
                                        s = duration / 60 + ":" + (duration % 60 < 10 ? "0" + (duration % 60) : (duration % 60));
                                } else {
                                    s = "∞";
                                    infinite = true;
                                }
                                k -= mc.font.width(s) / 2;
                                this.drawStringWithBackground(ms, s, k + 12, l + 14);
                            }
                        }
                    }
                }
        }
    }
    public static void setIdData(int id){
        focusedId = id;
    }
    public static void setData(double lifeData,double lifeMaxData,double manaData,double manaMaxData,double armorData,int rankData,int levelData){
        focusedLife = lifeData;
        focusedMaxLife = lifeMaxData;
        focusedMana = manaData;
        focusedMaxMana = manaMaxData;
        focusedArmor = armorData;
        entityLevel = levelData;
        rank = rankData;
    }
    public static int dataNeeded(){
        return focusedId;
    }
    public static void drawEntityOnScreen(int posX, int posY, LivingEntity entity) {
        int scale;
        int s1 = (int) (18 / entity.getBbHeight());
        int s3 = (int) (18 / entity.getScale());
        int offset = 0;
        scale = Math.min(s1, s3);

        if(entity instanceof Squid) {
            scale = 11;
            offset = -13;
        } else if(entity instanceof Spider) {
            scale = 11;
            offset = -5;
        }
        posY += offset;
        float f = (float) Math.atan((180 / 40.0F));
        float g = (float) Math.atan((0 / 40.0F));
        PoseStack ms = RenderSystem.getModelViewStack();
        ms.pushPose();
        ms.translate(posX, posY, 1050.0F);
        ms.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack poseStack = new PoseStack();
        poseStack.translate(0.0D, 0.0D, 1000.0D);
        poseStack.scale(scale, scale, scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion2 =  Vector3f.XP.rotationDegrees(g * 20.0F);
        quaternion.mul(quaternion2);
        poseStack.mulPose(quaternion);
        float h = entity.yBodyRot;
        float i = entity.getYRot();
        float j = entity.getXRot();
        float k = entity.yHeadRotO;
        float l = entity.yHeadRot;



        entity.setYBodyRot(180.0F + f * 20.0F);
        entity.setYRot(180.0F + f * 40.0F);
        entity.setXRot(-g * 20.0F);
        entity.yHeadRot = entity.getYRot();
        entity.yHeadRotO = entity.getYRot();

        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion2.conj();
        entityRenderDispatcher.overrideCameraOrientation(quaternion2);
        entityRenderDispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
        entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack, immediate, 15728880);
        immediate.endBatch();
        entityRenderDispatcher.setRenderShadow(true);


        entity.yBodyRot = h;
        entity.setYRot(i);
        entity.setXRot(j);
        entity.yHeadRotO = k;
        entity.yHeadRot = l;


        ms.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }
    private static ChatFormatting setColor(int rank){
        ChatFormatting result = ChatFormatting.GRAY;
        switch (rank){
            case 0 -> result = ChatFormatting.BLACK;
            case 1 -> result = ChatFormatting.WHITE;
            case 2 -> result = ChatFormatting.AQUA;
            case 3 -> result = ChatFormatting.DARK_AQUA;
            case 4 -> result = ChatFormatting.GREEN;
            case 5 -> result = ChatFormatting.DARK_GREEN;
            case 6 -> result = ChatFormatting.BLUE;
            case 7 -> result = ChatFormatting.LIGHT_PURPLE;
            case 8 -> result = ChatFormatting.GOLD;
            case 9 -> result = ChatFormatting.RED;
            case 10 -> result = ChatFormatting.DARK_RED;
            case 11 -> result = ChatFormatting.DARK_PURPLE;
        }
        return result;
    }

}