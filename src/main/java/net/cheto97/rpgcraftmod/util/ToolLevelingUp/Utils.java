package net.cheto97.rpgcraftmod.util.ToolLevelingUp;

import com.google.common.collect.ImmutableMap;
import net.cheto97.rpgcraftmod.util.levelConfig.ItemValueConfig;
import net.cheto97.rpgcraftmod.util.levelConfig.ToolLevelingConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public final class Utils {

    public static final Map<Enchantment, Integer> BREAKING_ENCHANTMENTS = ImmutableMap.<Enchantment, Integer>builder()
            .put(Enchantments.FISHING_LUCK, 84).put(Enchantments.QUICK_CHARGE, 5).put(Enchantments.THORNS, 7)
            .put(Enchantments.FISHING_SPEED, 5).build();

    public static long getEnchantmentUpgradeCost(Enchantment enchantment, int level) {
        double globalModifier = ToolLevelingConfig.globalUpgradeCostMultiplier.getValue();
        double specificModifier = ToolLevelingConfig.enchantmentUpgradeCostModifier.getValue().getOrDefault(enchantment, 1.0D);
        long minCost = ToolLevelingConfig.minimumUpgradeCost.getValue();
        return (long) Math.max(minCost, ((0.87 * level * level) + (300 * level)) * specificModifier * globalModifier);
    }

    public static long getItemWorth(Item item) {
        if (ItemValueConfig.itemValues.getValue().containsKey(item)) {
            return ItemValueConfig.itemValues.getValue().get(item);
        }
        return ItemValueConfig.defaultItemWorth.getValue();
    }

    public static long getItemWorth(ItemStack stack) {
        return Utils.getItemWorth(stack.getItem());
    }

    public static long getStackWorth(ItemStack stack) {
        return stack.getCount() * Utils.getItemWorth(stack);
    }

    public static boolean isEnchantmentAtCap(Enchantment enchantment, int level) {
        double globalEnchantmentCap = ToolLevelingConfig.globalEnchantmentCap.getValue();
        // global cap is set, use the lowest of the two
        if (globalEnchantmentCap > 0) {
            if (ToolLevelingConfig.enchantmentCaps.getValue().containsKey(enchantment)) {
                short enchantmentCap = ToolLevelingConfig.enchantmentCaps.getValue().get(enchantment);
                if (enchantmentCap < globalEnchantmentCap) {
                    return level >= enchantmentCap;
                }
            }
            return level >= globalEnchantmentCap;
        }
        if (ToolLevelingConfig.enchantmentCaps.getValue().containsKey(enchantment)) {
            return (level >= ToolLevelingConfig.enchantmentCaps.getValue().get(enchantment));
        }
        return false;
    }
    public static boolean isEnchantmentOverMinimum(Enchantment enchantment, int level) {
        double globalEnchantmentMin = ToolLevelingConfig.globalMinimumEnchantmentLevel.getValue();
        if (globalEnchantmentMin > 0) {
            if (ToolLevelingConfig.minimumEnchantmentLevels.getValue().containsKey(enchantment)) {
                double enchantmentMin = ToolLevelingConfig.minimumEnchantmentLevels.getValue().get(enchantment);
                if (enchantmentMin > globalEnchantmentMin) {
                    return level >= enchantmentMin;
                }
            }
            return level >= globalEnchantmentMin;
        }
        if (ToolLevelingConfig.minimumEnchantmentLevels.getValue().containsKey(enchantment)) {
            return level >= ToolLevelingConfig.minimumEnchantmentLevels.getValue().get(enchantment);
        }
        return level >= globalEnchantmentMin;
    }

    public static boolean willEnchantmentBreak(Enchantment enchantment, int level) {
        if (BREAKING_ENCHANTMENTS.containsKey(enchantment)) {
            return (level >= BREAKING_ENCHANTMENTS.get(enchantment));
        }
        return false;
    }

    public static boolean freeCreativeUpgrades(Player player) {
        return ToolLevelingConfig.freeUpgradesForCreativePlayers.getValue() && player.isCreative();
    }

}
