package net.cheto97.rpgcraftmod.world.feature;

import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
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

    public static final Supplier<List<OreConfiguration.TargetBlockState>> LIQUID_MANA_BLOCKS = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), ModBlocks.LIQUID_MANA_BLOCK.get().defaultBlockState())
    ));
    public static final RegistryObject<ConfiguredFeature<?, ?>> RED_MAPLE =
            CONFIGURED_FEATURES.register("red_maple", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ModBlocks.RED_MAPLE_LOG.get()),
                            new FancyTrunkPlacer(12, 12, 3),
                            BlockStateProvider.simple(ModBlocks.RED_MAPLE_LEAVES.get()),
                            new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), 6),
                            new ThreeLayersFeatureSize(30, 25, 8,8,8, OptionalInt.of(8)))
                            .build()));


    public static final RegistryObject<ConfiguredFeature<?, ?>> RED_MAPLE_SPAWN =
            CONFIGURED_FEATURES.register("red_maple_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            ModPlacedFeatures.RED_MAPLE_CHECKED.getHolder().get(),
                            0.5F)), ModPlacedFeatures.RED_MAPLE_CHECKED.getHolder().get())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ZAFIRO_ORE = CONFIGURED_FEATURES.register("zafiro_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_ZAFIRO_ORES.get(), 12)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> END_ZAFIRO_ORE = CONFIGURED_FEATURES.register("end_zafiro_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(END_ZAFIRO_ORES.get(), 9)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> NETHER_ZAFIRO_ORE = CONFIGURED_FEATURES.register("nether_zafiro_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(NETHER_ZAFIRO_ORES.get(), 9)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> LIQUID_MANA_BLOCK = CONFIGURED_FEATURES.register("liquid_mana_block",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(LIQUID_MANA_BLOCKS.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> SAPPHIRE_GEODE = CONFIGURED_FEATURES.register("sapphire_geode",
            () -> new ConfiguredFeature<>(Feature.GEODE,
            new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.DEEPSLATE),
            BlockStateProvider.simple(ModBlocks.bloque_mineral_zafiro.get()),
            BlockStateProvider.simple(Blocks.POLISHED_BLACKSTONE_BRICKS),
            BlockStateProvider.simple(Blocks.GILDED_BLACKSTONE),
            List.of(Blocks.LODESTONE.defaultBlockState()),
    BlockTags.FEATURES_CANNOT_REPLACE , BlockTags.GEODE_INVALID_BLOCKS),
            new GeodeLayerSettings(1.7D, 1.2D, 2.5D, 3.5D),
                        new GeodeCrackSettings(0.5D, 3D, 2), 0.5D, 0.1D,
            true, UniformInt.of(3, 8),
            UniformInt.of(2, 6), UniformInt.of(1, 2),
            -18, 18, 0.075D, 1)));

    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURES.register(eventBus);
    }
}