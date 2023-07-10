package net.cheto97.rpgcraftmod.networking.ToolLeveling;

import net.cheto97.rpgcraftmod.networking.ToolLeveling.packets.*;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.Names;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Names.MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    public static void registerPackets() {

        int id = 0;

        INSTANCE.registerMessage(id++,
                SetEnchantmentToolLevelingTable.class,
                SetEnchantmentToolLevelingTable::encode,
                SetEnchantmentToolLevelingTable::decode,
                SetEnchantmentToolLevelingTable::handle);

        INSTANCE.registerMessage(id++,
                SyncToolLevelingConfig.class,
                SyncToolLevelingConfig::encode,
                SyncToolLevelingConfig::decode,
                SyncToolLevelingConfig::handle);

        INSTANCE.registerMessage(id++,
                OpenItemValueScreenPacket.class,
                OpenItemValueScreenPacket::encode,
                OpenItemValueScreenPacket::decode,
                OpenItemValueScreenPacket::handle);
    }

}
