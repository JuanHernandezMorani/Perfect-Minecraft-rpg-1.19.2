package net.cheto97.rpgcraftmod.networking.packet.S2C;

import net.cheto97.rpgcraftmod.customstats.*;
import net.cheto97.rpgcraftmod.modsystem.Customlevel;
import net.cheto97.rpgcraftmod.networking.data.EntityData;
import net.cheto97.rpgcraftmod.networking.data.PlayerMountData;
import net.cheto97.rpgcraftmod.providers.*;
import net.cheto97.rpgcraftmod.util.ExperienceReward;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlayerMountSyncPacket {
    private int id;
    private int level;
    private int rank;
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
    private double experienceReward;
    private double luck;
    private String name;

    public PlayerMountSyncPacket(LivingEntity entity){
        this(
                entity.getName().getString(),
                entity.getId(),
                entity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1),
                new BlockPos(entity.getBlockX(),entity.getBlockY(),entity.getBlockZ()),
                entity.getCapability(LifeProvider.ENTITY_LIFE).map(Life::get).orElse(0.0),
                entity.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).map(LifeMax::get).orElse(0.0),
                entity.getCapability(ManaProvider.ENTITY_MANA).map(Mana::get).orElse(0.0),
                entity.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).map(ManaMax::get).orElse(0.0),
                entity.getCapability(AgilityProvider.ENTITY_AGILITY).map(Agility::get).orElse(0.0),
                entity.getCapability(CommandProvider.ENTITY_COMMAND).map(Command::get).orElse(0.0),
                entity.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(0.0),
                entity.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).map(MagicDefense::get).orElse(0.0),
                entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).map(Dexterity::get).orElse(0.0),
                entity.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).map(Intelligence::get).orElse(0.0),
                entity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).map(LifeRegeneration::get).orElse(0.0),
                entity.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).map(ManaRegeneration::get).orElse(0.0),
                entity.getCapability(StrengthProvider.ENTITY_STRENGTH).map(Strength::get).orElse(0.0),
                entity.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(0),
                entity.getCapability(ExperienceRewardProvider.ENTITY_EXPERIENCE_REWARD).map(ExperienceReward::get).orElse(1.0),
                entity.getCapability(LuckProvider.ENTITY_LUCK).map(Luck::get).orElse(1.0));
    }
    public PlayerMountSyncPacket(String name,int id, int level, BlockPos pos, double life, double lifeMax, double mana, double manaMax,
                            double agility, double command, double defense, double magicDefense, double dexterity,
                            double intelligence, double lifeRegeneration, double manaRegeneration, double strength,
                            int rank, double experienceReward, double luck){
        if(name != null){
            this.name = name;
        }
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

        if(rank >= 0){
            this.rank = rank;
        }
        if(experienceReward >= 0){
            this.experienceReward = experienceReward;
        }
        if(luck >= 0){
            this.luck = luck;
        }
    }

    public PlayerMountSyncPacket(FriendlyByteBuf buf){
        this.name = buf.readUtf();
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
        this.rank = buf.readInt();
        this.experienceReward = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(name);
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
        buf.writeInt(rank);
        buf.writeDouble(experienceReward);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> { new PlayerMountData(name,id,rank,level,pos,experienceReward,life,lifeMax,lifeRegeneration,mana,manaMax,manaRegeneration,agility,command,defense,magicDefense,dexterity,intelligence,strength,luck);
        });
        return true;
    }
}
