package net.cheto97.rpgcraftmod.util.ToolLevelingUp;

import com.mojang.brigadier.CommandDispatcher;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.commands.*;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID, bus = Bus.FORGE)
public final class ModCommands {

	@SubscribeEvent
	public static void register(final RegisterCommandsEvent event) {
		final CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
		final CommandBuildContext context = event.getBuildContext();
		SuperEnchantCommand.register(dispatcher, context);
		ToolLevelingCommand.register(dispatcher);
		AddLevelCommand.register(dispatcher,context);
		AddStatCommand.register(dispatcher,context);
		AddResetCommand.register(dispatcher,context);
		AddStatsPointCommand.register(dispatcher,context);
	}

}
