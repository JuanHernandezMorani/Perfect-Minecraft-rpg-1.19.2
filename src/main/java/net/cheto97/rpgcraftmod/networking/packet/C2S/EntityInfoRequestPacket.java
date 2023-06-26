package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.S2C.EntitySyncPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EntityInfoRequestPacket {
    private static int entityRequested;

    public EntityInfoRequestPacket(LivingEntity entity) {
        entityRequested = entity.getId();
    }
    public EntityInfoRequestPacket(FriendlyByteBuf buffer) {
        entityRequested = buffer.readInt();
    }
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(entityRequested);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->{
            ServerPlayer player = context.getSender();
            if(player != null){
                LivingEntity result = (LivingEntity)player.getLevel().getEntities().get(entityRequested);
                assert result != null;
                ModMessages.sendToPlayer(new EntitySyncPacket(result),player);
            }
        });
        return true;
    }
}
