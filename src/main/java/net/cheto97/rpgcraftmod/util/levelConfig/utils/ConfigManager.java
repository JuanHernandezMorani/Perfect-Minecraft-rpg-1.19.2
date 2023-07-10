package net.cheto97.rpgcraftmod.util.levelConfig.utils;


public final class ConfigManager {

    private ConfigManager() {}

    public static void setup() {
        for (ConfigIdentifier config : ConfigIdentifier.values()) {
            config.setToDefault();
        }
    }

    public static void resetOneConfig(ConfigIdentifier config) {
        config.setToDefault();
    }
}
