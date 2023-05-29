package net.cheto97.rpgcraftmod.fluid;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, RpgcraftMod.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_MANA = FLUIDS.register("mana_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.MANA_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_MANA = FLUIDS.register("flowing_mana",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MANA_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties MANA_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MANA_FLUID_TYPE, SOURCE_MANA, FLOWING_MANA)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .block(ModBlocks.LIQUID_MANA_BLOCK)
            .bucket(ModItems.MANA_BUCKET);

    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
    }
}
