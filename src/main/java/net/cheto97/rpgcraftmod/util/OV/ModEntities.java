package net.cheto97.rpgcraftmod.util.OV;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RpgcraftMod.MOD_ID);
    public static final RegistryObject<EntityType<Wildfire>> WILDFIRE = ENTITIES.register("wildfire", () -> EntityType.Builder
            .of(Wildfire::new, MobCategory.MONSTER)
            .fireImmune()
            .sized(0.8F, 2.0F)
            .build(new ResourceLocation(RpgcraftMod.MOD_ID, "wildfire").toString()));
    public static final RegistryObject<EntityType<Glutton>> GLUTTON = ENTITIES.register("glutton", () -> EntityType.Builder
            .of(Glutton::new, MobCategory.CREATURE)
            .sized(1.0F, 1.2F)
            .build(new ResourceLocation(RpgcraftMod.MOD_ID, "glutton").toString()));
    public static final RegistryObject<EntityType<Barnacle>> BARNACLE = ENTITIES.register("barnacle", () -> EntityType.Builder
            .of(Barnacle::new, MobCategory.MONSTER)
            .sized(1.2F, 1.2F)
            .build(new ResourceLocation(RpgcraftMod.MOD_ID, "barnacle").toString()));
    public static final RegistryObject<EntityType<CopperGolem>> COPPER_GOLEM = ENTITIES.register("copper_golem", () -> EntityType.Builder
            .of(CopperGolem::new, MobCategory.MISC)
            .sized(0.9F, 1.2F)
            .build(new ResourceLocation(RpgcraftMod.MOD_ID, "copper_golem").toString()));
}
