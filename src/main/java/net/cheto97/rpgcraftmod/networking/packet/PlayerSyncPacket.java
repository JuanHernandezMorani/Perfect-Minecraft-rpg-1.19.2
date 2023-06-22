package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.networking.data.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlayerSyncPacket {
    private int id = -1;
    private int level = -1;
    private BlockPos pos = null;
    private String name = null;
    private double life = -1;
    private double lifeMax = -1;
    private double lifeRegeneration = -1;
    private double mana = -1;
    private double manaMax = -1;
    private double manaRegeneration = -1;
    private double intelligence = -1;
    private double agility = -1;
    private double strength = -1;
    private double command = -1;
    private double dexterity = -1;
    private double defense = -1;
    private double magicDefense = -1;
    private double experience = -1;

    public PlayerSyncPacket(String name,int id, int level, BlockPos pos, double life, double lifeMax, double mana, double manaMax,
                            double agility, double command, double defense, double magicDefense, double dexterity,
                            double intelligence, double lifeRegeneration, double manaRegeneration, double strength,
                            double experience){
        if(name != null){
            this.name = name;
        }
        if(id != -1){
            this.id = id;
        }
        if(level != -1){
            this.level = level;
        }
        if(pos != null){
            this.pos = pos;
        }
        if(life != -1){
            this.life = life;
        }
        if(lifeMax != -1){
            this.lifeMax = lifeMax;
        }
        if(lifeRegeneration != -1){
            this.lifeRegeneration = lifeRegeneration;
        }
        if(mana != -1){
            this.mana = mana;
        }
        if(manaMax != -1){
            this.manaMax = manaMax;
        }
        if(manaRegeneration != -1){
            this.manaRegeneration = manaRegeneration;
        }
        if(agility != -1){
            this.agility = agility;
        }
        if(command != -1){
            this.command = command;
        }
        if(defense != -1){
            this.defense = defense;
        }
        if(magicDefense != -1){
            this.magicDefense = magicDefense;
        }
        if(dexterity != -1){
            this.dexterity = dexterity;
        }
        if(intelligence != -1){
            this.intelligence = intelligence;
        }
        if(strength != -1){
            this.strength = strength;
        }
        if(experience != -1){
            this.experience = experience;
        }
        
    }

    public PlayerSyncPacket(FriendlyByteBuf buf){
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
        this.experience = buf.readDouble();
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
        buf.writeDouble(experience);
    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPosData.set(pos);
            ClientPosData.setId(id);
            ClientLevelData.set(level);
            ClientLevelData.setId(id);
            ClientNameData.set(name);
            ClientNameData.setId(id);
            ClientLifeData.set(life);
            ClientLifeData.setMax(lifeMax);
            ClientLifeData.setId(id);
            ClientLifeRegenerationData.set(lifeRegeneration);
            ClientLifeRegenerationData.setId(id);
            ClientManaData.set(mana);
            ClientManaData.setMax(manaMax);
            ClientManaData.setId(id);
            ClientManaRegenerationData.set(manaRegeneration);
            ClientManaRegenerationData.setId(id);
            ClientAgilityData.set(agility);
            ClientAgilityData.setId(id);
            ClientDexterityData.set(dexterity);
            ClientDexterityData.setId(id);
            ClientDefenseData.set(defense);
            ClientDefenseData.setId(id);
            ClientMagicDefenseData.set(magicDefense);
            ClientMagicDefenseData.setId(id);
            ClientIntelligenceData.set(intelligence);
            ClientIntelligenceData.setId(id);
            ClientCommandData.set(command);
            ClientCommandData.setId(id);
            ClientStrengthData.set(strength);
            ClientStrengthData.setId(id);
            ClientExperienceData.set(experience);
            ClientExperienceData.setId(id);
        });
        return true;
    }
}
