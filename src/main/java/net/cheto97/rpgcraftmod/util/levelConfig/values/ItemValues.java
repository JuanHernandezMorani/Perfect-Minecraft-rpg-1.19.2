package net.cheto97.rpgcraftmod.util.levelConfig.values;

import com.google.gson.reflect.TypeToken;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class ItemValues extends RegistryMapConfig<Item, Long> {

    private static final Type TYPE = new TypeToken<Map<String, Long>>() {}.getType();

    public ItemValues(String identifier) {
        super(identifier, ForgeRegistries.ITEMS, getDefaultItemValues());
    }

    @Override
    protected boolean isKeyValid(Item item, ResourceLocation identifier) {
        if (item == null || item == Items.AIR) {
            RpgcraftMod.LOGGER.warn("Item {} not found in registry", identifier);
            return false;
        }
        ItemStack stack = new ItemStack(item);
        if (item.isDamageable(stack)) {
            RpgcraftMod.LOGGER.warn("Item {} is damageable, it not a valid item to use in the toolleveling table", identifier);
            return false;
        }
        if (item.isEnchantable(stack)) {
            RpgcraftMod.LOGGER.warn("Item {} is enchantable, it not a valid item to use in the toolleveling table", identifier);
            return false;
        }
        return true;
    }

    @Override
    protected boolean isValueValid(Long value) {
        return value >= 0L;
    }

    @Override
    protected Type getType() {
        return TYPE;
    }

    public static Map<Item, Long> getDefaultItemValues() {
        Map<Item, Long> values = new HashMap<>();

        // Ores
        values.put(Items.COAL, 8L);
        values.put(Items.COAL_ORE, 30L);
        values.put(Items.DEEPSLATE_COAL_ORE, 30L);
        values.put(Items.COAL_BLOCK, 73L);

        values.put(Items.COPPER_ORE, 10L);
        values.put(Items.DEEPSLATE_COPPER_ORE, 10L);
        values.put(Items.RAW_COPPER, 11L);
        values.put(Items.COPPER_INGOT, 14L);
        values.put(Items.COPPER_BLOCK, 126L);
        values.put(Items.RAW_COPPER_BLOCK, 99L);

        values.put(Items.RAW_IRON, 13L);
        values.put(Items.IRON_INGOT, 15L);
        values.put(Items.IRON_ORE, 12L);
        values.put(Items.DEEPSLATE_IRON_ORE, 12L);
        values.put(Items.RAW_IRON_BLOCK, 117L);
        values.put(Items.IRON_BLOCK, 135L);

        values.put(Items.GOLD_INGOT, 40L);
        values.put(Items.RAW_GOLD, 35L);
        values.put(Items.GOLD_ORE, 30L);
        values.put(Items.DEEPSLATE_GOLD_ORE, 30L);
        values.put(Items.RAW_GOLD_BLOCK, 315L);
        values.put(Items.GOLD_BLOCK, 360L);

        values.put(Items.DIAMOND, 160L);
        values.put(Items.DIAMOND_ORE, 160L);
        values.put(Items.DEEPSLATE_DIAMOND_ORE, 160L);
        values.put(Items.DIAMOND_BLOCK, 1450L);
        values.put(Items.NETHERITE_INGOT, 200L);
        values.put(Items.NETHERITE_SCRAP, 50L);
        values.put(Items.ANCIENT_DEBRIS, 50L);
        values.put(Items.NETHERITE_BLOCK, 1800L);
        values.put(Items.LAPIS_LAZULI, 8L);
        values.put(Items.LAPIS_ORE, 120L);
        values.put(Items.DEEPSLATE_LAPIS_ORE, 120L);
        values.put(Items.LAPIS_BLOCK, 70L);
        values.put(Items.EMERALD, 100L);
        values.put(Items.EMERALD_ORE, 800L);
        values.put(Items.DEEPSLATE_EMERALD_ORE, 800L);
        values.put(Items.EMERALD_BLOCK, 900L);
        values.put(Items.QUARTZ, 10L);
        values.put(Items.NETHER_QUARTZ_ORE, 40L);
        values.put(Items.QUARTZ_BLOCK, 40L);
        values.put(Items.REDSTONE, 4L);
        values.put(Items.REDSTONE_ORE, 60L);
        values.put(Items.DEEPSLATE_REDSTONE_ORE, 600L);
        values.put(Items.REDSTONE_BLOCK, 360L);
        values.put(Items.GLOWSTONE_DUST, 40L);
        values.put(Items.GLOWSTONE, 150L);

        // other
        values.put(Items.AMETHYST_BLOCK, 110L);
        values.put(Items.AMETHYST_SHARD, 170L);

        // Food
        values.put(Items.GOLDEN_APPLE, 400L);
        values.put(Items.GOLDEN_CARROT, 100L);
        values.put(Items.GLISTERING_MELON_SLICE, 100L);
        values.put(Items.ENCHANTED_GOLDEN_APPLE, 25000L);

        // Drops
        values.put(Items.SLIME_BALL, 25L);
        values.put(Items.SLIME_BLOCK, 250L);
        values.put(Items.ENDER_PEARL, 540L);
        values.put(Items.BLAZE_ROD, 330L);
        values.put(Items.ENDER_EYE, 5000L);
        values.put(Items.BLAZE_POWDER, 15L);
        values.put(Items.MAGMA_CREAM, 50L);
        values.put(Items.GHAST_TEAR, 2000L);
        values.put(Items.NETHER_STAR, 250000L);
        values.put(Items.SHULKER_SHELL, 20000L);
        values.put(Items.END_CRYSTAL, 3000L);
        values.put(Items.EXPERIENCE_BOTTLE, 10000L);
        values.put(Items.DRAGON_EGG, 20000L);
        values.put(Items.DRAGON_HEAD, 200000L);

        // Decorative
        values.put(Items.ENDER_CHEST, 140L);
        values.put(Items.BEACON, 2500L);

        //Custom
        values.put(ModBlocks.bloque_mana.get().asItem(), 1000000L);
        values.put(ModItems.zafiro.get(), 4500L);
        values.put(ModItems.BOSS_AURA.get(), 360000L);
        values.put(ModItems.ELITE_AURA.get(), 26000L);
        values.put(ModItems.LEGENDARY_AURA.get(), 96000L);
        values.put(ModItems.COMMON_AURA.get(), 6000L);
        values.put(ModItems.DEMON_AURA.get(), 86000L);
        values.put(ModItems.SEMI_BOSS_AURA.get(), 140000L);
        values.put(ModItems.UNIQUE_AURA.get(), 43000L);
        values.put(ModItems.HERO_AURA.get(), 32000L);
        values.put(ModItems.BRUTAL_AURA.get(), 46000L);
        values.put(ModItems.MYTHICAL_AURA.get(), 56000L);
        values.put(ModItems.CHAMPION_AURA.get(), 16000L);
        return values;
    }

}
