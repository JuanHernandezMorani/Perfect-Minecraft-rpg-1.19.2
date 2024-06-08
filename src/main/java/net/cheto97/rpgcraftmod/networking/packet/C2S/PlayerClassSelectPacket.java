package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.customstats.StatPoint;
import net.cheto97.rpgcraftmod.modsystem.CustomClass;
import net.cheto97.rpgcraftmod.modsystem.FirstJoin;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.S2C.PlayerSyncPacket;
import net.cheto97.rpgcraftmod.providers.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Random;
import java.util.function.Supplier;

public class PlayerClassSelectPacket {
    private static String playerClass;

    public PlayerClassSelectPacket(String data){
        playerClass = data;}
    public PlayerClassSelectPacket(FriendlyByteBuf buf){
        playerClass = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(playerClass);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->{
            ServerPlayer player = context.getSender();
            assert player != null;
            Player result = player.getLevel().getPlayerByUUID(player.getUUID());
            assert result != null;
            Random random = new Random();

            switch (playerClass){
                case "archer" -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(pClass -> pClass.set(1));
                case "mage" -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(pClass -> pClass.set(2));
                case "warrior" -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(pClass -> pClass.set(3));
                case "assassin" -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(pClass -> pClass.set(4));
                case "beast tamer" -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(pClass -> pClass.set(6));
                case "priest" -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(pClass -> pClass.set(7));
                case "knight" -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(pClass -> pClass.set(8));
                case "random" -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(pClass -> {
                        pClass.set(random.nextInt(8) + 1);
                        switch (pClass.getPlayerClass()){
                            case 1 -> playerClass = "archer";
                            case 2 -> playerClass = "mage";
                            case 3 -> playerClass = "warrior";
                            case 4 -> playerClass = "assassin";
                            case 5 -> playerClass = "balanced";
                            case 6 -> playerClass = "beast tamer";
                            case 7 -> playerClass = "priest";
                            case 8 -> playerClass = "knight";
                        }
                    });
                default -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(pClass -> pClass.set(5));
            }
            setPlayerStat(result.getCapability(CustomClassProvider.PLAYER_CLASS).map(CustomClass::getPlayerClass).orElse(0),result);
            result.getCapability(FirstJoinProvider.ENTITY_FIRST_JOIN).ifPresent(FirstJoin::set);

            CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
                handler.getStacksHandler("body").ifPresent(stacks -> {
                    stacks.addPermanentModifier(new AttributeModifier(player.getUUID(), player.getName().getString(), -1, AttributeModifier.Operation.ADDITION));
                });
            });

            ModMessages.sendToPlayer(new PlayerSyncPacket(result),player);
            player.sendSystemMessage(Component.literal("Your class is: "+playerClass));
            player.setInvulnerable(false);
        });
        return true;
    }

    private void setPlayerStat(int classType, Player player) {
        if (classType > 8 || classType < 1) {
            classType = 5;
        }
        switch (classType) {
            case 1 -> {
                player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats -> player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                    lifeMax.set(12);
                    life.set(lifeMax.get());
                    lifeRegeneration.set(0.75);
                    dexterity.set(4);
                    strength.set(2);
                    luck.set(10);
                    agility.set(4);
                })))))))))))))));
            }
            case 2 ->{
                {
                    player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats -> player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                        lifeMax.set(8);
                        life.set(lifeMax.get());
                        lifeRegeneration.set(0.65);

                        manaMax.set(50);
                        mana.set(manaMax.get());
                        manaRegeneration.set(5);

                        intelligence.set(10);
                        magicDefense.set(5);
                        defense.set(0.5);
                        strength.set(0.5);
                    })))))))))))))));
                }
            }
            case 3 -> {
                {
                    player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats -> player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                        lifeMax.set(22);
                        life.set(lifeMax.get());
                        lifeRegeneration.set(1.2);

                        manaMax.set(5);
                        mana.set(manaMax.get());

                        defense.set(3);
                        magicDefense.set(2);


                    })))))))))))))));
                }
            }
            case 4 ->{
                {
                    player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats -> player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                        lifeMax.set(5);
                        life.set(lifeMax.get());
                        lifeRegeneration.set(0.4);

                        defense.set(0.2);
                        magicDefense.set(0.2);

                        strength.set(8.3);
                        luck.set(9.2);
                        agility.set(8.5);
                        dexterity.set(7.5);
                    })))))))))))))));
                }
            }
            case 6 ->{
                {
                    player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats -> player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                        lifeMax.set(15);
                        life.set(lifeMax.get());

                        manaMax.set(15);
                        mana.set(manaMax.get());

                        command.set(35);
                    })))))))))))))));
                }
            }
            case 7 ->{
                {
                    player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats -> player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                        manaMax.set(20);
                        manaRegeneration.set(3);
                        intelligence.set(3);
                        strength.set(2);
                        magicDefense.set(8);
                    })))))))))))))));
                }
            }
            case 8 ->{
                {
                    player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats -> player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                        lifeMax.set(35);
                        life.set(lifeMax.get());
                        lifeRegeneration.set(3.25);

                        manaMax.set(1);
                        mana.set(manaMax.get());
                        manaRegeneration.set(0.15);

                        defense.set(5);
                        magicDefense.set(5);
                    })))))))))))))));
                }
            }
            default -> player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(StatPoint::add5);
        }
    }
}
