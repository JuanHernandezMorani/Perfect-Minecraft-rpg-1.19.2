package net.cheto97.rpgcraftmod;

import net.cheto97.rpgcraftmod.client.curio.EnqueueEvent;
import net.cheto97.rpgcraftmod.register_renderers.*;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.spawn_placements.MobsSpawnPlacement;
import net.cheto97.rpgcraftmod.util.DefaultWingsValues;
import net.cheto97.rpgcraftmod.util.WingsValues;
import net.cheto97.rpgcraftmod.util.levelConfig.utils.ConfigManager;
import net.cheto97.rpgcraftmod.villager.ModVillagers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.bernie.geckolib3.GeckoLib;

import java.util.function.Function;

@Mod(RpgcraftMod.MOD_ID)
public class RpgcraftMod{
    public static final String MOD_ID = "rpgcraftmod";
    public static RpgcraftMod instance;

    public static final Logger LOGGER = LogManager.getLogger(RpgcraftMod.MOD_ID);
    public static EntityRendererProvider.Context context;
    public static final Function<Entity, WingsValues> WINGS = (entity) -> DefaultWingsValues.INSTANCE;
    public RpgcraftMod(){
        instance = this;
        GeckoLib.initialize();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        RPGregisters.init(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
    }
    private void commonSetup(final FMLCommonSetupEvent event){
        ConfigManager.setup();
        event.enqueueWork(() -> {
            MobsSpawnPlacement.register();
            ModMessages.register();
            ModVillagers.registerPOIs();
        });
    }
    private void enqueueIMC(final InterModEnqueueEvent event) {
        EnqueueEvent.register(event);
    }
    private void processIMC(final InterModProcessEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            BlockItemsTypesRender.register();
            BlockEntityRender.register();
            MenuScreensRenderer.register();
            CurioRenderer.register();
            MobsRenderer.register();
        }
    }
}