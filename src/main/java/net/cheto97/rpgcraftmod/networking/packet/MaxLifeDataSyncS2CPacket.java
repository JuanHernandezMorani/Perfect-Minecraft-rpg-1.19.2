package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientMaxLifeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MaxLifeDataSyncS2CPacket {
    private final double maxLife;

    public MaxLifeDataSyncS2CPacket(double maxLife){
        this.maxLife = maxLife;
    }

    public MaxLifeDataSyncS2CPacket(FriendlyByteBuf buf){
        this.maxLife = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(maxLife);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientMaxLifeData.setMaxLife(maxLife);
        });
        return true;
    }
}
