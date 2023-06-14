package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.client.*;

import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.network.NetworkEvent;

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
                player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(cLvl ->{
                    player.sendSystemMessage(Component.literal("Level: "+ ClientCustomLevelData.getPlayerCustomLevel()+" XP needed: "+cLvl.experienceNeeded()+" XP: "+ ClientExperienceData.getPlayerExperience()));
                });
                player.sendSystemMessage(Component.literal("Life: "+ ClientMaxLifeData.getPlayerMaxLife()+" "+"regeneration: "+ ClientLifeRegenerationData.getPlayerLifeRegeneration()));
                player.sendSystemMessage(Component.literal("Mana: "+ClientMaxManaData.getPlayerMaxMana()+" "+"regeneration: "+ClientManaRegenerationData.getPlayerManaRegeneration()));
                player.sendSystemMessage(Component.literal("Agility: "+ClientAgilityData.getPlayerAgility()));
                player.sendSystemMessage(Component.literal("Command: "+ClientCommandData.getPlayerCommand()));
                player.sendSystemMessage(Component.literal("Defense: "+ClientDefenseData.getPlayerDefense()));
                player.sendSystemMessage(Component.literal("Dexterity: "+ClientDexterityData.getPlayerDexterity()));
                player.sendSystemMessage(Component.literal("Intelligent: "+ClientIntelligenceData.getPlayerIntelligence()));
                player.sendSystemMessage(Component.literal("Luck: "+ClientLuckData.getPlayerLuck()));
                player.sendSystemMessage(Component.literal("Strength: "+ClientStrengthData.getPlayerStrength()));
            }
        });
        return true;
    }


}
