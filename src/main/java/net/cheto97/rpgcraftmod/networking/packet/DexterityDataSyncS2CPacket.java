package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientDexterityData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DexterityDataSyncS2CPacket {
    private final double dexterity;

    public DexterityDataSyncS2CPacket(double dexterity){
        this.dexterity = dexterity;
    }

    public DexterityDataSyncS2CPacket(FriendlyByteBuf buf){
        this.dexterity = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(dexterity);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientDexterityData.set(dexterity);
        });
        return true;
    }
}
