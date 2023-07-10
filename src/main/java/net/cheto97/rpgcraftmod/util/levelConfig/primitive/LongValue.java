package net.cheto97.rpgcraftmod.util.levelConfig.primitive;

import com.google.gson.JsonElement;

public class LongValue extends NumberValue<Long> {

    public LongValue(String name, Long defaultValue, Long minValue, Long maxValue) {
        super(name, defaultValue, minValue, maxValue);
    }

    @Override
    Long getAsType(JsonElement jsonElement) {
        return jsonElement.getAsLong();
    }

}
