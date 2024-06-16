package net.cheto97.rpgcraftmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.cheto97.rpgcraftmod.event.ModEvents;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerStatSyncPacket;
import net.cheto97.rpgcraftmod.networking.packet.S2C.PlayerSyncPacket;
import net.cheto97.rpgcraftmod.providers.LifeRegenerationProvider;
import net.cheto97.rpgcraftmod.providers.ManaRegenerationProvider;
import net.cheto97.rpgcraftmod.providers.ResetProvider;
import net.cheto97.rpgcraftmod.providers.StatPointProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Collection;

public final class RPGcraftCommandPlayerIncrease {
    private static final SimpleCommandExceptionType RESET_FAILED_EXCEPTION;
    private static final SimpleCommandExceptionType STATS_POINTS_FAILED_EXCEPTION;
    private static final SimpleCommandExceptionType STAT_FAILED_EXCEPTION;
    private static final SimpleCommandExceptionType LEVEL_FAILED_EXCEPTION;

    static {
        RESET_FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.literal("Nothing changes, player reach max reset or reset value is out of range."));
        STATS_POINTS_FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.literal("Nothing changes, player reach stats point cap or amount is out of range."));
        STAT_FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.literal("Nothing changes, player reach stat cap or amount is out of range."));
        LEVEL_FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.literal("Nothing changes, player reach max level or level is out of range."));
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("rpgAdd")
                .requires((player) -> player.hasPermission(3))
                .then(addLevels())
                .then(addStatsPoints())
                .then(addStats())
                .then(addResets())
        );
    }
    private static LiteralCommandNode<CommandSourceStack> addLevels() {
        return Commands.literal("levels")
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 5000000))
                                .then(Commands.argument("should_show", IntegerArgumentType.integer(0,1))
                                        .executes((context) -> levels(context.getSource(), EntityArgument.getEntities(context, "targets"), IntegerArgumentType.getInteger(context, "amount"), IntegerArgumentType.getInteger(context, "should_show")))
                                )
                        )
                ).build();
    }
    private static LiteralCommandNode<CommandSourceStack> addStatsPoints() {
        return Commands.literal("points")
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 5000000))
                                .then(Commands.argument("should_show", IntegerArgumentType.integer(0,1))
                                        .executes((context) -> points(context.getSource(), EntityArgument.getEntities(context, "targets"), IntegerArgumentType.getInteger(context, "amount"), IntegerArgumentType.getInteger(context, "should_show")))
                                )
                        )
                ).build();
    }
    private static LiteralCommandNode<CommandSourceStack> addStats() {
        return Commands.literal("stats")
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.argument("stat", IntegerArgumentType.integer(1, 12))
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1, 5000000))
                                        .then(Commands.argument("should_show", IntegerArgumentType.integer(0,1))
                                                .executes((context) -> stats(context.getSource(), EntityArgument.getEntities(context, "targets"), IntegerArgumentType.getInteger(context, "stat"), IntegerArgumentType.getInteger(context, "amount"), IntegerArgumentType.getInteger(context, "should_show")))
                                        )
                                )
                        )
                ).build();
    }
    private static LiteralCommandNode<CommandSourceStack> addResets() {
        return Commands.literal("resets")
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 5000000))
                                .then(Commands.argument("should_show", IntegerArgumentType.integer(0,1))
                                        .executes((context) -> resets(context.getSource(), EntityArgument.getEntities(context, "targets"), IntegerArgumentType.getInteger(context, "amount"), IntegerArgumentType.getInteger(context, "should_show")))
                                )
                        )
                ).build();
    }
    private static int levels(CommandSourceStack source, Collection<? extends Entity> targets, int amount, int show)
            throws CommandSyntaxException {
        Component name = targets != null ? targets.iterator().next().getDisplayName() : Component.literal("player");
        int i = 0;

        assert targets != null;
        for(Entity entity : targets){
            if(entity instanceof ServerPlayer player){
                ModEvents.ForgeEvents.managePlayerLevel(player, amount,true);
                i++;
            }
        }

        if(i == 0){
            throw LEVEL_FAILED_EXCEPTION.create();
        }
        else{
            if(show == 1) source.sendSuccess(Component.literal(amount > 1 ? amount + " levels added to " + name.getString() : name.getString() + " level up"), true);
            return i;
        }
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
            throw RESET_FAILED_EXCEPTION.create();
        }
        else{
            if(show == 1) source.sendSuccess(Component.literal(amount > 1 ? amount + " resets added to " + name.getString() + ".": name.getString() + " do reset."), true);
            return i;
        }
    }

    private static String sendData(String stat, int amount){
        ModMessages.sendToServer(new PlayerStatSyncPacket(stat,amount));
        return stat;
    }
    private static int stats(CommandSourceStack source, Collection<? extends Entity> targets, int stat,int amount,int show)
            throws CommandSyntaxException {
        Component name = targets != null ? targets.iterator().next().getDisplayName() : Component.literal("player");
        int i = 0;
        String statAdded = "unknown";

        assert targets != null;
        for(Entity entity : targets){
            if(entity instanceof ServerPlayer player){
                switch (stat){
                    case 1 -> statAdded = sendData("life",amount);
                    case 2 -> {
                        player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(data -> data.add(amount));
                        statAdded = "life regeneration";
                    }
                    case 3 -> statAdded = sendData("mana",amount);
                    case 4 -> {
                        player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(data -> data.add(amount));
                        statAdded = "mana regeneration";
                    }
                    case 5 -> statAdded = sendData("dexterity",amount);
                    case 6 -> statAdded = sendData("intelligence",amount);
                    case 7 -> statAdded = sendData("strength",amount);
                    case 8 -> statAdded = sendData("command",amount);
                    case 9 -> statAdded = sendData("defense",amount);
                    case 10 -> statAdded = sendData("magicdefense",amount);
                    case 11 -> statAdded = sendData("luck",amount);
                    case 12 -> statAdded = sendData("agility",amount);
                }
                ModMessages.sendToPlayer(new PlayerSyncPacket(player),player);
                i++;
            }
        }

        if(i == 0){
            throw STAT_FAILED_EXCEPTION.create();
        }
        else{
            if(show == 1) source.sendSuccess(Component.literal(amount > 1 ? amount + " points added to " + statAdded + " stat to " + name.getString() + ".": name.getString() + statAdded + " increase in 1."), true);
            return i;
        }
    }

    private static int points(CommandSourceStack source, Collection<? extends Entity> targets, int amount,int show)
            throws CommandSyntaxException {
        Component name = targets != null ? targets.iterator().next().getDisplayName() : Component.literal("player");
        int i = 0;

        assert targets != null;
        for(Entity entity : targets){
            if(entity instanceof ServerPlayer player){
                player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stat -> stat.add(amount));
                ModMessages.sendToPlayer(new PlayerSyncPacket(player),player);
                i++;
            }
        }

        if(i == 0){
            throw STATS_POINTS_FAILED_EXCEPTION.create();
        }
        else{
            if(show == 1) source.sendSuccess(Component.literal(amount > 1 ? amount + " stats points added to " + name.getString() : name.getString() + " get 1 stats point"), true);
            return i;
        }
    }
}
