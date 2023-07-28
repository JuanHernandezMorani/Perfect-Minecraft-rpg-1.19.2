package net.cheto97.rpgcraftmod.entity;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.custom.MutantGolemEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RpgcraftMod.MOD_ID);


    public static final RegistryObject<EntityType<MutantGolemEntity>> MUTANT_GOLEM =
            ENTITY_TYPES.register("mutant_golem",
                    () -> EntityType.Builder.of(MutantGolemEntity::new, MobCategory.MONSTER)
                            .sized(0.4f, 1.5f)
                            .canSpawnFarFromPlayer()
                            .fireImmune()
                            .build(new ResourceLocation(RpgcraftMod.MOD_ID, "mutant_golem").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}