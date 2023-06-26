package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.S2C.PlayerSyncPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Supplier;

public class PlayerDataSyncPacket {
    private static UUID player;

    public PlayerDataSyncPacket(Player data){
        player = data.getUUID();
    }
    public PlayerDataSyncPacket(FriendlyByteBuf buf){
        player = buf.readUUID();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(player);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->{
            ServerPlayer serverPlayer = context.getSender();
            if(player != null && serverPlayer != null){
                Player result = serverPlayer.getLevel().getPlayerByUUID(player);
                assert result != null;
                ModMessages.sendToPlayer(new PlayerSyncPacket(result),serverPlayer);
            }
        });
        return true;
    }
}
