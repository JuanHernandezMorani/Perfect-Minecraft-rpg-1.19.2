package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.modsystem.Customlevel;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlayerNoReqPacket {
    private static String stat;

    public PlayerNoReqPacket(String data){stat = data;}
    public PlayerNoReqPacket(FriendlyByteBuf buf){
        stat = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(stat);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->{
            ServerPlayer player = context.getSender();
            assert player != null;
            Player result = player.getLevel().getPlayerByUUID(player.getUUID());
            assert result != null;
            switch (stat){
                case "reset" -> {
                    int level = result.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(0);
                    result.sendSystemMessage(Component.literal("You don't have enough levels. You need "+(10000 - level)+" more levels to reset."));
                }
                case "stat" -> result.sendSystemMessage(Component.literal("You don't have enough stat points to do that"));
                default -> result.sendSystemMessage(Component.literal("Invalid parameter"));
            }
        });
        return true;
    }
}
