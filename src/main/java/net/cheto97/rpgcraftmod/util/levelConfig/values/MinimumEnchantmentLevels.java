package net.cheto97.rpgcraftmod.util.levelConfig.values;

import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class MinimumEnchantmentLevels extends RegistryMapConfig<Enchantment, Double> {

    private static final Type TYPE = new TypeToken<Map<String, Double>>() {}.getType();

    public MinimumEnchantmentLevels(String identifier) {
        super(identifier, ForgeRegistries.ENCHANTMENTS, getDefaults());
    }

    @Override
    protected Type getType() {
        return TYPE;
    }

    @Override
    protected boolean isValueValid(Double value) {
        return value >= 0;
    }

    @Override
    protected boolean isKeyValid(Enchantment key, ResourceLocation identifier) {
        return key != null;
    }

    private static Map<Enchantment, Double> getDefaults() {
        return new HashMap<>();
    }

}
