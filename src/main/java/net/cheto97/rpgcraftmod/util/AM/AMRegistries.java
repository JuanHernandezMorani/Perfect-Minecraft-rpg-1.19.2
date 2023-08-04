package net.cheto97.rpgcraftmod.util.AM;

import com.mojang.serialization.Codec;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMRegistries {
 DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RpgcraftMod.MOD_ID);
 DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, RpgcraftMod.MOD_ID);
 DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RpgcraftMod.MOD_ID);
 DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RpgcraftMod.MOD_ID);
 DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, RpgcraftMod.MOD_ID);
 DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RpgcraftMod.MOD_ID);
 DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, RpgcraftMod.MOD_ID);
 DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, RpgcraftMod.MOD_ID);
 DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RpgcraftMod.MOD_ID);
 DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RpgcraftMod.MOD_ID);
 DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, RpgcraftMod.MOD_ID);
 DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, RpgcraftMod.MOD_ID);
 DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RpgcraftMod.MOD_ID);
 DeferredRegister<StatType<?>> STAT_TYPES = DeferredRegister.create(ForgeRegistries.STAT_TYPES, RpgcraftMod.MOD_ID);
 DeferredRegister<ResourceLocation> CUSTOM_STATS = DeferredRegister.create(Registry.CUSTOM_STAT_REGISTRY, RpgcraftMod.MOD_ID);
 DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, RpgcraftMod.MOD_ID);
 DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, RpgcraftMod.MOD_ID);
 DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, RpgcraftMod.MOD_ID);
 DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, RpgcraftMod.MOD_ID);


 @Internal
 static void init(IEventBus bus) {
 BLOCKS.register(bus);
 FLUIDS.register(bus);
 ITEMS.register(bus);
 MOB_EFFECTS.register(bus);
 ATTRIBUTES.register(bus);
 SOUND_EVENTS.register(bus);
 POTIONS.register(bus);
 ENCHANTMENTS.register(bus);
 ENTITY_TYPES.register(bus);
 BLOCK_ENTITY_TYPES.register(bus);
 PARTICLE_TYPES.register(bus);
 MENU_TYPES.register(bus);
 RECIPE_SERIALIZERS.register(bus);
 STAT_TYPES.register(bus);
 CUSTOM_STATS.register(bus);
 FLUID_TYPES.register(bus);
 ENTITY_DATA_SERIALIZERS.register(bus);
 GLOBAL_LOOT_MODIFIERS.register(bus);
 FEATURES.register(bus);
 }
}