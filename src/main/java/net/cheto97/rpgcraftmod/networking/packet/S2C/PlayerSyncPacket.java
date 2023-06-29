package net.cheto97.rpgcraftmod.networking.packet.S2C;

import net.cheto97.rpgcraftmod.customstats.*;
import net.cheto97.rpgcraftmod.modsystem.*;
import net.cheto97.rpgcraftmod.networking.data.*;
import net.cheto97.rpgcraftmod.providers.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlayerSyncPacket {
    private final int id;
    private final int level;
    private final BlockPos pos;
    private final double life;
    private final double lifeMax;
    private final double lifeRegeneration;
    private final double mana;
    private final double manaMax;
    private final double manaRegeneration;
    private final double intelligence;
    private final double agility;
    private final double strength;
    private final double command;
    private final double dexterity;
    private final double defense;
    private final double magicDefense;
    private final double experience;
    private final double luck;
    private final int statPoints;
    private final double expNeed;
    private final boolean join;
    private final int playerClass;

    public PlayerSyncPacket(Player player){
        this(player.getId(),
                player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1),
                new BlockPos(player.getBlockX(),player.getBlockY(),player.getBlockZ()),
                player.getCapability(LifeProvider.ENTITY_LIFE).map(Life::get).orElse(0.0),
                player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).map(LifeMax::get).orElse(0.0),
                player.getCapability(ManaProvider.ENTITY_MANA).map(Mana::get).orElse(0.0),
                player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).map(ManaMax::get).orElse(0.0),
                player.getCapability(AgilityProvider.ENTITY_AGILITY).map(Agility::get).orElse(0.0),
                player.getCapability(CommandProvider.ENTITY_COMMAND).map(Command::get).orElse(0.0),
                player.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(0.0),
                player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).map(MagicDefense::get).orElse(0.0),
                player.getCapability(DexterityProvider.ENTITY_DEXTERITY).map(Dexterity::get).orElse(0.0),
                player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).map(Intelligence::get).orElse(0.0),
                player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).map(LifeRegeneration::get).orElse(0.0),
                player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).map(ManaRegeneration::get).orElse(0.0),
                player.getCapability(StrengthProvider.ENTITY_STRENGTH).map(Strength::get).orElse(0.0),
                player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).map(Experience::get).orElse(0.0),
                player.getCapability(LuckProvider.ENTITY_LUCK).map(Luck::get).orElse(0.0),
                player.getCapability(StatPointProvider.ENTITY_STATPOINT).map(StatPoint::get).orElse(-1),
                player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::experienceNeeded).orElse(0.0),
                player.getCapability(FirstJoinProvider.ENTITY_FIRST_JOIN).map(FirstJoin::get).orElse(false),
                player.getCapability(CustomClassProvider.PLAYER_CLASS).map(CustomClass::getPlayerClass).orElse(0));
    }

    public PlayerSyncPacket(int id, int level, BlockPos pos, double life, double lifeMax, double mana, double manaMax,
                            double agility, double command, double defense, double magicDefense, double dexterity,
                            double intelligence, double lifeRegeneration, double manaRegeneration, double strength,
                            double experience,double luck,int stat,double expNeed, boolean join, int playerClass){
        this.id = id;
        this.level = level;
        this.pos = pos;
        this.life = life;
        this.lifeMax = lifeMax;
        this.lifeRegeneration = lifeRegeneration;
        this.mana = mana;
        this.manaMax = manaMax;
        this.manaRegeneration = manaRegeneration;
        this.agility = agility;
        this.command = command;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.strength = strength;
        this.experience = experience;
        this.luck = luck;
        this.statPoints = stat;
        this.expNeed = expNeed;
        this.join = join;
        this.playerClass = playerClass;
    }

    public PlayerSyncPacket(FriendlyByteBuf buf){
        this.id = buf.readInt();
        this.level = buf.readInt();
        this.pos = buf.readBlockPos();
        this.life = buf.readDouble();
        this.lifeMax = buf.readDouble();
        this.lifeRegeneration = buf.readDouble();
        this.mana = buf.readDouble();
        this.manaMax = buf.readDouble();
        this.manaRegeneration = buf.readDouble();
        this.agility = buf.readDouble();
        this.command = buf.readDouble();
        this.defense = buf.readDouble();
        this.magicDefense = buf.readDouble();
        this.dexterity = buf.readDouble();
        this.intelligence = buf.readDouble();
        this.strength = buf.readDouble();
        this.experience = buf.readDouble();
        this.luck = buf.readDouble();
        this.statPoints = buf.readInt();
        this.expNeed = buf.readDouble();
        this.join = buf.readBoolean();
        this.playerClass = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(id);
        buf.writeInt(level);
        buf.writeBlockPos(pos);
        buf.writeDouble(life);
        buf.writeDouble(lifeMax);
        buf.writeDouble(lifeRegeneration);
        buf.writeDouble(mana);
        buf.writeDouble(manaMax);
        buf.writeDouble(manaRegeneration);
        buf.writeDouble(agility);
        buf.writeDouble(command);
        buf.writeDouble(defense);
        buf.writeDouble(magicDefense);
        buf.writeDouble(dexterity);
        buf.writeDouble(intelligence);
        buf.writeDouble(strength);
        buf.writeDouble(experience);
        buf.writeDouble(luck);
        buf.writeInt(statPoints);
        buf.writeDouble(expNeed);
        buf.writeBoolean(join);
        buf.writeInt(playerClass);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            PlayerData.setPlayerId(id);
            PlayerData.setPlayerLevel(level);
            PlayerData.setPlayerPos(pos);
            PlayerData.setPlayerLife(life);
            PlayerData.setPlayerLifeMax(lifeMax);
            PlayerData.setPlayerLifeRegeneration(lifeRegeneration);
            PlayerData.setPlayerMana(mana);
            PlayerData.setPlayerManaMax(manaMax);
            PlayerData.setPlayerManaRegeneration(manaRegeneration);
            PlayerData.setPlayerAgility(agility);
            PlayerData.setPlayerCommand(command);
            PlayerData.setPlayerDefense(defense);
            PlayerData.setPlayerMagicDefense(magicDefense);
            PlayerData.setPlayerDexterity(dexterity);
            PlayerData.setPlayerIntelligence(intelligence);
            PlayerData.setPlayerStrength(strength);
            PlayerData.setPlayerExperience(experience);
            PlayerData.setPlayerLuck(luck);
            PlayerData.setPlayerStatPoints(statPoints);
            PlayerData.setExpNeed(expNeed);
            PlayerData.setJoin(join);
            PlayerData.setPlayerClass(playerClass);
        });
        return true;
    }
}
