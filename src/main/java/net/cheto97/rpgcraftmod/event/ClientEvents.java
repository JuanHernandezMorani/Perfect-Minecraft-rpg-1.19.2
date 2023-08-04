package net.cheto97.rpgcraftmod.event;

import net.cheto97.rpgcraftmod.ModHud.huds.HudHotbarWidget;
import net.cheto97.rpgcraftmod.RenderOverlay;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.client.Models.DryadModel;
import net.cheto97.rpgcraftmod.entity.client.Models.WintersGraspModel;
import net.cheto97.rpgcraftmod.item.wings.WingItem;
import net.cheto97.rpgcraftmod.item.wings.model.*;
import net.cheto97.rpgcraftmod.custom.curios.auras.model.*;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.DrinkManaFluidC2SPacket;
import net.cheto97.rpgcraftmod.networking.packet.C2S.ViewStatsC2SPacket;
import net.cheto97.rpgcraftmod.util.ColourHelper;
import net.cheto97.rpgcraftmod.util.KeyBinding;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.*;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.BOSS_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.BRUTAL_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.CHAMPION_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.COMMON_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.DEMON_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.ELITE_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.HERO_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.LEGENDARY_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.MYTHICAL_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.SEMI_BOSS_AURA;
import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.UNIQUE_AURA;
import static net.cheto97.rpgcraftmod.entity.client.Models.DryadModel.LAYER_LOCATION_D;
import static net.cheto97.rpgcraftmod.entity.client.Models.WintersGraspModel.LAYER_LOCATION_WG;
import static net.cheto97.rpgcraftmod.item.ModItems.*;

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
            event.registerLayerDefinition(LIGHT, LightWingsModel::getLayerDefinition);
            event.registerLayerDefinition(ZANZA, ZanzasWingsModel::getLayerDefinition);
            event.registerLayerDefinition(COMMON_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(CHAMPION_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(DEMON_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(BOSS_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(BRUTAL_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(ELITE_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(HERO_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(LEGENDARY_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(MYTHICAL_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(SEMI_BOSS_AURA, AuraModel::createLayer);
            event.registerLayerDefinition(UNIQUE_AURA, AuraModel::createLayer);

            event.registerLayerDefinition(LAYER_LOCATION_D, DryadModel::createBodyLayer);
            event.registerLayerDefinition(LAYER_LOCATION_WG, WintersGraspModel::createBodyLayer);
        }
        @SubscribeEvent
        public static void onRegisterAtlasSprites(TextureStitchEvent.Pre event){
            event.addSprite(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/slot/aura_slot"));
            event.addSprite(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/slot/wing_slot"));
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            HashMap<Integer, Item> customItems = new HashMap<>();
            fillHashMap(customItems);

            for(int i = 0; i < customItems.size();i++){
                event.register((stack, tintIndex) -> tintIndex == 0 ? ColourHelper.dyeToDecimal(((WingItem) stack.getItem()).getPrimaryColour()) : ColourHelper.dyeToDecimal(((WingItem) stack.getItem()).getSecondaryColour()),
                        customItems.get(i));
            }
        }
        public static void fillHashMap(HashMap<Integer, Item> customItems) {
            customItems.put(0, WHITE_LIGHT_WINGS.get().asItem());
            customItems.put(1, ORANGE_LIGHT_WINGS.get().asItem());
            customItems.put(2, MAGENTA_LIGHT_WINGS.get().asItem());
            customItems.put(3, LIGHT_BLUE_LIGHT_WINGS.get().asItem());
            customItems.put(4, YELLOW_LIGHT_WINGS.get().asItem());
            customItems.put(5, LIME_LIGHT_WINGS.get().asItem());
            customItems.put(6, PINK_LIGHT_WINGS.get().asItem());
            customItems.put(7, GREY_LIGHT_WINGS.get().asItem());
            customItems.put(8, LIGHT_GREY_LIGHT_WINGS.get().asItem());
            customItems.put(9, CYAN_LIGHT_WINGS.get().asItem());
            customItems.put(10, PURPLE_LIGHT_WINGS.get().asItem());
            customItems.put(11, BLUE_LIGHT_WINGS.get().asItem());
            customItems.put(12, BROWN_LIGHT_WINGS.get().asItem());
            customItems.put(13, GREEN_LIGHT_WINGS.get().asItem());
            customItems.put(14, RED_LIGHT_WINGS.get().asItem());
            customItems.put(15, BLACK_LIGHT_WINGS.get().asItem());
        }
    }
}