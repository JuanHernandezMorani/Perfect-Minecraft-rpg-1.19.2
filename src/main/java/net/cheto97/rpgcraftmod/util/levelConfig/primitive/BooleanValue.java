package net.cheto97.rpgcraftmod.util.levelConfig.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.util.levelConfig.utils.AbstractConfigValue;

public final class BooleanValue extends AbstractConfigValue<Boolean> {

    private boolean value;
    private final boolean defaultValue;

    public BooleanValue(String identifier, boolean defaultValue) {
        super(identifier);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    @Override
    public void setToDefault() {
        value = defaultValue;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void serialize(JsonObject jsonObject) {
        jsonObject.addProperty(getIdentifier(), getValue());
    }

    @Override
    public void deserialize(JsonObject jsonObject) {
        try {
            JsonElement element = jsonObject.get(getIdentifier());
            if (element != null) {
                value = element.getAsBoolean();
            }
        } catch (Exception e) {
            value = defaultValue;
            RpgcraftMod.LOGGER.warn("Error while loading the config value " + getIdentifier() + ", using defaultvalue instead");
        }
    }

}
