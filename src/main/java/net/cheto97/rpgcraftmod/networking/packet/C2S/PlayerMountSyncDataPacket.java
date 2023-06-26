package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.S2C.PlayerMountSyncPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlayerMountSyncDataPacket {

    public PlayerMountSyncDataPacket(){

    }

    public PlayerMountSyncDataPacket(FriendlyByteBuf buf){}

    public void toBytes(FriendlyByteBuf buf){}

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->{
            ServerPlayer serverPlayer = context.getSender();
            if(serverPlayer != null){
                Entity result = serverPlayer.getVehicle();
                if(result instanceof LivingEntity mountResult){
                    ModMessages.sendToPlayer(new PlayerMountSyncPacket(mountResult),serverPlayer);
                }

            }
        });
        return true;
    }
}
