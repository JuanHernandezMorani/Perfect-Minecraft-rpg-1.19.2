package net.cheto97.rpgcraftmod.event;

import net.cheto97.rpgcraftmod.ModHud.huds.HudHotbarWidget;
import net.cheto97.rpgcraftmod.RenderOverlay;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.DrinkManaFluidC2SPacket;
import net.cheto97.rpgcraftmod.networking.packet.ViewStatsC2SPacket;
import net.cheto97.rpgcraftmod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
            if (RpgcraftMod.instance.getActiveHud() instanceof HudHotbarWidget) {
                event.setPosY(event.getPosY() - 22);
            }
        }

        @SubscribeEvent
        public static void onRenderGameOverlay(RenderGuiOverlayEvent event){
            if (event.getOverlay().toString().contains("air") || event.getOverlay().toString().contains("AIR")) {
                    event.setCanceled(true);
            } else if (event.getOverlay().toString().contains("armor") || event.getOverlay().toString().contains("ARMOR")) {
                    event.setCanceled(true);
            } else if (event.getOverlay().toString().contains("xp") || event.getOverlay().toString().contains("XP")) {
                    event.setCanceled(true);
            } else if (event.getOverlay().toString().contains("food") || event.getOverlay().toString().contains("FOOD") || event.getOverlay().toString().contains("stam") || event.getOverlay().toString().contains("STAM")) {
                    event.setCanceled(true);
            } else if (event.getOverlay().toString().contains("health") || event.getOverlay().toString().contains("HEALTH")) {
                    event.setCanceled(true);
            } else if (event.getOverlay().toString().contains("mount") || event.getOverlay().toString().contains("MOUNT")) {
                    event.setCanceled(true);
            } else event.setCanceled(event.getOverlay().toString().contains("jump") || event.getOverlay().toString().contains("JUMP"));
            Minecraft mc = Minecraft.getInstance();
            RpgcraftMod mod = RpgcraftMod.instance;


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

       /*
         *
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
         event.registerAboveAll("mana", ManaHudOverlay.HUD_MANA);
        }
         */
    }
}