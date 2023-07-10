package net.cheto97.rpgcraftmod.networking.ToolLeveling.packets;

import java.util.function.Supplier;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.util.levelConfig.utils.ConfigSyncing;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public final class ClientSyncToolLevelingConfig {

	public static void handle(SyncToolLevelingConfig msg, Supplier<NetworkEvent.Context> context) {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			boolean success = ConfigSyncing.deserializeConfig(msg.identifier, msg.json);
			if (!success) {
				RpgcraftMod.LOGGER.error("Config {} could not be loaded", msg.identifier);
				throw new RuntimeException("Config " + msg.identifier + " could not be loaded");
			} else {
				RpgcraftMod.LOGGER.info("Config {} received and loaded.", msg.identifier);
			}
		});
	}

}
