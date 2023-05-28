package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.ClientExperienceData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ExperienceDataSyncS2CPacket {
    private final double experience;

    public ExperienceDataSyncS2CPacket(double experience){
        this.experience = experience;
    }

    public ExperienceDataSyncS2CPacket(FriendlyByteBuf buf){
        this.experience = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(experience);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientExperienceData.set(experience);
        });
        return true;
    }
}
