package net.cheto97.rpgcraftmod.world.feature;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.minecraft.core.Registry;
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
