package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientLuckData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LuckDataSyncS2CPacket {
    private final double luck;

    public LuckDataSyncS2CPacket(double luck){
        this.luck = luck;
    }

    public LuckDataSyncS2CPacket(FriendlyByteBuf buf){
        this.luck = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(luck);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientLuckData.set(luck);
        });
        return true;
    }
}
