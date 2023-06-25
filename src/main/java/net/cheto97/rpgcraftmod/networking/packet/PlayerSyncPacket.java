package net.cheto97.rpgcraftmod.networking.packet;

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
    private int id;
    private int level;
    private BlockPos pos;
    private double life ;
    private double lifeMax;
    private double lifeRegeneration;
    private double mana;
    private double manaMax;
    private double manaRegeneration;
    private double intelligence;
    private double agility;
    private double strength;
    private double command;
    private double dexterity;
    private double defense;
    private double magicDefense;
    private double experience;


    public PlayerSyncPacket(Player player){
        this(player.getId(),
                player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1),
                new BlockPos(player.getBlockX(),player.getBlockY(),player.getBlockZ()),
                player.getCapability(LifeProvider.ENTITY_LIFE).map(Life::get).orElse(0.0),
                player.getCapability(LifeProvider.ENTITY_LIFE).map(Life::getMax).orElse(0.0),
                player.getCapability(ManaProvider.ENTITY_MANA).map(Mana::get).orElse(0.0),
                player.getCapability(ManaProvider.ENTITY_MANA).map(Mana::getMax).orElse(0.0),
                player.getCapability(AgilityProvider.ENTITY_AGILITY).map(Agility::get).orElse(0.0),
                player.getCapability(CommandProvider.ENTITY_COMMAND).map(Command::get).orElse(0.0),
                player.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(0.0),
                player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).map(MagicDefense::get).orElse(0.0),
                player.getCapability(DexterityProvider.ENTITY_DEXTERITY).map(Dexterity::get).orElse(0.0),
                player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).map(Intelligence::get).orElse(0.0),
                player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).map(LifeRegeneration::get).orElse(0.0),
                player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).map(ManaRegeneration::get).orElse(0.0),
                player.getCapability(StrengthProvider.ENTITY_STRENGTH).map(Strength::get).orElse(0.0),
                player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).map(Experience::get).orElse(0.0));
    }

    public PlayerSyncPacket(int id, int level, BlockPos pos, double life, double lifeMax, double mana, double manaMax,
                            double agility, double command, double defense, double magicDefense, double dexterity,
                            double intelligence, double lifeRegeneration, double manaRegeneration, double strength,
                            double experience){
        if(id >= 0){
            this.id = id;
        }

        if(level >= 0){
            this.level = level;
        }

        if(pos != null){
            this.pos = pos;
        }

        if(life >= 0){
            this.life = life;
        }

        if(lifeMax >= 0){
            this.lifeMax = lifeMax;
        }

        if(lifeRegeneration >= 0){
            this.lifeRegeneration = lifeRegeneration;
        }

        if(mana >= 0){
            this.mana = mana;
        }

        if(manaMax >= 0){
            this.manaMax = manaMax;
        }

        if(manaRegeneration >= 0){
            this.manaRegeneration = manaRegeneration;
        }

        if(agility >= 0){
            this.agility = agility;
        }

        if(command >= 0){
            this.command = command;
        }

        if(defense >= 0){
            this.defense = defense;
        }

        if(magicDefense >= 0){
            this.magicDefense = magicDefense;
        }

        if(dexterity >= 0){
            this.dexterity = dexterity;
        }

        if(intelligence >= 0){
            this.intelligence = intelligence;
        }

        if(strength >= 0){
            this.strength = strength;
        }
        if(experience >= 0){
            this.experience = experience;
        }
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
        });
        return true;
    }
}
