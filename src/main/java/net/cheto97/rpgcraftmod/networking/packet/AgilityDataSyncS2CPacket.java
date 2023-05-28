package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientAgilityData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AgilityDataSyncS2CPacket {
    private final double agility;

    public AgilityDataSyncS2CPacket(double agility){
        this.agility = agility;
    }

    public AgilityDataSyncS2CPacket(FriendlyByteBuf buf){
        this.agility = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(agility);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientAgilityData.set(agility);
        });
        return true;
    }
}
