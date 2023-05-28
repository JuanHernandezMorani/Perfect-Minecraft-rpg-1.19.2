package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientLifeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LifeDataSyncS2CPacket {
    private final double life;

    public LifeDataSyncS2CPacket(double life){
        this.life = life;
    }

    public LifeDataSyncS2CPacket(FriendlyByteBuf buf){
        this.life = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(life);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientLifeData.set(life);
        });
        return true;
    }
}
