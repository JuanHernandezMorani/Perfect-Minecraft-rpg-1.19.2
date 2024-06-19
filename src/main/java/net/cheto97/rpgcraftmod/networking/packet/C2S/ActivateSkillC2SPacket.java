package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ActivateSkillC2SPacket {
    private static int skillSlotID;

    public ActivateSkillC2SPacket(int id){
        skillSlotID = id;
    }
    public ActivateSkillC2SPacket(FriendlyByteBuf buf){
        skillSlotID = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(skillSlotID);
    }
    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                pressAction(player);
            }
        });
        return true;
    }

    public static void pressAction(Player entity) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        Component msg = Component.literal("Skill "+ skillSlotID + " activated at " + pos + " position.").withStyle(ChatFormatting.AQUA);

        if (entity instanceof ServerPlayer player) {
            player.sendSystemMessage(msg);
        }
    }
}