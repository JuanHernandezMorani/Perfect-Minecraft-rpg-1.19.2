package net.cheto97.rpgcraftmod.world.feature;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, RpgcraftMod.MOD_ID);


    public static final RegistryObject<PlacedFeature> ZAFIRO_ORE_PLACED = PLACED_FEATURES.register("zafiro_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ZAFIRO_ORE.getHolder().get(),
                    commonOrePlacement(7,
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> END_ZAFIRO_ORE_PLACED = PLACED_FEATURES.register("end_zafiro_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.END_ZAFIRO_ORE.getHolder().get(), rareOrePlacement(7,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> NETHER_ZAFIRO_ORE_PLACED = PLACED_FEATURES.register("nether_zafiro_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.NETHER_ZAFIRO_ORE.getHolder().get(), commonOrePlacement(7,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> LIQUID_MANA_BLOCK_PLACED = PLACED_FEATURES.register("liquid_mana_block_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.LIQUID_MANA_BLOCK.getHolder().get(), rareOrePlacement(7,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> RED_MAPLE_CHECKED = PLACED_FEATURES.register("red_maple_checked",
            () -> new PlacedFeature(ModConfiguredFeatures.RED_MAPLE.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.RED_MAPLE_SAPLING.get()))));

    public static final RegistryObject<PlacedFeature> RED_MAPLE_PLACED = PLACED_FEATURES.register("red_maple_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.RED_MAPLE_SPAWN.getHolder().get(), VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(6, 0.2f, 4))));

    public static final RegistryObject<PlacedFeature>  SAPPHIRE_GEODE_PLACED = PLACED_FEATURES.register("sapphire_geode_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.SAPPHIRE_GEODE.getHolder().get(),
                    List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(50)),
                            BiomeFilter.biome())));

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }
}
