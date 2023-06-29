package net.cheto97.rpgcraftmod.networking.packet.C2S;

import io.netty.buffer.Unpooled;
import net.cheto97.rpgcraftmod.menu.PlayerClassSelectMenu;
import net.cheto97.rpgcraftmod.menu.PlayerStatsMenu;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.data.*;

import net.cheto97.rpgcraftmod.providers.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ViewStatsC2SPacket {

    public ViewStatsC2SPacket(){

    }

    public ViewStatsC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

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

        if (entity instanceof ServerPlayer player) {
            BlockPos pos = new BlockPos(x, y, z);
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Stat Menu");
                }
                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new PlayerStatsMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        }
    }
}