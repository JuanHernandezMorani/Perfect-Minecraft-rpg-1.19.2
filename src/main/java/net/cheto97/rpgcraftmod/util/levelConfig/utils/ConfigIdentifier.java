package net.cheto97.rpgcraftmod.util.levelConfig.utils;

import com.google.gson.JsonObject;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.Names;
import net.cheto97.rpgcraftmod.util.levelConfig.CommandConfig;
import net.cheto97.rpgcraftmod.util.levelConfig.ItemValueConfig;
import net.cheto97.rpgcraftmod.util.levelConfig.ToolLevelingConfig;

import java.util.function.Consumer;
import java.util.function.Function;

public enum ConfigIdentifier {

    GENERAL("general", "tool_leveling_table.json", Names.URLS.CONFIG_INFO_GENERAL, ToolLevelingConfig::setToDefault, ToolLevelingConfig::serialize, ToolLevelingConfig::deserialize),
    ITEM_VALUES("item_values", "item_values.json", Names.URLS.CONFIG_INFO_ITEM_VALUES, ItemValueConfig::setToDefault, ItemValueConfig::serialize, ItemValueConfig::deserialize),
    COMMANDS("commands", "command_config.json", Names.URLS.CONFIG_INFO_COMMANDS, CommandConfig::setToDefault, CommandConfig::serialize, CommandConfig::deserialize);

    private final String infoUrl;
    private final Runnable resetter;
    private final Function<JsonObject, JsonObject> serializer;
    private final Consumer<JsonObject> deserializer;

    ConfigIdentifier(String name, String fileName, String url, Runnable resetter,
                     Function<JsonObject, JsonObject> serializer, Consumer<JsonObject> deserializer) {
        this.infoUrl = url;
        this.resetter = resetter;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    public String withModID() {
        return Names.MOD_ID;
    }

    public String getFileName() {
        return null;
    }

    public void setToDefault() {
        this.resetter.run();
    }

    public JsonObject serialize(JsonObject json) {
        return this.serializer.apply(json);
    }

    public void deserialize(JsonObject json) {
        this.deserializer.accept(json);
    }

    public String getInfoUrl() {
        return this.infoUrl;
    }

}
