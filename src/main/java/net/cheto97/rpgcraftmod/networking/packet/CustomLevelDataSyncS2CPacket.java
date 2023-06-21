package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientCustomLevelData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CustomLevelDataSyncS2CPacket {
    private final int customlevel;

    public CustomLevelDataSyncS2CPacket(int customlevel){
        this.customlevel = customlevel;
    }

    public CustomLevelDataSyncS2CPacket(FriendlyByteBuf buf){
        this.customlevel = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(customlevel);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientCustomLevelData.set(customlevel);
        });
        return true;
    }
}
