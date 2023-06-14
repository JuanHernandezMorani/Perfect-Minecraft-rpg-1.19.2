package net.cheto97.rpgcraftmod.networking;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.networking.packet.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

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

        net.messageBuilder(ManaDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaDataSyncS2CPacket::new)
                .encoder(ManaDataSyncS2CPacket::toBytes)
                .consumerMainThread(ManaDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(MaxManaDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MaxManaDataSyncS2CPacket::new)
                .encoder(MaxManaDataSyncS2CPacket::toBytes)
                .consumerMainThread(MaxManaDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(LifeDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LifeDataSyncS2CPacket::new)
                .encoder(LifeDataSyncS2CPacket::toBytes)
                .consumerMainThread(LifeDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(MaxLifeDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MaxLifeDataSyncS2CPacket::new)
                .encoder(MaxLifeDataSyncS2CPacket::toBytes)
                .consumerMainThread(MaxLifeDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(ManaRegenerationDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaRegenerationDataSyncS2CPacket::new)
                .encoder(ManaRegenerationDataSyncS2CPacket::toBytes)
                .consumerMainThread(ManaRegenerationDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(LifeRegenerationDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LifeRegenerationDataSyncS2CPacket::new)
                .encoder(LifeRegenerationDataSyncS2CPacket::toBytes)
                .consumerMainThread(LifeRegenerationDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(StrengthDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(StrengthDataSyncS2CPacket::new)
                .encoder(StrengthDataSyncS2CPacket::toBytes)
                .consumerMainThread(StrengthDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(LuckDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LuckDataSyncS2CPacket::new)
                .encoder(LuckDataSyncS2CPacket::toBytes)
                .consumerMainThread(LuckDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(IntelligenceDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(IntelligenceDataSyncS2CPacket::new)
                .encoder(IntelligenceDataSyncS2CPacket::toBytes)
                .consumerMainThread(IntelligenceDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(DexterityDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DexterityDataSyncS2CPacket::new)
                .encoder(DexterityDataSyncS2CPacket::toBytes)
                .consumerMainThread(DexterityDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(DefenseDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DefenseDataSyncS2CPacket::new)
                .encoder(DefenseDataSyncS2CPacket::toBytes)
                .consumerMainThread(DefenseDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(CommandDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CommandDataSyncS2CPacket::new)
                .encoder(CommandDataSyncS2CPacket::toBytes)
                .consumerMainThread(CommandDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(AgilityDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AgilityDataSyncS2CPacket::new)
                .encoder(AgilityDataSyncS2CPacket::toBytes)
                .consumerMainThread(AgilityDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(ExperienceDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ExperienceDataSyncS2CPacket::new)
                .encoder(ExperienceDataSyncS2CPacket::toBytes)
                .consumerMainThread(ExperienceDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(CustomLevelDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CustomLevelDataSyncS2CPacket::new)
                .encoder(CustomLevelDataSyncS2CPacket::toBytes)
                .consumerMainThread(CustomLevelDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(EntityLifeDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EntityLifeDataSyncS2CPacket::new)
                .encoder(EntityLifeDataSyncS2CPacket::toBytes)
                .consumerMainThread(EntityLifeDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(EntityMaxLifeDataSyncS2CPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EntityMaxLifeDataSyncS2CPacket::new)
                .encoder(EntityMaxLifeDataSyncS2CPacket::toBytes)
                .consumerMainThread(EntityMaxLifeDataSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
