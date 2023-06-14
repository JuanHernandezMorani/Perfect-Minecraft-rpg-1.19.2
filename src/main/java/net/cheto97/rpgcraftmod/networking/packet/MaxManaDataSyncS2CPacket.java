package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientMaxManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MaxManaDataSyncS2CPacket {
    private final double maxMana;

    public MaxManaDataSyncS2CPacket(double maxMana){
        this.maxMana = maxMana;
    }

    public MaxManaDataSyncS2CPacket(FriendlyByteBuf buf){
        this.maxMana = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(maxMana);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientMaxManaData.setMaxMana(maxMana);
        });
        return true;
    }
}
