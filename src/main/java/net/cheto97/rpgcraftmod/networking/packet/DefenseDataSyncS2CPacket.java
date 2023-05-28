package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientDefenseData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DefenseDataSyncS2CPacket {
    private final double defense;

    public DefenseDataSyncS2CPacket(double defense){
        this.defense = defense;
    }

    public DefenseDataSyncS2CPacket(FriendlyByteBuf buf){
        this.defense = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(defense);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientDefenseData.set(defense);
        });
        return true;
    }
}
