package net.cheto97.rpgcraftmod.fluid;

import com.mojang.math.Vector3f;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {
    public static final ResourceLocation MANA_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation MANA_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation  MANA_OVERLAY_RL = new ResourceLocation(RpgcraftMod.MOD_ID,"misc/mana_fluid");

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, RpgcraftMod.MOD_ID);

    public static final RegistryObject<FluidType> MANA_FLUID_TYPE = register("mana_fluid",
            FluidType.Properties.create()
                    .canExtinguish(true)
                    .canPushEntity(true)
                    .canDrown(true)
                    .canSwim(true)
                    .temperature(25)
                    .lightLevel(15)
                    .density(15)
                    .viscosity(8)
                    .sound(SoundAction.get("drink"), SoundEvents.WITCH_DRINK)
    );

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties){
        return FLUID_TYPES.register(name, () -> new BaseFluidType(MANA_STILL_RL, MANA_FLOWING_RL,MANA_OVERLAY_RL, 0xFF00FFFF, new Vector3f(0f/255f,255f/255f,255f/255f), properties));
    }


    public static void register(IEventBus eventBus){
        FLUID_TYPES.register(eventBus);
    }

}
