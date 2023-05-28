package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientCommandData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CommandDataSyncS2CPacket {
    private final double command;

    public CommandDataSyncS2CPacket(double command){
        this.command = command;
    }

    public CommandDataSyncS2CPacket(FriendlyByteBuf buf){
        this.command = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(command);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientCommandData.set(command);
        });
        return true;
    }
}
