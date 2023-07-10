package net.cheto97.rpgcraftmod.util.levelConfig.utils;

import com.google.gson.JsonObject;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.event.ModEvents;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.ToolLeveling.PacketHandler;
import net.cheto97.rpgcraftmod.networking.ToolLeveling.packets.SyncToolLevelingConfig;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;

public final class ConfigSyncing {

    public static void syncAllConfigsToOneClient(ServerPlayer player) {
        for (ConfigIdentifier config : ConfigIdentifier.values()) {
            String identifier = config.withModID();
            JsonObject json = config.serialize(new JsonObject());
            RpgcraftMod.LOGGER.info("Sending config to client: '{}'", config.withModID());
            ModMessages.sendToPlayer(new SyncToolLevelingConfig(identifier,json), player);
        }
    }

    public static boolean deserializeConfig(String identifier, JsonObject json) {
        for (ConfigIdentifier config : ConfigIdentifier.values()) {
            if (config.withModID().equals(identifier)) {
                config.deserialize(json);
                return true;
            }
        }
        return false;
    }

}
