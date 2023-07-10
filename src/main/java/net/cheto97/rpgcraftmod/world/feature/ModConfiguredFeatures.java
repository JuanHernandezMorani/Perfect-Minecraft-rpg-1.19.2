package net.cheto97.rpgcraftmod.world.feature;

import com.google.common.base.Suppliers;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, RpgcraftMod.MOD_ID);

    public  static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_ZAFIRO_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.bloque_mineral_zafiro.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.bloque_profundo_mineral_zafiro.get().defaultBlockState()) ));

    public  static final Supplier<List<OreConfiguration.TargetBlockState>> END_ZAFIRO_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), ModBlocks.bloque_profundo_mineral_zafiro.get().defaultBlockState()) ));

    public  static final Supplier<List<OreConfiguration.TargetBlockState>> NETHER_ZAFIRO_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, ModBlocks.bloque_mineral_zafiro.get().defaultBlockState()) ));


    public static final RegistryObject<ConfiguredFeature<?, ?>> ZAFIRO_ORE = CONFIGURED_FEATURES.register("zafiro_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_ZAFIRO_ORES.get(), 12)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> END_ZAFIRO_ORE = CONFIGURED_FEATURES.register("end_zafiro_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(END_ZAFIRO_ORES.get(), 9)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> NETHER_ZAFIRO_ORE = CONFIGURED_FEATURES.register("nether_zafiro_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(NETHER_ZAFIRO_ORES.get(), 9)));

    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURES.register(eventBus);
    }
}
