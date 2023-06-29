package net.cheto97.rpgcraftmod.villager;

import com.google.common.collect.ImmutableSet;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, RpgcraftMod.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, RpgcraftMod.MOD_ID);

    public static final RegistryObject<PoiType> MANA_BLOCK_POI = POI_TYPES.register("mana_block_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.wizard_table.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> MAGIC_MASTER = VILLAGER_PROFESSIONS.register("magic_master",
            () -> new VillagerProfession("magic_master", x -> x.get() == MANA_BLOCK_POI.get(),
                    x -> x.get() == MANA_BLOCK_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_ARMORER));

public static void registerPOIs(){
    try{
        ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class)
                .invoke(null, MANA_BLOCK_POI.get());

    } catch (InvocationTargetException | IllegalAccessException exception){
        exception.printStackTrace();
    }
}

    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
