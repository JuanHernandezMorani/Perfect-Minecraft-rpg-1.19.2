package net.cheto97.rpgcraftmod.networking;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.networking.packet.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.core.jmx.Server;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RpgcraftMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(DrinkManaFluidC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrinkManaFluidC2SPacket::new)
                .encoder(DrinkManaFluidC2SPacket::toBytes)
                .consumerMainThread(DrinkManaFluidC2SPacket::handle)
                .add();

        net.messageBuilder(ViewStatsC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ViewStatsC2SPacket::new)
                .encoder(ViewStatsC2SPacket::toBytes)
                .consumerMainThread(ViewStatsC2SPacket::handle)
                .add();

        net.messageBuilder(EntitySyncPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EntitySyncPacket::new)
                .encoder(EntitySyncPacket::toBytes)
                .consumerMainThread(EntitySyncPacket::handle)
                .add();

        net.messageBuilder(PlayerSyncPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerSyncPacket::new)
                .encoder(PlayerSyncPacket::toBytes)
                .consumerMainThread(PlayerSyncPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendToTracking(MSG message, LivingEntity track) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> track), message);
    }
    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
    public static <MSG> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            INSTANCE.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }

}
