package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientIntelligenceData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class IntelligenceDataSyncS2CPacket {
    private final double intelligence;

    public IntelligenceDataSyncS2CPacket(double intelligence){
        this.intelligence = intelligence;
    }

    public IntelligenceDataSyncS2CPacket(FriendlyByteBuf buf){
        this.intelligence = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(intelligence);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientIntelligenceData.set(intelligence);
        });
        return true;
    }
}
