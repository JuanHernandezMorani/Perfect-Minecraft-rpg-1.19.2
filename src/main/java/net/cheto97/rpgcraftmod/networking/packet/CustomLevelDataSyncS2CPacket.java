package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientCustomLevelData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CustomLevelDataSyncS2CPacket {
    private final double customlevel;

    public CustomLevelDataSyncS2CPacket(double customlevel){
        this.customlevel = customlevel;
    }

    public CustomLevelDataSyncS2CPacket(FriendlyByteBuf buf){
        this.customlevel = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(customlevel);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientCustomLevelData.set(customlevel);
        });
        return true;
    }
}
