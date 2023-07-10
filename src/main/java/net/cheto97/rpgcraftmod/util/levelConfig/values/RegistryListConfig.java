package net.cheto97.rpgcraftmod.util.levelConfig.values;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.util.levelConfig.utils.AbstractConfigValue;
import net.cheto97.rpgcraftmod.util.levelConfig.utils.ConfigUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.IForgeRegistry;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RegistryListConfig<T> extends AbstractConfigValue<ImmutableList<T>> {

    private final ImmutableList<T> defaultValues;
    private ImmutableList<T> values;
    private List<String> rawValues = new ArrayList<>();
    private final Type type = new TypeToken<List<String>>() {}.getType();
    private final Gson GSON = new Gson();
    private final IForgeRegistry<T> registry;

    public RegistryListConfig(String identifier, IForgeRegistry<T> registry, List<T> defaultValues) {
        super(identifier);
        if (registry == null) {
            throw new NullPointerException("registry of the config value can't be null");
        }
        if (defaultValues == null) {
            throw new NullPointerException("defaultValues of the config value can't be null");
        }
        this.registry = registry;
        this.defaultValues = ImmutableList.copyOf(defaultValues);

        for (T arg : defaultValues) {
            rawValues.add(registry.getKey(arg).toString());
        }
        values = ImmutableList.copyOf(defaultValues);
    }

    @Override
    public void setToDefault() {
        values = ImmutableList.copyOf(defaultValues);
        this.rawValues.clear();
        this.defaultValues.forEach(x -> this.rawValues.add(this.registry.getKey(x).toString()));
    }

    @Override
    public ImmutableList<T> getValue() {
        return values;
    }

    @Override
    public void serialize(JsonObject jsonObject) {
        JsonElement jsonElement = GSON.toJsonTree(rawValues, type);
        jsonObject.add(getIdentifier(), jsonElement);
    }

    @Override
    public void deserialize(JsonObject jsonObject) {
        try {
            JsonElement jsonElement = jsonObject.get(getIdentifier());
            if (jsonElement == null) {
                this.setToDefault();
                RpgcraftMod.LOGGER.warn("Error while loading the config value " + getIdentifier() + ", using default values instead");
                return;
            }
            rawValues = GSON.fromJson(jsonElement, type);
            if (rawValues == null) {
                this.setToDefault();
                RpgcraftMod.LOGGER.warn("Error while loading the config value " + getIdentifier() + ", using default values instead");
                return;
            }

            Iterator<String> iterator = rawValues.iterator();
            List<T> parsedValues = new ArrayList<>();
            while (iterator.hasNext()) {
                String nextValue = iterator.next();

                //if nextValue is wildcard, add all entries of the registry
                if (nextValue.contains("*")) {
                    String modId = ConfigUtils.getModIdFromWildcard(nextValue);
                    if (modId != null) {
                        RpgcraftMod.LOGGER.info("Found wildcard with modid: '{}' in '{}'", modId, getIdentifier());
                        parsedValues.addAll(ConfigUtils.getAllFromWildcard(modId, registry));
                        continue;
                    }
                    RpgcraftMod.LOGGER.warn("Found wildcard with invalid modid '{}' in '{}'", nextValue, getIdentifier());
                }

                //if nextValue is tag, add all entries of the tag
                if (nextValue.startsWith("#")) {
                    TagKey<T> tagKey = ConfigUtils.getTagKeyFromTag(nextValue, registry);
                    if (tagKey != null) {
                        RpgcraftMod.LOGGER.info("Found tag '{}' in '{}'", tagKey, getIdentifier());
                        parsedValues.addAll(ConfigUtils.getAllFromTag(tagKey, registry));
                        continue;
                    }
                    RpgcraftMod.LOGGER.warn("Found tag with invalid name '{}' in '{}'", nextValue, getIdentifier());
                }

                //if nextValue is single valid entry, add it
                ResourceLocation loc = ResourceLocation.tryParse(nextValue);
                if (loc != null && registry.containsKey(loc)) {
                    T entry = registry.getValue(loc);
                    if (entry != null) {
                        parsedValues.add(entry);
                        continue;
                    }
                }

                //else nextValue is invalid, remove it
                RpgcraftMod.LOGGER.warn("Found invalid entry '{}' in '{}'", nextValue, getIdentifier());
                iterator.remove();
            }

            values = ImmutableList.copyOf(parsedValues);
        } catch (Exception e) {
            values = ImmutableList.copyOf(defaultValues);
            RpgcraftMod.LOGGER.error("Error while loading the config value " + getIdentifier() + ", using default values instead");
        }
    }

}
