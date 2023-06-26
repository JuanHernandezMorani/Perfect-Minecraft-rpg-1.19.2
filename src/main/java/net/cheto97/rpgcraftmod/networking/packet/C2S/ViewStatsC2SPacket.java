package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.data.*;

import net.cheto97.rpgcraftmod.providers.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.damagesource.DamageSource;
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
                player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(playerLevel ->{
                    player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life ->{
                        player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> {
                            player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana ->{
                                player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> {
                                    player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> {
                                        player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> {
                                            player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> {
                                                player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> {
                                                    player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> {
                                                        player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> {
                                                            player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> {
                                                                player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> {
                                                                    player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(experience -> {
                                                                        player.sendSystemMessage(Component.literal("Level: "+playerLevel.get()+" XP needed: "+playerLevel.experienceNeeded()+" XP: "+experience.get()));
                                                                        player.sendSystemMessage(Component.literal("Life: "+life.getMax()+" regeneration: "+lifeRegeneration.get()));
                                                                        player.sendSystemMessage(Component.literal("Mana: "+mana.getMax()+" regeneration: "+manaRegeneration.get()));
                                                                        player.sendSystemMessage(Component.literal("Agility: "+agility.get()));
                                                                        player.sendSystemMessage(Component.literal("Command: "+command.get()));
                                                                        player.sendSystemMessage(Component.literal("Defense: "+defense.get()+" MagicDefense: "+magicDefense.get()));
                                                                        player.sendSystemMessage(Component.literal("Dexterity: "+dexterity.get()));
                                                                        player.sendSystemMessage(Component.literal("Intelligent: "+intelligence.get()));
                                                                        player.sendSystemMessage(Component.literal("Strength: "+strength.get()));
                                                                    });
                                                                });
                                                            });
                                                        });
                                                    });
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            }
        });
        return true;
    }


}