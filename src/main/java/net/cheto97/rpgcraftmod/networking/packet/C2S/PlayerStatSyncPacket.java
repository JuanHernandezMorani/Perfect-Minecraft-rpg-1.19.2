package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.customstats.LifeMax;
import net.cheto97.rpgcraftmod.customstats.ManaMax;
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
            result.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(statPoint -> result.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(exp -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(playerClass -> result.getCapability(ResetProvider.ENTITY_RESET).ifPresent(reset -> result.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> result.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> result.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> result.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> result.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> result.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> result.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> result.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> result.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> result.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> result.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> result.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> result.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats ->result.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(level -> result.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> result.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                if(statPoint.get() > 0 && !Objects.equals(stat,"reset")){
                    double[] values = handleStat(stat,playerClass.getPlayerClass());
                    if(values[2] == -2){
                        player.sendSystemMessage(Component.literal("You cant add to that stat.").withStyle(ChatFormatting.DARK_RED));
                    }
                    lifeMax.add(values[0]);
                    life.add(values[0]);
                    lifeRegeneration.add(values[1]);
                    manaMax.add(values[2] >= 0 ? values[2] : 0);
                    mana.add(values[2] >= 0 ? values[2] : 0);
                    manaRegeneration.add(values[3] >= 0 ? values[3] : 0);
                    command.add(values[4]);
                    intelligence.add(values[5]);
                    agility.add(values[6]);
                    dexterity.add(values[7]);
                    defense.add(values[8]);
                    strength.add(values[9]);
                    magicDefense.add(values[10]);
                    luck.add(values[11]);
                    float speed = result.getSpeed() + (float)((agility.get()/4) * 0.000000003);
                    result.setSpeed(speed);

                    statPoint.consume();
                }
                else if(!Objects.equals(stat,"reset")){
                    result.sendSystemMessage(Component.literal("You don't have enough points to do that"));
                }
                else{
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
                }

                ModMessages.sendToPlayer(new PlayerSyncPacket(result),player);
            }))))))))))))))))))));
      });
        return true;
    }

    private double[] handleStat(String stat, int pClass){
        double[] res = new double[12];
        double lifeMax = 0;
        double lifeRegeneration = 0;
        double manaMax = 0;
        double manaRegeneration = 0;
        double command = 0;
        double intelligence = 0;
        double agility = 0;
        double dexterity = 0;
        double defense = 0;
        double strength = 0;
        double magicDefense = 0;
        double luck = 0;

        switch (stat){
            case "life" -> {
                double a = 7;
                double b = 5;
                double c = 11;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = 15;

                double a1 = 7;
                double b1 = 5;
                double c1 = 11;
                double d1 = 3;
                double e1 = 10;
                double f1 = 11;
                double g1 = 9;
                double h1 = 15;

                lifeMax = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
                lifeRegeneration = pClass == 1 ? a1 : pClass == 2 ? b1 : pClass == 3  ? c1 : pClass == 4 ? d1 : pClass == 5 ? e1 : pClass == 6 ? f1 : pClass == 7 ? g1 : pClass == 8 ? h1 : -1;
            }
            case "mana" -> {
                double a = 2;
                double b = 15;
                double c = -2;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = -2;

                double a1 = 7;
                double b1 = 5;
                double c1 = -1;
                double d1 = 3;
                double e1 = 10;
                double f1 = 11;
                double g1 = 9;
                double h1 = -1;

                manaMax = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
                manaRegeneration = pClass == 1 ? a1 : pClass == 2 ? b1 : pClass == 3  ? c1 : pClass == 4 ? d1 : pClass == 5 ? e1 : pClass == 6 ? f1 : pClass == 7 ? g1 : pClass == 8 ? h1 : -1;
            }
            case "dexterity" -> {
                double a = 7;
                double b = 5;
                double c = 11;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = 15;

                double a1 = 7;
                double b1 = 5;
                double c1 = 11;
                double d1 = 3;
                double e1 = 10;
                double f1 = 11;
                double g1 = 9;
                double h1 = 15;

                lifeMax = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
                dexterity = pClass == 1 ? a1 : pClass == 2 ? b1 : pClass == 3  ? c1 : pClass == 4 ? d1 : pClass == 5 ? e1 : pClass == 6 ? f1 : pClass == 7 ? g1 : pClass == 8 ? h1 : -1;
            }
            case "intelligence" -> {
                double a = 7;
                double b = 5;
                double c = 11;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = 15;

                double a1 = 7;
                double b1 = 5;
                double c1 = -1;
                double d1 = 3;
                double e1 = 10;
                double f1 = 11;
                double g1 = 9;
                double h1 = -1;

                double a2 = 7;
                double b2 = 5;
                double c2 = -1;
                double d2 = 3;
                double e2 = 10;
                double f2 = 11;
                double g2 = 9;
                double h2 = -1;

                double a3 = 7;
                double b3 = 5;
                double c3 = 11;
                double d3 = 3;
                double e3 = 10;
                double f3 = 11;
                double g3 = 9;
                double h3 = 15;

                intelligence = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
                manaMax = pClass == 1 ? a1 : pClass == 2 ? b1 : pClass == 3  ? c1 : pClass == 4 ? d1 : pClass == 5 ? e1 : pClass == 6 ? f1 : pClass == 7 ? g1 : pClass == 8 ? h1 : -1;
                manaRegeneration = pClass == 1 ? a2 : pClass == 2 ? b2 : pClass == 3  ? c2 : pClass == 4 ? d2 : pClass == 5 ? e2 : pClass == 6 ? f2 : pClass == 7 ? g2 : pClass == 8 ? h2 : -1;
                magicDefense = pClass == 1 ? a3 : pClass == 2 ? b3 : pClass == 3  ? c3 : pClass == 4 ? d3 : pClass == 5 ? e3 : pClass == 6 ? f3 : pClass == 7 ? g3 : pClass == 8 ? h3 : -1;
            }
            case "strength" -> {
                double a = 7;
                double b = 5;
                double c = 11;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = 15;

                double a1 = 7;
                double b1 = 5;
                double c1 = 11;
                double d1 = 3;
                double e1 = 10;
                double f1 = 11;
                double g1 = 9;
                double h1 = 15;

                double a2 = 7;
                double b2 = 5;
                double c2 = 11;
                double d2 = 3;
                double e2 = 10;
                double f2 = 11;
                double g2 = 9;
                double h2 = 15;

                strength = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
                defense = pClass == 1 ? a1 : pClass == 2 ? b1 : pClass == 3  ? c1 : pClass == 4 ? d1 : pClass == 5 ? e1 : pClass == 6 ? f1 : pClass == 7 ? g1 : pClass == 8 ? h1 : -1;
                lifeRegeneration = pClass == 1 ? a2 : pClass == 2 ? b2 : pClass == 3  ? c2 : pClass == 4 ? d2 : pClass == 5 ? e2 : pClass == 6 ? f2 : pClass == 7 ? g2 : pClass == 8 ? h2 : -1;
            }
            case "command" -> {
                double a = 7;
                double b = 5;
                double c = 11;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = 15;

                command = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
            }
            case "defense" -> {
                double a = 7;
                double b = 5;
                double c = 11;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = 15;

                defense = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
            }
            case "magicdefense" -> {
                double a = 7;
                double b = 5;
                double c = 11;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = 15;

                magicDefense = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
            }
            case "luck" -> {
                double a = 7;
                double b = 5;
                double c = 11;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = 15;

                luck = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
            }
            case "agility" -> {
                double a = 7;
                double b = 5;
                double c = 11;
                double d = 3;
                double e = 10;
                double f = 11;
                double g = 9;
                double h = 15;

                agility = pClass == 1 ? a : pClass == 2 ? b : pClass == 3  ? c : pClass == 4 ? d : pClass == 5 ? e : pClass == 6 ? f : pClass == 7 ? g : pClass == 8 ? h : -1;
            }
        }

        res[0] = lifeMax;
        res[1] = lifeRegeneration;
        res[2] = manaMax;
        res[3] = manaRegeneration;
        res[4] = command;
        res[5] = intelligence;
        res[6] = agility;
        res[7] = dexterity;
        res[8] = defense;
        res[9] = strength;
        res[10] = magicDefense;
        res[11] = luck;

        return res;
    }
}
