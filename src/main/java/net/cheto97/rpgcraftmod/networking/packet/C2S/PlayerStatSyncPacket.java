package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.customstats.LifeMax;
import net.cheto97.rpgcraftmod.customstats.StatPoint;
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
                            result.sendSystemMessage(Component.literal("You can up your maximum mana").withStyle(ChatFormatting.DARK_RED));
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

                    case "reset" -> result.getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(playerClass -> result.getCapability(ResetProvider.ENTITY_RESET).ifPresent(reset -> result.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> result.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> result.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> result.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> result.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> result.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> result.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> result.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> result.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> result.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> result.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> result.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> result.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats ->result.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(level -> result.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> result.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                        reset.make();

                        switch (playerClass.getPlayerClass()) {
                            case 1 -> {
                                 lifeMax.set(12);
                                    life.set(lifeMax.get());
                                    lifeRegeneration.set(0.75);
                                    dexterity.set(4);
                                    strength.set(2);
                                    luck.set(10);
                                    agility.set(4);

                                    lifeMax.set(12+(reset.get()*1.25));
                                    life.set(lifeMax.get());
                                    lifeRegeneration.set(0.75+(reset.get()*0.015));
                                    command.set(1+(reset.get()*0.35));
                                    manaMax.set(1);
                                    mana.set(manaMax.get());
                                    manaRegeneration.set(0.15);
                                    intelligence.set(1+(reset.get()*0.01));
                                    agility.set(1+(reset.get()*0.35));
                                    dexterity.set(1+(reset.get()*0.14));
                                    defense.set(5+(reset.get()*1.25));
                                    magicDefense.set(5+(+(reset.get())*0.25));
                                    stats.set((reset.get()*2));
                            }
                            case 2 ->{
                                {
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

                                        lifeMax.set(35+(reset.get()*2));
                                        life.set(lifeMax.get());
                                        lifeRegeneration.set(3.25+(reset.get()*0.025));
                                        command.set(1+(reset.get()*0.35));
                                        manaMax.set(1);
                                        mana.set(manaMax.get());
                                        manaRegeneration.set(0.15);
                                        intelligence.set(1+(reset.get()*0.01));
                                        agility.set(1+(reset.get()*0.35));
                                        dexterity.set(1+(reset.get()*0.14));
                                        defense.set(5+(reset.get()*1.25));
                                        magicDefense.set(5+(+(reset.get())*0.25));
                                        stats.set((reset.get()*2));
                                }
                            }
                            case 3 -> {
                                {
                                        lifeMax.set(22);
                                        life.set(lifeMax.get());
                                        lifeRegeneration.set(1.2);

                                        manaMax.set(5);
                                        mana.set(manaMax.get());

                                        defense.set(3);
                                        magicDefense.set(2);

                                        lifeMax.set(35+(reset.get()*2));
                                        life.set(lifeMax.get());
                                        lifeRegeneration.set(3.25+(reset.get()*0.025));
                                        command.set(1+(reset.get()*0.35));
                                        manaMax.set(1);
                                        mana.set(manaMax.get());
                                        manaRegeneration.set(0.15);
                                        intelligence.set(1+(reset.get()*0.01));
                                        agility.set(1+(reset.get()*0.35));
                                        dexterity.set(1+(reset.get()*0.14));
                                        defense.set(5+(reset.get()*1.25));
                                        magicDefense.set(5+(+(reset.get())*0.25));
                                        stats.set((reset.get()*2));
                                }
                            }
                            case 4 ->{
                                {
                                       lifeMax.set(5);
                                        life.set(lifeMax.get());
                                        lifeRegeneration.set(0.4);

                                        defense.set(0.2);
                                        magicDefense.set(0.2);

                                        strength.set(8.3);
                                        luck.set(9.2);
                                        agility.set(8.5);
                                        dexterity.set(7.5);

                                        lifeMax.set(35+(reset.get()*2));
                                        life.set(lifeMax.get());
                                        lifeRegeneration.set(3.25+(reset.get()*0.025));
                                        command.set(1+(reset.get()*0.35));
                                        manaMax.set(1);
                                        mana.set(manaMax.get());
                                        manaRegeneration.set(0.15);
                                        intelligence.set(1+(reset.get()*0.01));
                                        agility.set(1+(reset.get()*0.35));
                                        dexterity.set(1+(reset.get()*0.14));
                                        defense.set(5+(reset.get()*1.25));
                                        magicDefense.set(5+(+(reset.get())*0.25));
                                        stats.set((reset.get()*2));
                                }
                            }
                            case 6 ->{
                                {
                                       lifeMax.set(15);
                                        life.set(lifeMax.get());

                                        manaMax.set(15);
                                        mana.set(manaMax.get());

                                        command.set(35);

                                        lifeMax.set(35+(reset.get()*2));
                                        life.set(lifeMax.get());
                                        lifeRegeneration.set(3.25+(reset.get()*0.025));
                                        command.set(1+(reset.get()*0.35));
                                        manaMax.set(1);
                                        mana.set(manaMax.get());
                                        manaRegeneration.set(0.15);
                                        intelligence.set(1+(reset.get()*0.01));
                                        agility.set(1+(reset.get()*0.35));
                                        dexterity.set(1+(reset.get()*0.14));
                                        defense.set(5+(reset.get()*1.25));
                                        magicDefense.set(5+(+(reset.get())*0.25));
                                        stats.set((reset.get()*2));
                                }
                            }
                            case 7 ->{
                                {
                                        manaMax.set(20);
                                        manaRegeneration.set(3);
                                        intelligence.set(3);
                                        strength.set(2);
                                        magicDefense.set(8);

                                        lifeMax.set(35+(reset.get()*2));
                                        life.set(lifeMax.get());
                                        lifeRegeneration.set(3.25+(reset.get()*0.025));
                                        command.set(1+(reset.get()*0.35));
                                        manaMax.set(20);
                                        mana.set(manaMax.get());
                                        manaRegeneration.set(0.15);
                                        intelligence.set(1+(reset.get()*0.01));
                                        agility.set(1+(reset.get()*0.35));
                                        dexterity.set(1+(reset.get()*0.14));
                                        defense.set(5+(reset.get()*1.25));
                                        magicDefense.set(5+(+(reset.get())*0.25));
                                        stats.set((reset.get()*2));
                                }
                            }
                            case 8 ->{
                                {
                                       lifeMax.set(35+(reset.get()*2));
                                        life.set(lifeMax.get());
                                        lifeRegeneration.set(3.25+(reset.get()*0.025));
                                        command.set(1+(reset.get()*0.35));
                                        manaMax.set(1);
                                        mana.set(manaMax.get());
                                        manaRegeneration.set(0.15);
                                        intelligence.set(1+(reset.get()*0.01));
                                        agility.set(1+(reset.get()*0.35));
                                        dexterity.set(1+(reset.get()*0.14));
                                        defense.set(5+(reset.get()*1.25));
                                        magicDefense.set(5+(+(reset.get())*0.25));
                                        stats.set((reset.get()*2));
                                }
                            }
                            default ->{
                                life.resetStat();
                                lifeMax.resetStat();
                                lifeMax.add(reset.get());

                                lifeRegeneration.resetStat();
                                lifeRegeneration.add(reset.get()*0.25);

                                mana.resetStat();
                                manaMax.resetStat();
                                manaMax.add(reset.get()*5);

                                manaRegeneration.resetStat();
                                manaRegeneration.add(reset.get()*0.5);

                                defense.resetStat();
                                defense.add(reset.get()*1.25);

                                magicDefense.resetStat();
                                magicDefense.add(reset.get()*1.25);

                                agility.resetStat();
                                agility.add(reset.get()*1.25);

                                command.resetStat();
                                command.add(reset.get()*1.3);

                                dexterity.resetStat();
                                dexterity.add(reset.get()*1.25);

                                intelligence.resetStat();
                                intelligence.add(reset.get()*1.25);

                                luck.resetStat();
                                luck.add(reset.get()*1.25);

                                strength.resetStat();
                                strength.add(reset.get()*1.25);

                                stats.resetStat();
                                stats.add((reset.get()*2)+5);
                            }
                        }
                    }))))))))))))))))));
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
