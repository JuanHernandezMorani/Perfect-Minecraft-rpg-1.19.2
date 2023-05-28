package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ManaDataSyncS2CPacket {
    private final double mana;

    public ManaDataSyncS2CPacket(double mana){
        this.mana = mana;
    }

    public ManaDataSyncS2CPacket(FriendlyByteBuf buf){
        this.mana = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(mana);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientManaData.set(mana);
        });
        return true;
    }
}
