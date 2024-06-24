package net.cheto97.rpgcraftmod.ModHud;
/*
import static net.cheto97.rpgcraftmod.util.Effects.Helper.calculateValue;
import static net.cheto97.rpgcraftmod.util.IntToString.formatearNumero;
import static net.cheto97.rpgcraftmod.util.NumberUtils.defTogether;
import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;
import static net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.INVENTORY_LOCATION;

import java.util.Collection;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.custom.MutantGolemEntity;
import net.cheto97.rpgcraftmod.entity.custom.prefabs.RPGEntityAgro;
import net.cheto97.rpgcraftmod.entity.custom.prefabs.RPGEntityPasive;
import net.cheto97.rpgcraftmod.entity.custom.prefabs.RPGEntityTameable;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.data.EntityData;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.networking.packet.C2S.EntityInfoRequestPacket;
import net.cheto97.rpgcraftmod.util.RayTrace;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class HudElementViewVanilla extends HudElement {
    static private boolean infinite = false;
    static private final RayTrace TRACE = new RayTrace();
    protected static final ResourceLocation DAMAGE_INDICATOR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/view_hud.png");
    protected static final ResourceLocation HEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/health_bar.png");
    protected static final ResourceLocation FROZENHEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/frozen_health_bar.png");
    protected static final ResourceLocation WITHERHEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/wither_health_bar.png");
    protected static final ResourceLocation POISONHEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/poison_health_bar.png");
    protected static final ResourceLocation ABSORTIONHEALTHBAR = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/absorption_health_bar.png");
    protected static final ResourceLocation PASIVE = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/mob_type/animal.png");
    protected static final ResourceLocation AGRESIVE = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/mob_type/monster.png");
    protected static final ResourceLocation NEUTRAL = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/mob_type/unknown.png");
    protected static final ResourceLocation PLAYER = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/mob_type/player.png");
    protected static final ResourceLocation VILLAGER = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/mob_type/villager.png");
    protected static final ResourceLocation ILLAGER = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/mob_type/illager.png");
    protected static final ResourceLocation UNKNOWN = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/mob_type/unknown.png");

    @Override
    public boolean checkConditions() {
        return  this.mc.player != null && !this.mc.player.isSpectator();
    }
    public HudElementViewVanilla() {
        super(HudType.VIEW, 0, 0, 0, 0, true);
    }
    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        assert this.mc.player != null;
        LivingEntity focused = TRACE.getEntityInCrosshair(1.0f, 32);
        Player player = this.mc.player;
        if (focused != null && player != null) {

            ModMessages.sendToServer(new EntityInfoRequestPacket(focused));

            int playerLevel = PlayerData.getPlayerLevel();
            int rank = 0;
            int entityLevel = 0;
            double focusedLife = 0.0;
            double focusedMaxLife = 0.0;
            double focusedArmor = 0.0;
            double focusedMagicArmor = 0.0;
            int resetQ = 0;
            double defBonus = -1;

            if (focused.getId() == EntityData.getEntityId() && player.getId() == PlayerData.getPlayerId()) {
                rank = EntityData.getEntityRank() > 0 ? EntityData.getEntityRank() : -1;
                resetQ = rank == -1 ? EntityData.getResetQ() : -1;
                entityLevel = EntityData.getEntityLevel();
                focusedMaxLife = EntityData.getEntityLifeMax();
                focusedLife = EntityData.getEntityLife();

                MobEffectInstance defEff = focused.hasEffect(MobEffects.DAMAGE_RESISTANCE) ? focused.getEffect(MobEffects.DAMAGE_RESISTANCE) : null;

                defBonus = defEff != null ? calculateValue(defEff, EntityData.getEntityDefense(), "add") : 0.0;

                focusedArmor = EntityData.getEntityDefense() + defBonus;

                focusedMagicArmor = EntityData.getEntityMagicDefense();
            }

            String focusedRank;
            ChatFormatting rankColor = setColor(rank);
            switch (rank) {
                case -1 -> focusedRank = "[reset: " + resetQ + "] Player";
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
            ChatFormatting color = getChatFormatting(playerLevel, entityLevel);
            Component focusedName = Component.literal("{lvl " + formatearNumero(entityLevel) + "} " + focused.getName().getString()).withStyle(color);

            int posX = (scaledWidth / 2) + this.settings.getPositionValue(Settings.inspector_position)[0];
            int posY = 20 + this.settings.getPositionValue(Settings.inspector_position)[1];
            bind(DAMAGE_INDICATOR);
            gui.blit(ms, posX - 62, 20 + posY, 0, 0, 128, 36);

            if (focusedLife > focusedMaxLife) focusedLife = focusedMaxLife;

            ResourceLocation select = HEALTHBAR;

            if (focused.hasEffect(MobEffects.ABSORPTION)) {
                select = ABSORTIONHEALTHBAR;
            } else if (focused.isFreezing() || focused.isFullyFrozen() || focused.isInPowderSnow) {
                select = FROZENHEALTHBAR;
            } else if (focused.hasEffect(MobEffects.POISON)) {
                select = POISONHEALTHBAR;
            } else if (focused.hasEffect(MobEffects.WITHER)) {
                select = WITHERHEALTHBAR;
            }

            bind(select);

            gui.blit(ms, posX - 28, 26 + posY, 0, 128, (int) (92D * (focusedLife / focusedMaxLife)), 36);

            String stringLife = doubleToString(focusedLife) + " / " + doubleToString(focusedMaxLife);
            ms.scale(0.5f, 0.5f, 0.5f);
            Gui.drawCenteredString(ms, this.mc.font, stringLife, (posX - 27 + 44) * 2, (36 + posY) * 2, -1);
            ms.scale(2f, 2f, 2f);

            int x = (posX - 29 + 44 - (focused.getName().getString().length() / 2));
            int y = 25 + posY;

            Component entityRank = Component.literal("[" + focusedRank + "]").withStyle(rankColor);

            Gui.drawCenteredString(ms, this.mc.font, entityRank, x + 10, y - 10, -1);
            Gui.drawCenteredString(ms, this.mc.font, focusedName, x + 10, y, -1);

            drawEntityOnScreen(gui, ms, 0, 0, focused);

            if (focusedArmor > 0 || focusedMagicArmor > 0) {

                    Component value = defBonus > 0 ? Component.literal(defTogether(focusedMagicArmor, focusedArmor)).withStyle(ChatFormatting.GOLD) : Component.literal(defTogether(focusedMagicArmor, focusedArmor));
                    ms.scale(0.5f, 0.5f, 0.5f);
                    Gui.drawString(ms, this.mc.font, value, (posX - 18) * 2 - 17, (posY + 45) * 2 + 1, -1);
                    ms.scale(2f, 2f, 2f);
                }

            Collection<MobEffectInstance> collection = EntityData.getEffects();

            if (collection != null && !collection.isEmpty()) {
                ms.scale(0.5f, 0.5f, 0.5f);
                RenderSystem.enableBlend();
                int i = 0;
                int j = 0;
                MobEffectTextureManager potionspriteuploader = this.mc.getMobEffectTextures();
                bind(INVENTORY_LOCATION);

                for (MobEffectInstance effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
                    MobEffect effect = effectinstance.getEffect();
                    bind(INVENTORY_LOCATION);
                    if (effectinstance.showIcon()) {
                        int k = (posX - 28) * 2 - 80;
                        int l = (posY + 45) * 2 + 15;
                        if (this.mc.isDemo()) {
                            l += 15;
                        }

                        if (effect.isBeneficial()) {
                            ++i;
                            k += 25 * i;
                        } else {
                            ++j;
                            k += 25 * j;
                            l += 25;
                        }
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        float f = 1.0F;
                        if (effectinstance.isAmbient()) {
                            gui.blit(ms, k, l, 165, 166, 24, 24);
                        } else {
                            gui.blit(ms, k, l, 141, 166, 24, 24);
                            if (effectinstance.getDuration() <= 200) {
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

                        if (RpgcraftMod.settings.getBoolValue(Settings.status_time) && !effectinstance.isAmbient()) {
                            int duration = effectinstance.getDuration() / 20;
                            String s;
                            int amp = effectinstance.isAmbient() ? effectinstance.getAmplifier() + 1 : effectinstance.getAmplifier();
                            String statLevel = formatearNumero(amp);
                            if (effectinstance.getDuration() < 999500 && !infinite) {
                                s = "*:**";
                                if (duration < 600)
                                    s = duration / 60 + ":" + (duration % 60 < 10 ? "0" + (duration % 60) : (duration % 60));
                            } else {
                                s = "∞";
                                infinite = true;
                            }
                            k -= mc.font.width(s) / 2;
                            this.drawStringWithBackground(ms, statLevel, k + 12, l + 1);
                            this.drawStringWithBackground(ms, s, k + 12, l + 14);
                        }
                    }
                }
            }
        }
    }

    @NotNull
    private static ChatFormatting getChatFormatting(int playerLevel, int entityLevel) {
        ChatFormatting color;

        if (playerLevel > entityLevel) {
            if (playerLevel - (playerLevel * 0.05) <= entityLevel) {
                color = ChatFormatting.AQUA;
            } else if (playerLevel - (playerLevel * 0.12) <= entityLevel && playerLevel - (playerLevel * 0.05) >= entityLevel) {
                color = ChatFormatting.DARK_AQUA;
            } else if (playerLevel - (playerLevel * 0.3) <= entityLevel && playerLevel - (playerLevel * 0.12) >= entityLevel) {
                color = ChatFormatting.GREEN;
            } else {
                color = ChatFormatting.DARK_GREEN;
            }
        } else if (playerLevel == entityLevel) {
            color = ChatFormatting.GRAY;
        } else {
            if (playerLevel - (playerLevel * 0.05) >= entityLevel) {
                color = ChatFormatting.DARK_GRAY;
            } else if (playerLevel - (playerLevel * 0.12) >= entityLevel && playerLevel - (playerLevel * 0.05) <= entityLevel) {
                color = ChatFormatting.LIGHT_PURPLE;
            } else if (playerLevel - (playerLevel * 0.3) >= entityLevel && playerLevel - (playerLevel * 0.12) <= entityLevel) {
                color = ChatFormatting.RED;
            } else {
                color = ChatFormatting.DARK_RED;
            }
        }
        return color;
    }

    public void drawEntityOnScreen(Gui gui, PoseStack ms, int posX, int posY, LivingEntity entity) {

        ResourceLocation texture = GetTexture(entity);

        bind(texture);

        int spriteWidth = 32;
        int spriteHeight = 32;

        gui.blit(ms, -32, 64, 18, 18, spriteWidth, spriteHeight);
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
    private ResourceLocation GetTexture(LivingEntity entity){
        return entity instanceof Villager ? VILLAGER : entity.getMobType() == MobType.ILLAGER ? ILLAGER : entity instanceof Player ? PLAYER : isNeutral(entity) && !isEnemy(entity) ? NEUTRAL : isPasive(entity) ? PASIVE : isAgresive(entity) ? AGRESIVE : UNKNOWN;
    }
    private boolean isPasive(LivingEntity entity){
        return entity instanceof Animal || entity instanceof RPGEntityPasive;
    }
    private boolean isAgresive(LivingEntity entity){
        return isEnemy(entity) && (entity instanceof Monster || entity instanceof RPGEntityAgro || isNeutral(entity));
    }
    private boolean isNeutral(LivingEntity entity){
        return entity instanceof RPGEntityTameable || entity instanceof AbstractGolem || entity instanceof MutantGolemEntity;
    }
    private boolean isEnemy(LivingEntity entity){
        return entity instanceof Enemy;
    }
}*/