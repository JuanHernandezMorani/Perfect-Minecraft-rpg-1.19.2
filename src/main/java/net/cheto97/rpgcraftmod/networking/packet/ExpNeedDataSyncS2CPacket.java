package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.util.client.ClientLifeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ExpNeedDataSyncS2CPacket {
    private final double expNeed;

    public ExpNeedDataSyncS2CPacket(double expNeed){
        this.expNeed = expNeed;
    }

    public ExpNeedDataSyncS2CPacket(FriendlyByteBuf buf){
        this.expNeed = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(expNeed);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientLifeData.set(expNeed);
        });
        return true;
    }
}
