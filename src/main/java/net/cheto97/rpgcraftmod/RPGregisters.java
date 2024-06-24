package net.cheto97.rpgcraftmod;

import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.block.entity.ModBlockEntities;
import net.cheto97.rpgcraftmod.entity.ModEntityTypes;
import net.cheto97.rpgcraftmod.fluid.ModFluidTypes;
import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.loot.ModLootModifiers;
import net.cheto97.rpgcraftmod.menu.ModMenuTypes;
import net.cheto97.rpgcraftmod.modSounds.ModSoundsRPG;
import net.cheto97.rpgcraftmod.painting.ModPaintings;
import net.cheto97.rpgcraftmod.recipe.ModRecipes;
import net.cheto97.rpgcraftmod.villager.ModVillagers;
import net.cheto97.rpgcraftmod.world.feature.ModConfiguredFeatures;
import net.cheto97.rpgcraftmod.world.feature.ModPlacedFeatures;
import net.minecraftforge.eventbus.api.IEventBus;

public class RPGregisters {
    public static void init(IEventBus modEventBus){
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
    }
}
