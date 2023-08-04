package net.cheto97.rpgcraftmod.util.AM;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AMEntities {
  DeferredRegister<EntityType<?>> AM_ENTITY_TYPES =
          DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RpgcraftMod.MOD_ID);

  RegistryObject<EntityType<WaterGuardian>> WATER_GUARDIAN = AM_ENTITY_TYPES.register("water_guardian", () -> EntityType.Builder.of(WaterGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(  1,  1.5F).build("water_guardian"));
  RegistryObject<EntityType<FireGuardian>>  FIRE_GUARDIAN  = AM_ENTITY_TYPES.register("fire_guardian",  () -> EntityType.Builder.of(FireGuardian::new,  MobCategory.MONSTER).clientTrackingRange(8).sized(  1, 3).build("fire_guardian"));
  RegistryObject<EntityType<EarthGuardian>> EARTH_GUARDIAN = AM_ENTITY_TYPES.register("earth_guardian", () -> EntityType.Builder.of(EarthGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized( 1.5F,  2.5F).build("earth_guardian"));
  RegistryObject<EntityType<AirGuardian>> AIR_GUARDIAN = AM_ENTITY_TYPES.register("air_guardian", () -> EntityType.Builder.of(AirGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized( 0.6F, 1.75F).build("air_guardian"));
  RegistryObject<EntityType<IceGuardian>> ICE_GUARDIAN = AM_ENTITY_TYPES.register("ice_guardian", () -> EntityType.Builder.of(IceGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized( 1.5F, 3).build("ice_guardian"));
  RegistryObject<EntityType<LightningGuardian>> LIGHTNING_GUARDIAN = AM_ENTITY_TYPES.register("lightning_guardian", () -> EntityType.Builder.of(LightningGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized( 0.5F, 1.25F).build("lightning_guardian"));
  RegistryObject<EntityType<NatureGuardian>>  NATURE_GUARDIAN  = AM_ENTITY_TYPES.register("nature_guardian", () -> EntityType.Builder.of(NatureGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1.25F, 4.25F).build("nature_guardian"));
  RegistryObject<EntityType<LifeGuardian>>  LIFE_GUARDIAN  = AM_ENTITY_TYPES.register("life_guardian",  () -> EntityType.Builder.of(LifeGuardian::new,  MobCategory.MONSTER).clientTrackingRange(8).sized(  1, 1.25F).build("life_guardian"));
  RegistryObject<EntityType<ArcaneGuardian>>  ARCANE_GUARDIAN  = AM_ENTITY_TYPES.register("arcane_guardian", () -> EntityType.Builder.of(ArcaneGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized( 0.9F, 2.25F).build("arcane_guardian"));
  RegistryObject<EntityType<EnderGuardian>> ENDER_GUARDIAN = AM_ENTITY_TYPES.register("ender_guardian", () -> EntityType.Builder.of(EnderGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(  1, 2.25F).build("ender_guardian"));
  RegistryObject<EntityType<WintersGrasp>>  WINTERS_GRASP  = AM_ENTITY_TYPES.register("winters_grasp",  () -> EntityType.Builder.of(WintersGrasp::new,  MobCategory.MISC) .clientTrackingRange(8).sized(0.25F, 0.25F).build("winters_grasp"));
RegistryObject<EntityType<Dryad>>  DRYAD = AM_ENTITY_TYPES.register("dryad", () -> EntityType.Builder.of(Dryad::new,  MobCategory.AMBIENT).clientTrackingRange(8).sized( 0.6F,  1.8F).build("dryad"));
  RegistryObject<EntityType<Mage>> MAGE = AM_ENTITY_TYPES.register("mage", () -> EntityType.Builder.of(Mage::new, MobCategory.MONSTER).clientTrackingRange(8).sized( 0.6F,  1.8F).build("mage"));
  RegistryObject<EntityType<ManaCreeper>> MANA_CREEPER = AM_ENTITY_TYPES.register("mana_creeper", () -> EntityType.Builder.of(ManaCreeper::new, MobCategory.MONSTER).clientTrackingRange(8).sized( 0.6F,  1.7F).build("mana_creeper"));
  RegistryObject<EntityType<ManaVortex>> MANA_VORTEX = AM_ENTITY_TYPES.register("mana_vortex", () -> EntityType.Builder.of(ManaVortex::new, MobCategory.MISC) .clientTrackingRange(8).sized(0.25F, 0.25F).build("mana_vortex"));

  static void registerAM(IEventBus eventBus) {
    AM_ENTITY_TYPES.register(eventBus);
  }
}