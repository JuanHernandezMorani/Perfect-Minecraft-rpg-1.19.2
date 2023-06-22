package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.networking.data.*;

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
                    player.sendSystemMessage(Component.literal("Level: "+ ClientLevelData.getEntityId()+" XP needed: "+cLvl.experienceNeeded()+" XP: "+ ClientExperienceData.getEntityData()));
                });
                player.sendSystemMessage(Component.literal("Life: "+ ClientLifeData.getEntityDataMax()+" "+"regeneration: "+ ClientLifeRegenerationData.getEntityData()));
                player.sendSystemMessage(Component.literal("Mana: "+ClientManaData.getEntityData()+" "+"regeneration: "+ClientManaRegenerationData.getEntityData()));
                player.sendSystemMessage(Component.literal("Agility: "+ClientAgilityData.getEntityData()));
                player.sendSystemMessage(Component.literal("Command: "+ClientCommandData.getEntityData()));
                player.sendSystemMessage(Component.literal("Defense: "+ClientDefenseData.getEntityData()));
                player.sendSystemMessage(Component.literal("Dexterity: "+ClientDexterityData.getEntityData()));
                player.sendSystemMessage(Component.literal("Intelligent: "+ClientIntelligenceData.getEntityData()));
                player.sendSystemMessage(Component.literal("Strength: "+ClientStrengthData.getEntityData()));
            }
        });
        return true;
    }


}