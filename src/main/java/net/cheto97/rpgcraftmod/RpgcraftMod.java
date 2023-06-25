package net.cheto97.rpgcraftmod;

import com.mojang.logging.LogUtils;
import net.cheto97.rpgcraftmod.ModHud.Hud;
import net.cheto97.rpgcraftmod.ModHud.huds.HudDefault;
import net.cheto97.rpgcraftmod.ModHud.huds.HudHotbarWidget;
import net.cheto97.rpgcraftmod.ModHud.huds.HudVanilla;
import net.cheto97.rpgcraftmod.ModHud.huds.RPGHud;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.fluid.ModFluidTypes;
import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.painting.ModPaintings;
import net.cheto97.rpgcraftmod.villager.ModVillagers;
import net.cheto97.rpgcraftmod.world.feature.ModConfiguredFeatures;
import net.cheto97.rpgcraftmod.world.feature.ModPlacedFeatures;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

@Mod(RpgcraftMod.MOD_ID)
public class RpgcraftMod {
    public static final String MOD_ID = "rpgcraftmod";
    public static RpgcraftMod instance;
    public static Settings settings;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Map<String, Hud> huds = new LinkedHashMap<String, Hud>();
    public static boolean[] renderDetailsAgain = { false, false, false };


    public RpgcraftMod(){
        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModVillagers.register(modEventBus);
        ModPaintings.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event){

        event.enqueueWork(() -> {
            ModMessages.register();
            ModVillagers.registerPOIs();

        });
    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents{

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MANA.get(),RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MANA.get(),RenderType.translucent());

            RpgcraftMod.settings = new Settings();
            var hudtype = new RPGHud(Minecraft.getInstance(), "rpg", "Rpg");

            RpgcraftMod.registerHud(new HudDefault(Minecraft.getInstance(), "default", "Default"));
            RpgcraftMod.registerHud(new HudVanilla(Minecraft.getInstance(), "vanilla", "Vanilla"));
            RpgcraftMod.registerHud(new HudHotbarWidget(Minecraft.getInstance(), "hotbar", "Hotbar Widget"));
            RpgcraftMod.registerHud(new RPGHud(Minecraft.getInstance(), "rpg", "Rpg"));

            RpgcraftMod.settings.setSetting(Settings.hud_type, hudtype);

        }
    }
    public static void registerHud(Hud hud) {
        RpgcraftMod.huds.put(hud.getHudKey(), hud);
    }
    public static Hud getActiveHud() {
        return RpgcraftMod.huds.get(RpgcraftMod.settings.getStringValue(Settings.hud_type));
    }

}
