package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.providers.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.function.Supplier;

public class ViewStatsC2SPacket {

    private static String formatDouble(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.###", symbols);
        return decimalFormat.format(value);
    }

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
                player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(lvl ->{
                    player.sendSystemMessage(Component.literal("Level: "+lvl.get()));
                    player.sendSystemMessage(Component.literal("Experience need to level up: "+formatDouble(lvl.experienceNeeded())));
                });
                player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(xp ->{
                    player.sendSystemMessage(Component.literal("Experience: "+formatDouble(xp.get())));
                });
                player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Life: "+formatDouble(stat.get())));
                });
                player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Life Regeneration: "+formatDouble(stat.get())));
                });
                player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Mana: "+formatDouble(stat.get())));
                });
                player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Mana Regeneration: "+formatDouble(stat.get())));
                });
               player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(stat ->{
                   player.sendSystemMessage(Component.literal("Agility: "+formatDouble(stat.get())));
               });
                player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Command: "+formatDouble(stat.get())));
                });
                player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Defense: "+formatDouble(stat.get())));
                });
                player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Dexterity: "+formatDouble(stat.get())));
                });
                player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Intelligent: "+formatDouble(stat.get())));
                });
                player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Luck: "+formatDouble(stat.get())));
                });
                player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(stat ->{
                    player.sendSystemMessage(Component.literal("Strength: "+formatDouble(stat.get())));
                });
                }
        });
        return true;
    }


}
