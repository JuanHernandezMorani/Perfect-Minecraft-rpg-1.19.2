package net.cheto97.rpgcraftmod.util.levelConfig.utils;

import com.google.gson.JsonObject;

import java.util.function.Consumer;
import java.util.function.Function;

public final class Config {
    private final Runnable reset;
    private final Function<JsonObject, JsonObject> serializer;
    private final Consumer<JsonObject> deserializer;

    public Config(String fileName, Runnable reset, Function<JsonObject, JsonObject> serializer, Consumer<JsonObject> deserializer) {
        this.reset = reset;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    public void setToDefault() {
        this.reset.run();
    }

    public JsonObject serialize(JsonObject json) {
        return this.serializer.apply(json);
    }

    public void deserialize(JsonObject json) {
        this.deserializer.accept(json);
    }
}