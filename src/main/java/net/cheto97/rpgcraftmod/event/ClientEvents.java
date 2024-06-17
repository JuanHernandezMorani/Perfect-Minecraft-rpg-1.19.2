package net.cheto97.rpgcraftmod.event;

import net.cheto97.rpgcraftmod.ModHud.huds.HudHotbarWidget;
import net.cheto97.rpgcraftmod.RenderOverlay;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.wings.model.*;
import net.cheto97.rpgcraftmod.custom.curios.auras.model.*;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.DrinkManaFluidC2SPacket;
import net.cheto97.rpgcraftmod.networking.packet.C2S.ViewStatsC2SPacket;
import net.cheto97.rpgcraftmod.util.KeyBinding;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
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
        }

        @SubscribeEvent
        public void onChatRender(CustomizeGuiOverlayEvent.Chat event) {
            if (RpgcraftMod.getActiveHud() instanceof HudHotbarWidget) {
                event.setPosY(event.getPosY() - 22);
            }
        }

        @SubscribeEvent
        public static void onRenderGameOverlay(RenderGuiOverlayEvent event){
            Minecraft mc = Minecraft.getInstance();
            RpgcraftMod mod = RpgcraftMod.instance;
                if (event.getOverlay().id().toString().contains("air_level") || event.getOverlay().id().toString().contains("armor_level")
                   || event.getOverlay().id().toString().contains("experience_bar") || event.getOverlay().id().toString().contains("food") || event.getOverlay().toString().contains("stam")
                   || event.getOverlay().id().toString().contains("player_health") || event.getOverlay().id().toString().contains("mount_health")
                   || event.getOverlay().id().toString().contains("jump_bar") || event.getOverlay().id().toString().contains("hotbar")) {
                    event.setCanceled(true);
                }
                RenderOverlay.renderOverlay(mod, mc, event.getPoseStack(), event.getPartialTick());
        }

    }


    @Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.DRINKING_KEY);
            event.register(KeyBinding.STATS_KEY);
        }
        @SubscribeEvent
        public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            // wings
            registerWings(event, WINGS_1);
            registerWings(event, WINGS_2);

            // auras
            registerAuras(event, COMMON_AURA);
            registerAuras(event, CHAMPION_AURA);
            registerAuras(event, DEMON_AURA);
            registerAuras(event, BOSS_AURA);
            registerAuras(event, BRUTAL_AURA);
            registerAuras(event, ELITE_AURA);
            registerAuras(event, HERO_AURA);
            registerAuras(event, LEGENDARY_AURA);
            registerAuras(event, MYTHICAL_AURA);
            registerAuras(event, SEMI_BOSS_AURA);
            registerAuras(event, UNIQUE_AURA);
        }
        @SubscribeEvent
        public static void onRegisterAtlasSprites(TextureStitchEvent.Pre event){
            event.addSprite(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/slot/aura_slot"));
            event.addSprite(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/slot/wing_slot"));
        }
        private static void registerWings(EntityRenderersEvent.RegisterLayerDefinitions event, ModelLayerLocation itemLocation){
            event.registerLayerDefinition(itemLocation, WingEntityModel::getModelData);
        }
        private static void registerAuras(EntityRenderersEvent.RegisterLayerDefinitions event, ModelLayerLocation itemLocation){
            event.registerLayerDefinition(itemLocation, AuraModel::createLayer);
        }
    }
}