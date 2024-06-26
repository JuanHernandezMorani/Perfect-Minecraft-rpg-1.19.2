package net.cheto97.rpgcraftmod.networking;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.networking.ToolLeveling.packets.OpenItemValueScreenPacket;
import net.cheto97.rpgcraftmod.networking.ToolLeveling.packets.SetEnchantmentToolLevelingTable;
import net.cheto97.rpgcraftmod.networking.ToolLeveling.packets.SyncToolLevelingConfig;
import net.cheto97.rpgcraftmod.networking.packet.C2S.*;
import net.cheto97.rpgcraftmod.networking.packet.S2C.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

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

        client2Server(net);
        server2Client(net);
    }

    private static void client2Server(SimpleChannel net){
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

        net.messageBuilder(EntityInfoRequestPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EntityInfoRequestPacket::new)
                .encoder(EntityInfoRequestPacket::toBytes)
                .consumerMainThread(EntityInfoRequestPacket::handle)
                .add();

        net.messageBuilder(PlayerMountSyncDataPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerMountSyncDataPacket::new)
                .encoder(PlayerMountSyncDataPacket::toBytes)
                .consumerMainThread(PlayerMountSyncDataPacket::handle)
                .add();

        net.messageBuilder(PlayerDataSyncPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerDataSyncPacket::new)
                .encoder(PlayerDataSyncPacket::toBytes)
                .consumerMainThread(PlayerDataSyncPacket::handle)
                .add();

        net.messageBuilder(PlayerStatSyncPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerStatSyncPacket::new)
                .encoder(PlayerStatSyncPacket::toBytes)
                .consumerMainThread(PlayerStatSyncPacket::handle)
                .add();

        net.messageBuilder(PlayerNoReqPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerNoReqPacket::new)
                .encoder(PlayerNoReqPacket::toBytes)
                .consumerMainThread(PlayerNoReqPacket::handle)
                .add();

        net.messageBuilder(PlayerClassSelectPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerClassSelectPacket::new)
                .encoder(PlayerClassSelectPacket::toBytes)
                .consumerMainThread(PlayerClassSelectPacket::handle)
                .add();

        net.messageBuilder(ActivateSkillC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ActivateSkillC2SPacket::new)
                .encoder(ActivateSkillC2SPacket::toBytes)
                .consumerMainThread(ActivateSkillC2SPacket::handle)
                .add();

        net.messageBuilder(SetEnchantmentToolLevelingTable.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetEnchantmentToolLevelingTable::decode)
                .encoder(SetEnchantmentToolLevelingTable::encode)
                .consumerMainThread(SetEnchantmentToolLevelingTable::handle)
                .add();

        net.messageBuilder(SyncToolLevelingConfig.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncToolLevelingConfig::decode)
                .encoder(SyncToolLevelingConfig::encode)
                .consumerMainThread(SyncToolLevelingConfig::handle)
                .add();

        net.messageBuilder(OpenItemValueScreenPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenItemValueScreenPacket::decode)
                .encoder(OpenItemValueScreenPacket::encode)
                .consumerMainThread(OpenItemValueScreenPacket::handle)
                .add();
    }
    private static void server2Client(SimpleChannel net){
        INSTANCE = net;

        net.messageBuilder(EntitySyncPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EntitySyncPacket::new)
                .encoder(EntitySyncPacket::toBytes)
                .consumerMainThread(EntitySyncPacket::handle)
                .add();

        net.messageBuilder(PlayerMountSyncPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerMountSyncPacket::new)
                .encoder(PlayerMountSyncPacket::toBytes)
                .consumerMainThread(PlayerMountSyncPacket::handle)
                .add();

        net.messageBuilder(PlayerSyncPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerSyncPacket::new)
                .encoder(PlayerSyncPacket::toBytes)
                .consumerMainThread(PlayerSyncPacket::handle)
                .add();

        net.messageBuilder(EnergySyncPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergySyncPacket::new)
                .encoder(EnergySyncPacket::toBytes)
                .consumerMainThread(EnergySyncPacket::handle)
                .add();

        net.messageBuilder(FluidSyncPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FluidSyncPacket::new)
                .encoder(FluidSyncPacket::toBytes)
                .consumerMainThread(FluidSyncPacket::handle)
                .add();

        net.messageBuilder(SyncToolLevelingConfig.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncToolLevelingConfig::decode)
                .encoder(SyncToolLevelingConfig::encode)
                .consumerMainThread(SyncToolLevelingConfig::handle)
                .add();

        net.messageBuilder(ItemStackSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackSyncS2CPacket::new)
                .encoder(ItemStackSyncS2CPacket::toBytes)
                .consumerMainThread(ItemStackSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

}
