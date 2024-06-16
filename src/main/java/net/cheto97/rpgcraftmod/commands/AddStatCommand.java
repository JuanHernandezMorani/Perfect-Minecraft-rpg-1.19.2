package net.cheto97.rpgcraftmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerStatSyncPacket;
import net.cheto97.rpgcraftmod.networking.packet.S2C.PlayerSyncPacket;
import net.cheto97.rpgcraftmod.providers.*;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Collection;


public final class AddStatCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION;

    static {
        FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.literal("Nothing changes, player reach stat cap or amount is out of range."));
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext c){
        dispatcher.register(Commands.literal("addstat")
                .requires((player) -> player.hasPermission(3))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.argument("stat", IntegerArgumentType.integer(1, 11))
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1, 5000000))
                                        .then(Commands.argument("should_show", IntegerArgumentType.integer(0,1))
                                                .executes((context) -> stats(context.getSource(),EntityArgument.getEntities(context,"targets"),IntegerArgumentType.getInteger(context,"stat"),IntegerArgumentType.getInteger(context,"amount"),IntegerArgumentType.getInteger(context,"should_show")))
                                        )
                                )
                        )
                )
        );
    }
    private static void sendData(String stat, int amount){
        ModMessages.sendToServer(new PlayerStatSyncPacket(stat,amount));
    }
    private static int stats(CommandSourceStack source, Collection<? extends Entity> targets, int stat,int amount,int show)

        throws CommandSyntaxException {
        Component name = targets != null ? targets.iterator().next().getDisplayName() : Component.literal("player");
        int i = 0;

        assert targets != null;
        for(Entity entity : targets){
            if(entity instanceof ServerPlayer player){
                switch (stat){
                    case 1 -> sendData("life",amount);
                    case 2 -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(data -> data.add(amount));
                    case 3 -> sendData("mana",amount);
                    case 4 -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(data -> data.add(amount));
                    case 5 -> sendData("dexterity",amount);
                    case 6 -> sendData("intelligence",amount);
                    case 7 -> sendData("strength",amount);
                    case 8 -> sendData("command",amount);
                    case 9 -> sendData("defense",amount);
                    case 10 -> sendData("magicdefense",amount);
                    case 11 -> sendData("luck",amount);
                    case 12 -> sendData("agility",amount);
                }
                ModMessages.sendToPlayer(new PlayerSyncPacket(player),player);
                i++;
            }
        }

            if(i == 0){
                throw FAILED_EXCEPTION.create();
            }
            else{
                if(show == 1) source.sendSuccess(Component.literal(amount == 1 ? amount + " resets added to " + name.getString() + ".": name.getString() + " do reset."), true);
                return i;
            }
        }


}
