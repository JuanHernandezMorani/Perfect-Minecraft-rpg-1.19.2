package net.cheto97.rpgcraftmod.util.levelConfig;

import com.google.gson.JsonObject;
import net.cheto97.rpgcraftmod.util.levelConfig.primitive.BooleanValue;

public final class CommandConfig {

    private static final String SUPERENCHANT = "superenchant";
    public static final BooleanValue allowWrongEnchantments = new BooleanValue("allow_wrong_enchantments", true);
    public static final BooleanValue allowIncompatibleEnchantments = new BooleanValue("allow_incompatible_enchantments", true);

    private CommandConfig() {}

    public static void setToDefault() {
        allowWrongEnchantments.setToDefault();
        allowIncompatibleEnchantments.setToDefault();
    }

    public static JsonObject serialize(JsonObject json) {
        JsonObject superenchant = new JsonObject();
        allowWrongEnchantments.serialize(superenchant);
        allowIncompatibleEnchantments.serialize(superenchant);
        json.add(SUPERENCHANT, superenchant);
        return json;
    }

    public static void deserialize(JsonObject json) {
        if (json.has(SUPERENCHANT)) {
            JsonObject superenchant = json.get(SUPERENCHANT).getAsJsonObject();
            allowWrongEnchantments.deserialize(superenchant);
            allowIncompatibleEnchantments.deserialize(superenchant);
        }
    }

}
