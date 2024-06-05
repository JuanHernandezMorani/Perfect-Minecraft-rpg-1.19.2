package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.customstats.LifeMax;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.S2C.PlayerSyncPacket;
import net.cheto97.rpgcraftmod.providers.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class PlayerStatSyncPacket {
    private static String stat;
    public PlayerStatSyncPacket(String data){stat = data;}
    public PlayerStatSyncPacket(FriendlyByteBuf buf){
        stat = buf.readUtf();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(stat);
    }
    private double toAdd(int reset, double value){
        return reset + (1+(value * 0.25));
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->{
            ServerPlayer player = context.getSender();
            assert player != null;
            Player result = player.getLevel().getPlayerByUUID(player.getUUID());
            assert result != null;
            result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(customClass -> result.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(statPoint ->{

                if(statPoint.get() > 0){
                switch (stat){
                    case "life" -> result.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(LifeMax::add);

                    case "mana" -> {
                        if(customClass.getPlayerClass() == 8 || customClass.getPlayerClass() == 3) {
                            result.sendSystemMessage(Component.literal("You can´t up your maximum mana").withStyle(ChatFormatting.DARK_RED));
                        }else{
                            result.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(mana -> mana.add(5));
                        }
                    }

                    case "dexterity" -> result.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> result.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> result.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> {
                        dexterity.add(0.042);
                        lifeRegeneration.add(0.01);
                        lifeMax.add(0.25);
                    })));

                    case "intelligence" -> result.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> result.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(mana -> result.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> result.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> {
                        if(customClass.getPlayerClass() == 8 || customClass.getPlayerClass() == 3) {
                           intelligence.add(0.042);
                           magicDefense.add(0.25);
                        }else{
                            intelligence.add(0.042);
                            mana.add(0.5);
                            manaRegeneration.add(0.03);
                            magicDefense.add(0.15);
                        }
                    }))));

                    case "strength" -> result.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> result.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> result.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> {
                        strength.add(0.042);
                        defense.add(0.15);
                        lifeRegeneration.add(0.01);
                    })));

                    case "command" -> result.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> command.add(0.042));

                    case "defense" -> result.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> defense.add(0.45));

                    case "magicdefense" -> result.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(defense -> defense.add(0.45));

                    case "luck" -> result.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> luck.add(0.52));

                    case "agility" -> result.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> {
                        agility.add(0.042);
                        result.setSpeed(result.getSpeed()+(float)((agility.get()/4)*0.000000003));
                    });

                    case "reset" -> result.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(exp -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(playerClass -> result.getCapability(ResetProvider.ENTITY_RESET).ifPresent(reset -> result.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> result.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> result.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> result.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> result.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> result.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> result.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> result.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> result.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> result.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> result.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> result.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> result.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats ->result.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(level -> result.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> result.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                        reset.make();
                        level.makeReset();
                        exp.resetStat();

                        switch (playerClass.getPlayerClass()) {
                            case 1 -> {
                                lifeMax.set(12 + toAdd(reset.get(),12));
                                life.set(lifeMax.get());
                                lifeRegeneration.set(0.75 + toAdd(reset.get(),0.75));

                                manaMax.set(1  + toAdd(reset.get(),1));
                                mana.set(manaMax.get());
                                manaRegeneration.set(1 + toAdd(reset.get(),1));

                                command.set(1 + toAdd(reset.get(),1));
                                intelligence.set(1 + toAdd(reset.get(),1));
                                agility.set(4 + toAdd(reset.get(),4));
                                dexterity.set(4 + toAdd(reset.get(),4));
                                defense.set(1 + toAdd(reset.get(),1));
                                strength.set(2 + toAdd(reset.get(),2));
                                magicDefense.set(1 + toAdd(reset.get(),1));
                                luck.set(10 + toAdd(reset.get(),10));

                                stats.set((reset.get()*5));
                            }
                            case 2 ->{
                                {
                                    lifeMax.set(8 + toAdd(reset.get(),8));
                                    life.set(lifeMax.get());
                                    lifeRegeneration.set(0.65 + toAdd(reset.get(),0.65));

                                    manaMax.set(50  + toAdd(reset.get(),50));
                                    mana.set(manaMax.get());
                                    manaRegeneration.set(5 + toAdd(reset.get(),5));

                                    command.set(1 + toAdd(reset.get(),1));
                                    intelligence.set(10 + toAdd(reset.get(),10));
                                    agility.set(4 + toAdd(reset.get(),4));
                                    dexterity.set(4 + toAdd(reset.get(),4));
                                    defense.set(0.5 + toAdd(reset.get(),0.5));
                                    strength.set(0.5 + toAdd(reset.get(),0.5));
                                    magicDefense.set(5 + toAdd(reset.get(),5));
                                    luck.set(10 + toAdd(reset.get(),10));

                                    stats.set((reset.get()*5));
                                }
                            }
                            case 3 -> {
                                {
                                    lifeMax.set(22 + toAdd(reset.get(),22));
                                    life.set(lifeMax.get());
                                    lifeRegeneration.set(1.2 + toAdd(reset.get(),1.2));

                                    manaMax.set(5);
                                    mana.set(manaMax.get());
                                    manaRegeneration.set(1 + toAdd(reset.get(),1));

                                    command.set(1 + toAdd(reset.get(),1));
                                    intelligence.set(1 + toAdd(reset.get(),1));
                                    agility.set(1 + toAdd(reset.get(),1));
                                    dexterity.set(1 + toAdd(reset.get(),1));
                                    defense.set(3 + toAdd(reset.get(),3));
                                    strength.set(1 + toAdd(reset.get(),1));
                                    magicDefense.set(2 + toAdd(reset.get(),2));
                                    luck.set(1 + toAdd(reset.get(),1));

                                    stats.set((reset.get()*5));
                                }
                            }
                            case 4 ->{
                                lifeMax.set(5 + toAdd(reset.get(),5));
                                life.set(lifeMax.get());
                                lifeRegeneration.set(0.4 + toAdd(reset.get(),0.4));

                                manaMax.set(1  + toAdd(reset.get(),1));
                                mana.set(manaMax.get());
                                manaRegeneration.set(1 + toAdd(reset.get(),1));

                                command.set(1 + toAdd(reset.get(),1));
                                intelligence.set(1 + toAdd(reset.get(),1));
                                agility.set(8.5 + toAdd(reset.get(),8.5));
                                dexterity.set(7.5 + toAdd(reset.get(),7.5));
                                defense.set(0.2 + toAdd(reset.get(),0.2));
                                strength.set(8.3 + toAdd(reset.get(),8.3));
                                magicDefense.set(0.2 + toAdd(reset.get(),0.2));
                                luck.set(9.2 + toAdd(reset.get(),9.2));

                                stats.set((reset.get()*5));
                            }
                            case 6 ->{
                                lifeMax.set(15 + toAdd(reset.get(),15));
                                life.set(lifeMax.get());
                                lifeRegeneration.set(1 + toAdd(reset.get(),1));

                                manaMax.set(15  + toAdd(reset.get(),15));
                                mana.set(manaMax.get());
                                manaRegeneration.set(1 + toAdd(reset.get(),1));

                                command.set(35 + toAdd(reset.get(),35));
                                intelligence.set(1 + toAdd(reset.get(),1));
                                agility.set(1 + toAdd(reset.get(),1));
                                dexterity.set(1 + toAdd(reset.get(),1));
                                defense.set(1 + toAdd(reset.get(),1));
                                strength.set(1 + toAdd(reset.get(),1));
                                magicDefense.set(1 + toAdd(reset.get(),1));
                                luck.set(1 + toAdd(reset.get(),1));

                                stats.set((reset.get()*5));
                            }
                            case 7 ->{
                                lifeMax.set(20 + toAdd(reset.get(),20));
                                life.set(lifeMax.get());
                                lifeRegeneration.set(1 + toAdd(reset.get(),1));

                                manaMax.set(20  + toAdd(reset.get(),20));
                                mana.set(manaMax.get());
                                manaRegeneration.set(3 + toAdd(reset.get(),3));

                                command.set(1 + toAdd(reset.get(),1));
                                intelligence.set(3 + toAdd(reset.get(),3));
                                agility.set(1 + toAdd(reset.get(),1));
                                dexterity.set(1 + toAdd(reset.get(),1));
                                defense.set(1 + toAdd(reset.get(),1));
                                strength.set(2 + toAdd(reset.get(),2));
                                magicDefense.set(8 + toAdd(reset.get(),8));
                                luck.set(1 + toAdd(reset.get(),1));

                                stats.set((reset.get()*5));
                            }
                            case 8 ->{
                                lifeMax.set(35 + toAdd(reset.get(),35));
                                life.set(lifeMax.get());
                                lifeRegeneration.set(3.25 + toAdd(reset.get(),3.25));

                                manaMax.set(1);
                                mana.set(manaMax.get());
                                manaRegeneration.set(0.15 + toAdd(reset.get(),0.15));

                                command.set(1 + toAdd(reset.get(),1));
                                intelligence.set(1 + toAdd(reset.get(),1));
                                agility.set(1 + toAdd(reset.get(),1));
                                dexterity.set(1 + toAdd(reset.get(),1));
                                defense.set(5 + toAdd(reset.get(),5));
                                strength.set(1 + toAdd(reset.get(),1));
                                magicDefense.set(5 + toAdd(reset.get(),5));
                                luck.set(1 + toAdd(reset.get(),1));

                                stats.set((reset.get()*5));
                            }
                            default ->{
                                lifeMax.set(20 + toAdd(reset.get(),20));
                                life.set(lifeMax.get());
                                lifeRegeneration.set(1 + toAdd(reset.get(),1));

                                manaMax.set(10  + toAdd(reset.get(),10));
                                mana.set(manaMax.get());
                                manaRegeneration.set(1 + toAdd(reset.get(),1));

                                command.set(1 + toAdd(reset.get(),1));
                                intelligence.set(1 + toAdd(reset.get(),1));
                                agility.set(1 + toAdd(reset.get(),1));
                                dexterity.set(1 + toAdd(reset.get(),1));
                                defense.set(1 + toAdd(reset.get(),1));
                                strength.set(1 + toAdd(reset.get(),1));
                                magicDefense.set(1 + toAdd(reset.get(),1));
                                luck.set(1 + toAdd(reset.get(),1));

                                stats.resetStat();
                                stats.add((reset.get()*5)+5);
                            }
                        }
                    })))))))))))))))))));
                }
                if(!Objects.equals(stat, "reset")){
                    statPoint.consume();
                }
                ModMessages.sendToPlayer(new PlayerSyncPacket(result),player);
            }else{
                result.sendSystemMessage(Component.literal("You don't have enough points to do that"));
                ModMessages.sendToPlayer(new PlayerSyncPacket(result),player);
            }

        }));
      });
        return true;
    }
}
