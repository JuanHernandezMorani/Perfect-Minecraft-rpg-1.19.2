package net.cheto97.rpgcraftmod.event;

import net.cheto97.rpgcraftmod.ModHud.huds.HudHotbarWidget;
import net.cheto97.rpgcraftmod.RenderOverlay;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.wings.WingItem;
import net.cheto97.rpgcraftmod.item.wings.model.*;
import net.cheto97.rpgcraftmod.item.wings.renderer.WingsFeatureRenderer;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.DrinkManaFluidC2SPacket;
import net.cheto97.rpgcraftmod.networking.packet.C2S.ViewStatsC2SPacket;
import net.cheto97.rpgcraftmod.util.ColourHelper;
import net.cheto97.rpgcraftmod.util.KeyBinding;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.HashMap;

import static net.cheto97.rpgcraftmod.item.ModItems.*;

public class ClientEvents {
    public static final ModelLayerLocation FEATHERED = new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/feathered"), "main");
    public static final ModelLayerLocation LEATHER = new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/leather"), "main");
    public static final ModelLayerLocation LIGHT = new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/light"), "main");
    public static final ModelLayerLocation FLANDRE = new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/flandre"), "main");
    public static final ModelLayerLocation DISCORD = new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/discord"), "main");
    public static final ModelLayerLocation ZANZA = new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/zanza"), "main");
    
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
            event.registerLayerDefinition(FEATHERED, FeatheredWingsModel::getLayerDefinition);
            event.registerLayerDefinition(LEATHER, LeatherWingsModel::getLayerDefinition);
            event.registerLayerDefinition(LIGHT, LightWingsModel::getLayerDefinition);
            event.registerLayerDefinition(FLANDRE, FlandresWingsModel::getLayerDefinition);
            event.registerLayerDefinition(DISCORD, DiscordsWingsModel::getLayerDefinition);
            event.registerLayerDefinition(ZANZA, ZanzasWingsModel::getLayerDefinition);
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
            customItems.put(0, WHITE_FEATHERED_WINGS.get().asItem());
            customItems.put(1, ORANGE_FEATHERED_WINGS.get().asItem());
            customItems.put(2, MAGENTA_FEATHERED_WINGS.get().asItem());
            customItems.put(3, LIGHT_BLUE_FEATHERED_WINGS.get().asItem());
            customItems.put(4, YELLOW_FEATHERED_WINGS.get().asItem());
            customItems.put(5, LIME_FEATHERED_WINGS.get().asItem());
            customItems.put(6, PINK_FEATHERED_WINGS.get().asItem());
            customItems.put(7, GREY_FEATHERED_WINGS.get().asItem());
            customItems.put(8, LIGHT_GREY_FEATHERED_WINGS.get().asItem());
            customItems.put(9, CYAN_FEATHERED_WINGS.get().asItem());
            customItems.put(10, PURPLE_FEATHERED_WINGS.get().asItem());
            customItems.put(11, BLUE_FEATHERED_WINGS.get().asItem());
            customItems.put(12, BROWN_FEATHERED_WINGS.get().asItem());
            customItems.put(13, GREEN_FEATHERED_WINGS.get().asItem());
            customItems.put(14, RED_FEATHERED_WINGS.get().asItem());
            customItems.put(15, BLACK_FEATHERED_WINGS.get().asItem());

            customItems.put(16, WHITE_DRAGON_WINGS.get().asItem());
            customItems.put(17, ORANGE_DRAGON_WINGS.get().asItem());
            customItems.put(18, MAGENTA_DRAGON_WINGS.get().asItem());
            customItems.put(19, LIGHT_BLUE_DRAGON_WINGS.get().asItem());
            customItems.put(20, YELLOW_DRAGON_WINGS.get().asItem());
            customItems.put(21, LIME_DRAGON_WINGS.get().asItem());
            customItems.put(22, PINK_DRAGON_WINGS.get().asItem());
            customItems.put(23, GREY_DRAGON_WINGS.get().asItem());
            customItems.put(24, LIGHT_GREY_DRAGON_WINGS.get().asItem());
            customItems.put(25, CYAN_DRAGON_WINGS.get().asItem());
            customItems.put(26, PURPLE_DRAGON_WINGS.get().asItem());
            customItems.put(27, BLUE_DRAGON_WINGS.get().asItem());
            customItems.put(28, BROWN_DRAGON_WINGS.get().asItem());
            customItems.put(29, GREEN_DRAGON_WINGS.get().asItem());
            customItems.put(30, RED_DRAGON_WINGS.get().asItem());
            customItems.put(31, BLACK_DRAGON_WINGS.get().asItem());

            customItems.put(32, WHITE_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(33, ORANGE_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(34, MAGENTA_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(35, LIGHT_BLUE_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(36, YELLOW_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(37, LIME_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(38, PINK_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(39, GREY_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(40, LIGHT_GREY_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(41, CYAN_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(42, PURPLE_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(43, BLUE_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(44, BROWN_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(45, GREEN_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(46, RED_MECHANICAL_FEATHERED_WINGS.get().asItem());
            customItems.put(47, BLACK_MECHANICAL_FEATHERED_WINGS.get().asItem());

            customItems.put(48, WHITE_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(49, ORANGE_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(50, MAGENTA_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(51, LIGHT_BLUE_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(52, YELLOW_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(53, LIME_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(54, PINK_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(55, GREY_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(56, LIGHT_GREY_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(57, CYAN_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(58, PURPLE_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(59, BLUE_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(60, BROWN_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(61, GREEN_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(62, RED_MECHANICAL_LEATHER_WINGS.get().asItem());
            customItems.put(63, BLACK_MECHANICAL_LEATHER_WINGS.get().asItem());

            customItems.put(64, WHITE_LIGHT_WINGS.get().asItem());
            customItems.put(65, ORANGE_LIGHT_WINGS.get().asItem());
            customItems.put(66, MAGENTA_LIGHT_WINGS.get().asItem());
            customItems.put(67, LIGHT_BLUE_LIGHT_WINGS.get().asItem());
            customItems.put(68, YELLOW_LIGHT_WINGS.get().asItem());
            customItems.put(69, LIME_LIGHT_WINGS.get().asItem());
            customItems.put(70, PINK_LIGHT_WINGS.get().asItem());
            customItems.put(71, GREY_LIGHT_WINGS.get().asItem());
            customItems.put(72, LIGHT_GREY_LIGHT_WINGS.get().asItem());
            customItems.put(73, CYAN_LIGHT_WINGS.get().asItem());
            customItems.put(74, PURPLE_LIGHT_WINGS.get().asItem());
            customItems.put(75, BLUE_LIGHT_WINGS.get().asItem());
            customItems.put(76, BROWN_LIGHT_WINGS.get().asItem());
            customItems.put(77, GREEN_LIGHT_WINGS.get().asItem());
            customItems.put(78, RED_LIGHT_WINGS.get().asItem());
            customItems.put(79, BLACK_LIGHT_WINGS.get().asItem());
        }


    }
}