package net.cheto97.rpgcraftmod.ModHud.Elements.vanilla;

import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;
import static net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.INVENTORY_LOCATION;
import static net.minecraft.world.level.ClipContext.Block.OUTLINE;
import static net.minecraft.world.level.ClipContext.Fluid.NONE;

import java.util.Collection;
import java.util.List;

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
import net.cheto97.rpgcraftmod.client.ClientCustomLevelData;
import net.cheto97.rpgcraftmod.modsystem.Customlevel;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class HudElementViewVanilla extends HudElement {
    static public double focusedLife;
    static public double focusedMaxLife;
    static public int focusedId;
    static public double focusedArmor;
    static public Component focusedName;
    static public String focusedRank;
    static public int playerLevel = ClientCustomLevelData.getPlayerCustomLevel();
    static private boolean inVertical = false;
    static private ChatFormatting rankColor;
    static private boolean infinite = false;


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
        LivingEntity focused = getFocusedEntity(this.mc.player);
        if(focused != null) {
            setIdData(focused.getId());
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

            int x = (posX - 29 + 44 - (focusedName.getString().length() / 2));
            int y = 25 + posY;

            Component entityName = focusedName;
            Component entityRank = Component.literal("["+focusedRank+"]").withStyle(rankColor);

            Gui.drawCenteredString(ms,this.mc.font,entityRank,x+10,y-10,-1);
            Gui.drawCenteredString(ms,this.mc.font,entityName,x+10,y,-1);

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
                            if(inVertical) {
                                k -= 25;
                                l += 25 * (i - 1);
                            } else {
                                k += 25 * i;
                            }


                        } else {
                            ++j;
                            if(inVertical) {
                                k -= 50;
                                l += 25 * (j - 1);

                            } else {
                                k += 25 * j;
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

                        if(rpgHud.settings.getBoolValue(Settings.status_time) && !effectinstance.isAmbient()) {
                            int duration = effectinstance.getDuration()/20;
                            String s;
                            if(effectinstance.getDuration() < 999500 && !infinite){
                                s = "*:**";
                                if(duration < 600) s = String.valueOf(duration / 60 + ":" + (duration % 60 < 10 ? "0" + (duration % 60) : (duration % 60)));
                            }else{
                                s = "∞";
                                infinite = true;
                            }
                            k -= mc.font.width(s)/2;
                            this.drawStringWithBackground(ms, s, k +12, l +14, -1, 0);
                        }
                    }
                }
            }


        }
    }

    public static void setIdData(int id){
        focusedId = id;
    }

    public static int getIdData(){
        return focusedId;
    }
    public static int getPlayerLevel(){
        return playerLevel;
    }
    public static void setData(double lifeData, double lifeMaxData, double armorData,int rank ,Component nameData){
        focusedLife = lifeData;
        focusedMaxLife = lifeMaxData;
        focusedArmor = armorData;
        rankColor = setColor(rank);
        switch (rank){
            case 0 -> focusedRank = "NULL";
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
        }
        focusedName = nameData;
    }

    public static void drawEntityOnScreen(int posX, int posY, LivingEntity entity) {
        int scale = 1;
        int s1 = (int) (18 / entity.getBbHeight());
        int s3 = (int) (18 / entity.getScale());
        int offset = 0;
        if(s1 > s3) {
            scale = s3;
        } else
            scale = s1;

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

    public static LivingEntity getFocusedEntity(Entity watcher) {
        LivingEntity focusedEntity = null;
        double maxDistance = 64;
        Vec3 vec = new Vec3(watcher.getX(), watcher.getY(), watcher.getZ());
        Vec3 posVec = watcher.position();
        if(watcher instanceof Player) {
            vec = vec.add(0D, watcher.getEyeHeight(), 0D);
            posVec = posVec.add(0D, watcher.getEyeHeight(), 0D);
        }

        Vec3 lookVec = Vec3.directionFromRotation(watcher.getRotationVector());
        Vec3 vec2 = vec.add(lookVec.normalize().multiply(maxDistance,maxDistance,maxDistance));

        BlockHitResult ray = watcher.level
                .clip(new ClipContext(vec, vec2, OUTLINE, NONE, watcher));

        double distance = maxDistance;
        if(ray != null) {
            distance = ray.getBlockPos().distSqr(new Vec3i(posVec.x,posVec.y,posVec.z));
        }
        Vec3 reachVector = posVec.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance);

        double currentDistance = distance;

        List<Entity> entitiesWithinMaxDistance = watcher.level.getEntities(watcher,
                watcher.getBoundingBox().expandTowards(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance).expandTowards(1, 1, 1));
        for(Entity entity : entitiesWithinMaxDistance) {
            if(entity instanceof LivingEntity) {
                float collisionBorderSize = entity.getPickRadius();
                AABB hitBox = entity.getBoundingBox().expandTowards(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                Vec3 hitVecIn = intercept(posVec, reachVector, hitBox);
                if(hitBox.contains(posVec)) {
                    if(currentDistance <= 0D) {
                        currentDistance = 0;
                        focusedEntity = (LivingEntity) entity;
                    }
                } else if(hitVecIn != null) {
                    Vec3 hitVec = new Vec3(hitVecIn.x, hitVecIn.y, hitVecIn.z);
                    double distanceToEntity = posVec.distanceTo(hitVec);
                    if(distanceToEntity <= currentDistance) {
                        currentDistance = distanceToEntity;
                        focusedEntity = (LivingEntity) entity;
                    }
                }
            }
        }
        return focusedEntity;
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

    public static Vec3 intercept(Vec3 vecA, Vec3 vecB, AABB bb) {
        double[] adouble = new double[] { 1.0D };
        Direction enumfacing = null;
        double d0 = vecB.x - vecA.x;
        double d1 = vecB.y - vecA.y;
        double d2 = vecB.z - vecA.z;
        enumfacing = func_197741_a(bb, vecA, adouble, enumfacing, d0, d1, d2);
        if(enumfacing == null) {
            return null;
        } else {
            double d3 = adouble[0];
            return vecA.add(d3 * d0, d3 * d1, d3 * d2);
        }
    }

    private static Direction func_197741_a(AABB aabb, Vec3 p_197741_1_, double[] p_197741_2_, Direction facing, double p_197741_4_,
                                           double p_197741_6_, double p_197741_8_) {
        if(p_197741_4_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.minX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, Direction.WEST,
                    p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        } else if(p_197741_4_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.maxX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, Direction.EAST,
                    p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        }

        if(p_197741_6_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.minY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, Direction.DOWN,
                    p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        } else if(p_197741_6_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.maxY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, Direction.UP,
                    p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        }

        if(p_197741_8_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.minZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY,
                    Direction.NORTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        } else if(p_197741_8_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.maxZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY,
                    Direction.SOUTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        }

        return facing;
    }

    private static Direction func_197740_a(double[] p_197740_0_, Direction p_197740_1_, double p_197740_2_, double p_197740_4_, double p_197740_6_,
                                           double p_197740_8_, double p_197740_10_, double p_197740_12_, double p_197740_14_, double p_197740_16_, Direction p_197740_18_, double p_197740_19_,
                                           double p_197740_21_, double p_197740_23_) {
        double d0 = (p_197740_8_ - p_197740_19_) / p_197740_2_;
        double d1 = p_197740_21_ + d0 * p_197740_4_;
        double d2 = p_197740_23_ + d0 * p_197740_6_;
        if(0.0D < d0 && d0 < p_197740_0_[0] && p_197740_10_ - 1.0E-7D < d1 && d1 < p_197740_12_ + 1.0E-7D && p_197740_14_ - 1.0E-7D < d2
                && d2 < p_197740_16_ + 1.0E-7D) {
            p_197740_0_[0] = d0;
            return p_197740_18_;
        } else {
            return p_197740_1_;
        }
    }

}