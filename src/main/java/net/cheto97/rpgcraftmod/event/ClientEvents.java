package net.cheto97.rpgcraftmod.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.parts.compassWidget;
import net.cheto97.rpgcraftmod.ModHud.parts.playerStats;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.model.AsuraWingsModel;
import net.cheto97.rpgcraftmod.item.model.AuraModel;
import net.cheto97.rpgcraftmod.item.model.VoltWingsModel;
import net.cheto97.rpgcraftmod.item.model.WingsModel;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.ActivateSkillC2SPacket;
import net.cheto97.rpgcraftmod.networking.packet.C2S.DrinkManaFluidC2SPacket;
import net.cheto97.rpgcraftmod.networking.packet.C2S.ViewStatsC2SPacket;
import net.cheto97.rpgcraftmod.util.KeyBinding;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.*;

public class ClientEvents {
    
    @Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents{
        

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(KeyBinding.DRINKING_KEY.consumeClick()){
                ModMessages.sendToServer(new DrinkManaFluidC2SPacket());
            }
            if(KeyBinding.STATS_KEY.consumeClick()){
                ModMessages.sendToServer(new ViewStatsC2SPacket());
            }
            if(KeyBinding.SKILL_1_STATS_KEY.consumeClick()){
                ModMessages.sendToServer(new ActivateSkillC2SPacket(1));
            }
            if(KeyBinding.SKILL_2_STATS_KEY.consumeClick()){
                ModMessages.sendToServer(new ActivateSkillC2SPacket(2));
            }
            if(KeyBinding.SKILL_3_STATS_KEY.consumeClick()){
                ModMessages.sendToServer(new ActivateSkillC2SPacket(3));
            }
        }

        @SubscribeEvent
        public static void onRenderGameOverlay(RenderGuiOverlayEvent event){
            Minecraft mc = Minecraft.getInstance();
            ForgeGui emptyGui = new ForgeGui(mc);
            PoseStack ms = event.getPoseStack();

                if (event.getOverlay().id().toString().contains("air_level") || event.getOverlay().id().toString().contains("armor_level")
                   || event.getOverlay().id().toString().contains("experience_bar") || event.getOverlay().id().toString().contains("food") || event.getOverlay().toString().contains("stamina")
                   || event.getOverlay().id().toString().contains("player_health") || event.getOverlay().id().toString().contains("mount_health")
                   || event.getOverlay().id().toString().contains("jump_bar")) {
                    event.getOverlay().overlay().render(emptyGui,event.getPoseStack(),0,0,0);
                }

            ms.pushPose();
            RenderSystem.enableBlend();
            new compassWidget(ms,mc);
            new playerStats(ms,mc);
            ms.popPose();
        }

    }

    @Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.DRINKING_KEY);
            event.register(KeyBinding.STATS_KEY);
            event.register(KeyBinding.SKILL_1_STATS_KEY);
            event.register(KeyBinding.SKILL_2_STATS_KEY);
            event.register(KeyBinding.SKILL_3_STATS_KEY);
        }
        @SubscribeEvent
        public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            // wings
            registerWings(event, WINGS_1_LAYER,1);
            registerWings(event, WINGS_2_LAYER,1);
            registerWings(event, ASURA_WINGS_LAYER,2);
            registerWings(event, VOLT_WINGS_LAYER,3);

            // auras
            registerAuras(event, COMMON_AURA_LAYER);
            registerAuras(event, CHAMPION_AURA_LAYER);
            registerAuras(event, DEMON_AURA_LAYER);
            registerAuras(event, BOSS_AURA_LAYER);
            registerAuras(event, BRUTAL_AURA_LAYER);
            registerAuras(event, ELITE_AURA_LAYER);
            registerAuras(event, HERO_AURA_LAYER);
            registerAuras(event, LEGENDARY_AURA_LAYER);
            registerAuras(event, MYTHICAL_AURA_LAYER);
            registerAuras(event, SEMI_BOSS_AURA_LAYER);
            registerAuras(event, UNIQUE_AURA_LAYER);
        }
        @SubscribeEvent
        public static void onRegisterAtlasSprites(TextureStitchEvent.Pre event){
            event.addSprite(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/slot/aura_slot"));
            event.addSprite(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/slot/wing_slot"));
        }
        private static void registerWings(EntityRenderersEvent.RegisterLayerDefinitions event, ModelLayerLocation itemLocation, int variant){
            switch(variant){
                case 1 ->  event.registerLayerDefinition(itemLocation, WingsModel::createLayer);
                case 2 -> event.registerLayerDefinition(itemLocation, AsuraWingsModel::createLayer);
                case 3 -> event.registerLayerDefinition(itemLocation, VoltWingsModel::createLayer);
            }
        }
        private static void registerAuras(EntityRenderersEvent.RegisterLayerDefinitions event, ModelLayerLocation itemLocation){
            event.registerLayerDefinition(itemLocation, AuraModel::createLayer);
        }
    }
}