package net.cheto97.rpgcraftmod.entity;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.custom.*;
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

     public static final RegistryObject<EntityType<DrakeV1Entity>> DRAKE =
             ENTITY_TYPES.register("drake_1",
             () -> EntityType.Builder.of(DrakeV1Entity::new, MobCategory.MONSTER)
                     .sized(1.0f,1.0f)
                     .canSpawnFarFromPlayer()
                     .build(new ResourceLocation(RpgcraftMod.MOD_ID, "drake_1").toString()));

    public static final RegistryObject<EntityType<DrakeV2Entity>> DRAKE_2 =
            ENTITY_TYPES.register("drake_2",
            () -> EntityType.Builder.of(DrakeV2Entity::new, MobCategory.MONSTER)
                    .sized(1.0f,1.0f)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(RpgcraftMod.MOD_ID, "drake_2").toString()));

    public static final RegistryObject<EntityType<DrakeV3Entity>> DRAKE_3 =
            ENTITY_TYPES.register("drake_3",
            () -> EntityType.Builder.of(DrakeV3Entity::new, MobCategory.MONSTER)
                    .sized(1.0f,1.0f)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(RpgcraftMod.MOD_ID, "drake_3").toString()));

    public static final RegistryObject<EntityType<DrakeV4Entity>> DRAKE_4 =
            ENTITY_TYPES.register("drake_4",
            () -> EntityType.Builder.of(DrakeV4Entity::new, MobCategory.MONSTER)
                    .sized(1.0f,1.0f)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(RpgcraftMod.MOD_ID, "drake_4").toString()));

    public static final RegistryObject<EntityType<DrakeV5Entity>> DRAKE_5 =
            ENTITY_TYPES.register("drake_5",
            () -> EntityType.Builder.of(DrakeV5Entity::new, MobCategory.MONSTER)
                    .sized(1.0f,1.0f)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(RpgcraftMod.MOD_ID, "drake_5").toString()));

    public static final RegistryObject<EntityType<DrakeV6Entity>> DRAKE_6 =
            ENTITY_TYPES.register("drake_6",
            () -> EntityType.Builder.of(DrakeV6Entity::new, MobCategory.MONSTER)
                    .sized(1.0f,1.0f)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(RpgcraftMod.MOD_ID, "drake_6").toString()));

    public static final RegistryObject<EntityType<DrakeV7Entity>> DRAKE_7 =
            ENTITY_TYPES.register("drake_7",
            () -> EntityType.Builder.of(DrakeV7Entity::new, MobCategory.MONSTER)
                    .sized(1.0f,1.0f)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(RpgcraftMod.MOD_ID, "drake_7").toString()));

    public static final RegistryObject<EntityType<KoboldWarriorEntity>> KOBOLD_WARRIOR =
            ENTITY_TYPES.register("kobold_warrior",
                    () -> EntityType.Builder.of(KoboldWarriorEntity::new, MobCategory.MONSTER)
                            .sized(1.f,1.0f)
                            .canSpawnFarFromPlayer()
                            .build(new ResourceLocation(RpgcraftMod.MOD_ID,"kobold_warrior").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}