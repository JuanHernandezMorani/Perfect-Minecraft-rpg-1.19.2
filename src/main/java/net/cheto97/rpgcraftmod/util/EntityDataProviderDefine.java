package net.cheto97.rpgcraftmod.util;

import net.cheto97.rpgcraftmod.customstats.Rank;
import net.cheto97.rpgcraftmod.modsystem.Customlevel;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.cheto97.rpgcraftmod.providers.RankProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class EntityDataProviderDefine {
    private final static Random random = new Random();
    public static double DoubleGenerator(String provider, LivingEntity data){
        switch (provider){
            case "MagicDefense" ->{
                return GenerateMagicDefense(data);
            }
            case "Defense" ->{
                return GenerateDefense(data);
            }
            case "Dexterity" ->{
                return GenerateDexterity(data);
            }
            case "GenerateAgility" ->{
                return GenerateAgility(data);
            }
            case "Command" ->{
                return GenerateCommand(data);
            }
            case "Intelligence" ->{
                return GenerateIntelligence(data);
            }
            case "Life" ->{
                return GenerateLife(data);
            }
            case "LifeRegeneration" ->{
                return GenerateLifeRegeneration(data);
            }
            case "Mana" ->{
                return GenerateMana(data);
            }
            case "ManaRegeneration" ->{
                return GenerateManaRegeneration(data);
            }
            case "Strength" ->{
                return GenerateStrength(data);
            }
            case "ExperienceReward"->{
                return GenerateExperienceReward(data);
            }
        }
        return 0;
    }

    private static double GenerateAgility(LivingEntity data){
        if(data instanceof Player){
            return 1;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double res = 1 + 1 * random.nextDouble(bonusLevel*0.01);
        return res + bonusRank*(bonusLevel*0.0002);
    }
    private static double GenerateCommand(LivingEntity data){
        if(data instanceof Player){
            return 1;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.0002);
    }
    private static double GenerateDefense(LivingEntity data){
        if(data instanceof Player){
            return 1;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.0052);
    }
    private static double GenerateDexterity(LivingEntity data){
        if(data instanceof Player){
            return 1;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.0002);
    }
    private static double GenerateMagicDefense(LivingEntity data){
        if(data instanceof Player){
            return 1;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.0052);
    }
    private static double GenerateIntelligence(LivingEntity data){
        if(data instanceof Player){
            return 1;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.0002);
    }
    private static double GenerateLife(LivingEntity data){
        if(data instanceof Player){
            return 20;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.035) + data.getMaxHealth() + data.getAbsorptionAmount();
    }
    private static double GenerateLifeRegeneration(LivingEntity data){
        if(data instanceof Player){
            return 1;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.00002);
    }
    private static double GenerateMana(LivingEntity data){
        if(data instanceof Player){
            return 10;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.065);
    }
    private static double GenerateManaRegeneration(LivingEntity data){
        if(data instanceof Player){
            return 1;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.0000856);
    }
    private static double GenerateStrength(LivingEntity data){
        if(data instanceof Player){
            return 1;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).isPresent() ? data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1) : 0;
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).isPresent() ? data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1): 0;
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))));
        return res + bonusRank*(bonusLevel*0.0002);
    }
    private static double GenerateExperienceReward(LivingEntity data){
        if(data instanceof Player){
            return 0;
        }
        int bonusLevel = data.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1);
        int bonusRank = data.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1);
        int exp = data.getExperienceReward();
        double extra = bonusLevel*0.01;
        if(extra < 1){
            extra = extra + 1;
        }
        double res = 1 * ((extra+1)*random.nextDouble(random.nextDouble(random.nextDouble(20))))+exp;
        if(res < 0){
            res = 1;
        }
        return res + bonusRank*(bonusLevel*0.0002);
    }
}