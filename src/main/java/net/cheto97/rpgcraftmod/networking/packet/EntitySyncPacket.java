package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.customstats.*;
import net.cheto97.rpgcraftmod.modsystem.Customlevel;
import net.cheto97.rpgcraftmod.modsystem.Experience;
import net.cheto97.rpgcraftmod.networking.data.*;
import net.cheto97.rpgcraftmod.providers.*;
import net.cheto97.rpgcraftmod.util.ExperienceReward;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EntitySyncPacket {
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
    
    private int idLastValue = -1;
    private int levelLastValue = -1;
    private int rankLastValue = -1;
    private BlockPos posLastValue = null;
    private double lifeLastValue = -1;
    private double lifeMaxLastValue = -1;
    private double lifeRegenerationLastValue = -1;
    private double manaLastValue = -1;
    private double manaMaxLastValue = -1;
    private double manaRegenerationLastValue = -1;
    private double intelligenceLastValue = -1;
    private double agilityLastValue = -1;
    private double strengthLastValue = -1;
    private double commandLastValue = -1;
    private double dexterityLastValue = -1;
    private double defenseLastValue = -1;
    private double magicDefenseLastValue = -1;
    private double experienceRewardLastValue = 0;

    public EntitySyncPacket(LivingEntity entity){
            this(entity.getId(),
                    entity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1),
                    new BlockPos(entity.getBlockX(),entity.getBlockY(),entity.getBlockZ()),
                    entity.getCapability(LifeProvider.ENTITY_LIFE).map(Life::get).orElse(0.0),
                    entity.getCapability(LifeProvider.ENTITY_LIFE).map(Life::getMax).orElse(0.0),
                    entity.getCapability(ManaProvider.ENTITY_MANA).map(Mana::get).orElse(0.0),
                    entity.getCapability(ManaProvider.ENTITY_MANA).map(Mana::getMax).orElse(0.0),
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
                    entity.getCapability(ExperienceRewardProvider.ENTITY_EXPERIENCE_REWARD).map(ExperienceReward::get).orElse(1.0));
    }
    public EntitySyncPacket(int id, int level,BlockPos pos, double life, double lifeMax, double mana, double manaMax,
                            double agility, double command, double defense, double magicDefense, double dexterity,
                            double intelligence, double lifeRegeneration, double manaRegeneration, double strength,
                            int rank,double experienceReward){
        if(id >= 0){
            this.idLastValue = this.id;
            this.id = id;
        }else{
            this.id = this.idLastValue;
        }
        
        if(level >= 0){
            this.levelLastValue = this.level;
            this.level = level;
        }else{
            this.level = this.levelLastValue;
        }
        
        if(pos != null){
            this.posLastValue = this.pos;
            this.pos = pos;
        }else{
            this.pos = this.posLastValue;
        }
        
        if(life >= 0){
            this.lifeLastValue = this.life;
            this.life = life;
        }else{
            this.life = this.lifeLastValue;
        }
        
        if(lifeMax >= 0){
            this.lifeMaxLastValue = this.lifeMax;
            this.lifeMax = lifeMax;
        }else{
            this.lifeMax = this.lifeMaxLastValue;
        }
        
        if(lifeRegeneration >= 0){
            this.lifeRegenerationLastValue = this.lifeRegeneration;
            this.lifeRegeneration = lifeRegeneration;
        }else{
            this.lifeRegeneration = this.lifeRegenerationLastValue;
        }
        
        if(mana >= 0){
            this.manaLastValue = this.mana;
            this.mana = mana;
        }else{
            this.mana = this.manaLastValue;
        }
        
        if(manaMax >= 0){
            this.manaMaxLastValue = this.manaMax;
            this.manaMax = manaMax;
        }else{
            this.manaMax = this.manaMaxLastValue;
        }
        
        if(manaRegeneration >= 0){
            this.manaRegenerationLastValue = this.manaRegeneration;
            this.manaRegeneration = manaRegeneration;
        }else{
            this.manaRegeneration = this.manaRegenerationLastValue;
        }
        
        if(agility >= 0){
            this.agilityLastValue = this.agility;
            this.agility = agility;
        }else{
            this.agility = this.agilityLastValue;
        }
        
        if(command >= 0){
            this.commandLastValue = this.command;
            this.command = command;
        }else{
            this.command = this.commandLastValue;
        }
        
        if(defense >= 0){
            this.defenseLastValue = this.defense;
            this.defense = defense;
        }else{
            this.defense = this.defenseLastValue;
        }
        
        if(magicDefense >= 0){
            this.magicDefenseLastValue = this.magicDefense;
            this.magicDefense = magicDefense;
        }else{
            this.magicDefense = this.magicDefenseLastValue;
        }
        
        if(dexterity >= 0){
            this.dexterityLastValue = this.dexterity;
            this.dexterity = dexterity;
        }else{
            this.dexterity = this.dexterityLastValue;
        }
        
        if(intelligence >= 0){
            this.intelligenceLastValue = this.intelligence;
            this.intelligence = intelligence;
        }else{
            this.intelligence = this.intelligenceLastValue;
        }
        
        if(strength >= 0){
            this.strengthLastValue = this.strength;
            this.strength = strength;
        }else{
            this.strength = this.strengthLastValue;
        }
        
        if(rank >= 0){
            this.rankLastValue = this.rank;
            this.rank = rank;
        }else{
            this.rank = this.rankLastValue;
        }
        if(experienceReward >= 0){
            this.experienceRewardLastValue = this.experienceReward;
            this.experienceReward = experienceReward;
        }else{
            this.experienceReward = this.experienceRewardLastValue;
        }
    }

    public EntitySyncPacket(FriendlyByteBuf buf){
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
        context.enqueueWork(() -> {
            EntityData.setEntityId(id);
            EntityData.setEntityLevel(level);
            EntityData.setEntityPos(pos);
            EntityData.setEntityLife(life);
            EntityData.setEntityLifeMax(lifeMax);
            EntityData.setEntityLifeRegeneration(lifeRegeneration);
            EntityData.setEntityMana(mana);
            EntityData.setEntityManaMax(manaMax);
            EntityData.setEntityManaRegeneration(manaRegeneration);
            EntityData.setEntityAgility(agility);
            EntityData.setEntityCommand(command);
            EntityData.setEntityDefense(defense);
            EntityData.setEntityMagicDefense(magicDefense);
            EntityData.setEntityDexterity(dexterity);
            EntityData.setEntityIntelligence(intelligence);
            EntityData.setEntityStrength(strength);
            EntityData.setEntityRank(rank);
            EntityData.setEntityExperienceReward(experienceReward);
        });
        return true;
    }
}
