package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientStrengthData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class StrengthDataSyncS2CPacket {
    private final double strength;

    public StrengthDataSyncS2CPacket(double strength){
        this.strength = strength;
    }

    public StrengthDataSyncS2CPacket(FriendlyByteBuf buf){
        this.strength = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(strength);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientStrengthData.set(strength);
        });
        return true;
    }
}
