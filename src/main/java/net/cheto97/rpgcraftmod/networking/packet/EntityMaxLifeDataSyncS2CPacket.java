package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.EntityLifeData;
import net.cheto97.rpgcraftmod.client.EntityMaxLifeData;
import net.cheto97.rpgcraftmod.providers.LifeProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EntityMaxLifeDataSyncS2CPacket {
    private final int entityId;
    private final double maxLife;

    public EntityMaxLifeDataSyncS2CPacket(int entityId, double maxLife) {
        this.entityId = entityId;
        this.maxLife = maxLife;
    }

    public EntityMaxLifeDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.maxLife = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeDouble(maxLife);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            EntityMaxLifeData.set(maxLife,entityId);
        });
        return true;
    }
}
