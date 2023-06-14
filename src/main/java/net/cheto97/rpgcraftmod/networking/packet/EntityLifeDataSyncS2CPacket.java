package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.EntityLifeData;
import net.cheto97.rpgcraftmod.providers.LifeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EntityLifeDataSyncS2CPacket {
    private final int entityId;
    private final double life;

    public EntityLifeDataSyncS2CPacket(int entityId, double life) {
        this.entityId = entityId;
        this.life = life;
    }

    public EntityLifeDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.life = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeDouble(life);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            EntityLifeData.set(life,entityId);
        });
        return true;
    }
}
