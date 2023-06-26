package net.cheto97.rpgcraftmod.event;

import net.cheto97.rpgcraftmod.ModHud.huds.HudHotbarWidget;
import net.cheto97.rpgcraftmod.RenderOverlay;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.DrinkManaFluidC2SPacket;
import net.cheto97.rpgcraftmod.networking.packet.C2S.ViewStatsC2SPacket;
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
            if (RpgcraftMod.getActiveHud() instanceof HudHotbarWidget) {
                event.setPosY(event.getPosY() - 22);
            }
        }

        @SubscribeEvent
        public static void onRenderGameOverlay(RenderGuiOverlayEvent event){
            Minecraft mc = Minecraft.getInstance();
            RpgcraftMod mod = RpgcraftMod.instance;
                if (event.getOverlay().id().toString().contains("air_level")) {
                    event.setCanceled(true);
                } else if (event.getOverlay().id().toString().contains("armor_level")) {
                    event.setCanceled(true);
                } else if (event.getOverlay().id().toString().contains("experience_bar")) {
                    event.setCanceled(true);
                } else if (event.getOverlay().id().toString().contains("food") || event.getOverlay().toString().contains("stam")) {
                    event.setCanceled(true);
                } else if (event.getOverlay().id().toString().contains("player_health") || event.getOverlay().id().toString().contains("mount_health")) {
                    event.setCanceled(true);
                } else if(event.getOverlay().id().toString().contains("jump_bar")){
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
    }
}