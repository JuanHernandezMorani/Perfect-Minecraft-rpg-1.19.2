package net.cheto97.rpgcraftmod.networking.ToolLeveling.packets;

import java.util.function.Supplier;

import net.cheto97.rpgcraftmod.screen.ItemValueScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ClientOpenItemValueScreenPacket {

	public static void handle(OpenItemValueScreenPacket msg, Supplier<NetworkEvent.Context> context) {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			Minecraft.getInstance().setScreen(new ItemValueScreen());
		});
	}

}
