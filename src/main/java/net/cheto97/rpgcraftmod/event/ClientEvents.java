package net.cheto97.rpgcraftmod.event;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.client.ManaHudOverlay;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.DrinkManaFluidC2SPacket;
import net.cheto97.rpgcraftmod.networking.packet.ViewStatsC2SPacket;
import net.cheto97.rpgcraftmod.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
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
    }

    @Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.DRINKING_KEY);
            event.register(KeyBinding.STATS_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("mana", ManaHudOverlay.HUD_MANA);
        }

    }
}
