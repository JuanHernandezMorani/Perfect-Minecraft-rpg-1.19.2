package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientLifeRegenerationData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LifeRegenerationDataSyncS2CPacket {
    private final double liferegeneration;

    public LifeRegenerationDataSyncS2CPacket(double liferegeneration){
        this.liferegeneration = liferegeneration;
    }

    public LifeRegenerationDataSyncS2CPacket(FriendlyByteBuf buf){
        this.liferegeneration = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(liferegeneration);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientLifeRegenerationData.set(liferegeneration);
        });
        return true;
    }
}
