package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientManaRegenerationData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ManaRegenerationDataSyncS2CPacket {
    private final double manaregeneration;

    public ManaRegenerationDataSyncS2CPacket(double manaregeneration){
        this.manaregeneration = manaregeneration;
    }

    public ManaRegenerationDataSyncS2CPacket(FriendlyByteBuf buf){
        this.manaregeneration = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(manaregeneration);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientManaRegenerationData.set(manaregeneration);
        });
        return true;
    }
}
