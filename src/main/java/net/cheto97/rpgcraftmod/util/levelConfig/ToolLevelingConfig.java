package net.cheto97.rpgcraftmod.util.levelConfig;

import com.google.gson.JsonObject;
import net.cheto97.rpgcraftmod.util.levelConfig.primitive.BooleanValue;
import net.cheto97.rpgcraftmod.util.levelConfig.primitive.DoubleValue;
import net.cheto97.rpgcraftmod.util.levelConfig.primitive.LongValue;
import net.cheto97.rpgcraftmod.util.levelConfig.values.EnchantmentCaps;
import net.cheto97.rpgcraftmod.util.levelConfig.values.EnchantmentModifier;
import net.cheto97.rpgcraftmod.util.levelConfig.values.MinimumEnchantmentLevels;
import net.cheto97.rpgcraftmod.util.levelConfig.values.RegistryListConfig;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public final class ToolLevelingConfig {

    // General options
    private static final String GENERAL_OPTIONS = "general_options";
    public static final LongValue minimumUpgradeCost = new LongValue("minimum_upgrade_cost", 100L, 1L, Long.MAX_VALUE);
    public static final BooleanValue allowLevelingUselessEnchantments = new BooleanValue("allow_leveling_of_useless_enchantments", true);
    public static final BooleanValue allowLevelingBreakingEnchantments = new BooleanValue("allow_leveling_of_breaking_enchantments", false);
    public static final BooleanValue freeUpgradesForCreativePlayers = new BooleanValue("free_upgrades_for_creative_players", true);

    // Enchantment options
    private static final String ENCHANTMENT_OPTIONS = "enchantment_options";
    public static final RegistryListConfig<Enchantment> whitelist = new RegistryListConfig<>("enchantment_whitelist", ForgeRegistries.ENCHANTMENTS, new ArrayList<>());
    public static final RegistryListConfig<Enchantment> blacklist = new RegistryListConfig<>("enchantment_blacklist", ForgeRegistries.ENCHANTMENTS, getDefaultEnchantmentBlacklist());
    public static final DoubleValue globalEnchantmentCap = new DoubleValue("global_enchantment_cap", 32767D, 0D, 32767D);
    public static final EnchantmentCaps enchantmentCaps = new EnchantmentCaps("enchantment_caps");
    public static final DoubleValue globalUpgradeCostMultiplier = new DoubleValue("global_upgrade_cost_multiplier", 1.0D, 0.0D, 100.0D);
    public static final EnchantmentModifier enchantmentUpgradeCostModifier = new EnchantmentModifier("enchantment_upgrade_cost_modifier");
    public static final DoubleValue globalMinimumEnchantmentLevel = new DoubleValue("global_minimum_enchantment_level", 0D, 0D, 32767D);
    public static final MinimumEnchantmentLevels minimumEnchantmentLevels = new MinimumEnchantmentLevels("minimum_enchantment_levels");

    private ToolLevelingConfig() {}

    public static void setToDefault() {
        minimumUpgradeCost.setToDefault();
        allowLevelingUselessEnchantments.setToDefault();
        allowLevelingBreakingEnchantments.setToDefault();
        freeUpgradesForCreativePlayers.setToDefault();

        whitelist.setToDefault();
        blacklist.setToDefault();
        globalEnchantmentCap.setToDefault();
        enchantmentCaps.setToDefault();
        globalUpgradeCostMultiplier.setToDefault();
        enchantmentUpgradeCostModifier.setToDefault();
        globalMinimumEnchantmentLevel.setToDefault();
        minimumEnchantmentLevels.setToDefault();
    }

    public static JsonObject serialize(JsonObject json) {
        //serialize general options
        JsonObject general = new JsonObject();
        minimumUpgradeCost.serialize(general);
        allowLevelingUselessEnchantments.serialize(general);
        allowLevelingBreakingEnchantments.serialize(general);
        freeUpgradesForCreativePlayers.serialize(general);
        json.add(GENERAL_OPTIONS, general);

        //serialize enchantment options
        JsonObject enchantment = new JsonObject();
        whitelist.serialize(enchantment);
        blacklist.serialize(enchantment);
        globalEnchantmentCap.serialize(enchantment);
        enchantmentCaps.serialize(enchantment);
        globalUpgradeCostMultiplier.serialize(enchantment);
        enchantmentUpgradeCostModifier.serialize(enchantment);
        globalMinimumEnchantmentLevel.serialize(enchantment);
        minimumEnchantmentLevels.serialize(enchantment);
        json.add(ENCHANTMENT_OPTIONS, enchantment);
        return json;
    }

    public static void deserialize(JsonObject json) {
        if (json.has(GENERAL_OPTIONS)) {
            JsonObject general = json.getAsJsonObject(GENERAL_OPTIONS);
            minimumUpgradeCost.deserialize(general);
            allowLevelingUselessEnchantments.deserialize(general);
            allowLevelingBreakingEnchantments.deserialize(general);
            freeUpgradesForCreativePlayers.deserialize(general);
        }

        if (json.has(ENCHANTMENT_OPTIONS)) {
            JsonObject enchantment = json.getAsJsonObject(ENCHANTMENT_OPTIONS);
            whitelist.deserialize(enchantment);
            blacklist.deserialize(enchantment);
            globalEnchantmentCap.deserialize(enchantment);
            enchantmentCaps.deserialize(enchantment);
            globalUpgradeCostMultiplier.deserialize(enchantment);
            enchantmentUpgradeCostModifier.deserialize(enchantment);
            globalMinimumEnchantmentLevel.deserialize(enchantment);
            minimumEnchantmentLevels.deserialize(enchantment);
        }
    }

    private static List<Enchantment> getDefaultEnchantmentBlacklist() {
        return new ArrayList<>();
    }

}
