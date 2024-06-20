package net.cheto97.rpgcraftmod;

import net.cheto97.rpgcraftmod.ModHud.Hud;
import net.cheto97.rpgcraftmod.ModHud.huds.*;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.block.entity.ModBlockEntities;
import net.cheto97.rpgcraftmod.block.entity.renderer.CraftingTableBlockEntityRenderer;
import net.cheto97.rpgcraftmod.block.entity.renderer.DeathContainerBlockEntityRenderer;
import net.cheto97.rpgcraftmod.block.entity.renderer.GemInfusingStationBlockEntityRenderer;
import net.cheto97.rpgcraftmod.block.entity.renderer.WizardTableBlockEntityRenderer;
import net.cheto97.rpgcraftmod.client.curio.CurioRenderer;
import net.cheto97.rpgcraftmod.entity.ModEntityTypes;
import net.cheto97.rpgcraftmod.entity.client.Renderers.*;
import net.cheto97.rpgcraftmod.fluid.*;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.loot.ModLootModifiers;
import net.cheto97.rpgcraftmod.modSounds.ModSoundsRPG;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.painting.ModPaintings;
import net.cheto97.rpgcraftmod.menu.ModMenuTypes;
import net.cheto97.rpgcraftmod.recipe.ModRecipes;
import net.cheto97.rpgcraftmod.screen.*;
import net.cheto97.rpgcraftmod.screen.renderer.tile.ToolLevelingTableRenderer;
import net.cheto97.rpgcraftmod.util.DefaultWingsValues;
import net.cheto97.rpgcraftmod.util.WingsValues;
import net.cheto97.rpgcraftmod.util.levelConfig.utils.ConfigManager;
import net.cheto97.rpgcraftmod.villager.ModVillagers;
import net.cheto97.rpgcraftmod.world.feature.ModConfiguredFeatures;
import net.cheto97.rpgcraftmod.world.feature.ModPlacedFeatures;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.bernie.geckolib3.GeckoLib;

import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.*;
import java.util.function.Function;

@Mod(RpgcraftMod.MOD_ID)
public class RpgcraftMod{
    public static final String MOD_ID = "rpgcraftmod";
    public static RpgcraftMod instance;
    public static Settings settings;



    public static final Logger LOGGER = LogManager.getLogger(RpgcraftMod.MOD_ID);
    public static EntityRendererProvider.Context context;
    public static Map<String, Hud> huds = new LinkedHashMap<>();
    public static final Function<Entity, WingsValues> WINGS = (entity) -> DefaultWingsValues.INSTANCE;
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
        ModSoundsRPG.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        GeckoLib.initialize();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);
        MinecraftForge.EVENT_BUS.register(this);

    }
    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.CHARM.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.NECKLACE.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BACK.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BRACELET.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HANDS.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.RING.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("aura")
                        .size(1)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("spells")
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("combat_arts")
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("wing")
                        .size(1)
                        .build());
    }
    private void processIMC(final InterModProcessEvent event) {
    }
    private void commonSetup(final FMLCommonSetupEvent event){
        ConfigManager.setup();
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntityTypes.MUTANT_GOLEM.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkAnyLightMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.DRAKE.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.DRAKE_2.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.DRAKE_3.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.DRAKE_4.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.DRAKE_5.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.DRAKE_6.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.DRAKE_7.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.KOBOLD_WARRIOR.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkAnyLightMonsterSpawnRules);
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
            MenuScreens.register(ModMenuTypes.WIZARD_TABLE_MENU.get(), WizardTableScreen::new);
            MenuScreens.register(ModMenuTypes.PLAYER_STATS_MENU.get(), PlayerStatsScreen::new);
            MenuScreens.register(ModMenuTypes.PLAYER_CLASS_SELECT_MENU.get(), PlayerClassSelectScreen::new);
            MenuScreens.register(ModMenuTypes.GEM_INFUSING_STATION_MENU.get(), GemInfusingStationScreen::new);
            MenuScreens.register(ModMenuTypes.TLT_CONTAINER.get(), ToolLevelingTableScreen::new);
            MenuScreens.register(ModMenuTypes.CRAFTING_MENU_TYPE.get(), CraftingScreen::new);
            BlockEntityRenderers.register(ModBlockEntities.TLT_TILE_ENTITY.get(), ToolLevelingTableRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.GEM_INFUSING_STATION.get(), GemInfusingStationBlockEntityRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.WIZARD_TABLE.get(), WizardTableBlockEntityRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.CRAFTING_TABLE_BLOCK_ENTITY.get(), CraftingTableBlockEntityRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.DEATH_CONTAINER_BLOCK_ENTITY.get(), DeathContainerBlockEntityRenderer::new);

            CurioRenderer.register();

            EntityRenderers.register(ModEntityTypes.MUTANT_GOLEM.get(), MutantGolemRenderer::new);
            EntityRenderers.register(ModEntityTypes.DRAKE.get(), DrakeV1Renderer::new);
            EntityRenderers.register(ModEntityTypes.DRAKE_2.get(), DrakeV2Renderer::new);
            EntityRenderers.register(ModEntityTypes.DRAKE_3.get(), DrakeV3Renderer::new);
            EntityRenderers.register(ModEntityTypes.DRAKE_4.get(), DrakeV4Renderer::new);
            EntityRenderers.register(ModEntityTypes.DRAKE_5.get(), DrakeV5Renderer::new);
            EntityRenderers.register(ModEntityTypes.DRAKE_6.get(), DrakeV6Renderer::new);
            EntityRenderers.register(ModEntityTypes.DRAKE_7.get(), DrakeV7Renderer::new);
            EntityRenderers.register(ModEntityTypes.KOBOLD_WARRIOR.get(), KoboldWarriorRenderer::new);
            EntityRenderers.register(ModEntityTypes.KOBOLD_WARRIOR.get(), KoboldWarriorRendererB::new);
            EntityRenderers.register(ModEntityTypes.KOBOLD_WARRIOR.get(), KoboldWarriorRendererC::new);
        }
    }
    public static void registerHud(Hud hud) {
        RpgcraftMod.huds.put(hud.getHudKey(), hud);
    }
    public static Hud getActiveHud() {
        return RpgcraftMod.huds.get(RpgcraftMod.settings.getStringValue(Settings.hud_type));
    }
}