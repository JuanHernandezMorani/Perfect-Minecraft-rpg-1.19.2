package net.cheto97.rpgcraftmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.cheto97.rpgcraftmod.event.ModEvents;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.S2C.PlayerSyncPacket;
import net.cheto97.rpgcraftmod.providers.ResetProvider;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Collection;


public final class AddResetCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION;

    static {
        FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.literal("Nothing changes, player reach max reset or reset value is out of range."));
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext c){
        dispatcher.register(Commands.literal("addresets")
                .requires((player) -> player.hasPermission(3))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 5000000))
                                .then(Commands.argument("should_show", IntegerArgumentType.integer(0,1))
                                        .executes((context) -> resets(context.getSource(),EntityArgument.getEntities(context,"targets"),IntegerArgumentType.getInteger(context,"amount"),IntegerArgumentType.getInteger(context,"should_show")))
                                )
                        )
                )
        );
    }

    private static int resets(CommandSourceStack source, Collection<? extends Entity> targets, int amount,int show)
        throws CommandSyntaxException {
        Component name = targets != null ? targets.iterator().next().getDisplayName() : Component.literal("player");
        int i = 0;

        assert targets != null;
        for(Entity entity : targets){
            if(entity instanceof ServerPlayer player){
                player.getCapability(ResetProvider.ENTITY_RESET).ifPresent(reset -> reset.setReset(reset.get() + amount));
                ModMessages.sendToPlayer(new PlayerSyncPacket(player),player);
                i++;
            }
        }

            if(i == 0){
                throw FAILED_EXCEPTION.create();
            }
            else{
                if(show == 1) source.sendSuccess(Component.literal(amount > 1 ? amount + " resets added to " + name.getString() + ".": name.getString() + " do reset."), true);
                return i;
            }
        }


}
