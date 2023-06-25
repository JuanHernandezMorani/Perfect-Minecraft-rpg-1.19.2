package net.cheto97.rpgcraftmod.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.customstats.*;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.modsystem.Customlevel;
import net.cheto97.rpgcraftmod.modsystem.Experience;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.*;
import net.cheto97.rpgcraftmod.providers.*;
import net.cheto97.rpgcraftmod.util.ExperienceReward;
import net.cheto97.rpgcraftmod.villager.ModVillagers;
import static net.cheto97.rpgcraftmod.ModHud.Elements.vanilla.HudElementViewVanilla.*;
import static net.cheto97.rpgcraftmod.ranks.Boss.bossModify;
import static net.cheto97.rpgcraftmod.ranks.Brutal.brutalModify;
import static net.cheto97.rpgcraftmod.ranks.Champion.championModify;
import static net.cheto97.rpgcraftmod.ranks.Demon.demonModify;
import static net.cheto97.rpgcraftmod.ranks.Elite.eliteModify;
import static net.cheto97.rpgcraftmod.ranks.Hero.heroModify;
import static net.cheto97.rpgcraftmod.ranks.Legendary.legendaryModify;
import static net.cheto97.rpgcraftmod.ranks.Mythical.mythicalModify;
import static net.cheto97.rpgcraftmod.ranks.Unique.uniqueModify;
import static net.cheto97.rpgcraftmod.ranks.semiBoss.semiBossModify;
import static net.cheto97.rpgcraftmod.util.CriticalDamageModifierCalculator.calculateCriticalDamageModifier;
import static net.cheto97.rpgcraftmod.util.CriticalHitCalculator.calculateCriticalHit;
import static net.cheto97.rpgcraftmod.util.NumberUtils.randomInt;
import static net.cheto97.rpgcraftmod.util.RPGutil.setBonusValue;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.List;

@Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID)
public class ModEvents {
    private static final String MESSAGE_LEVEL_TOO_HIGH = "message.rpgcraftmod.level_too_high";
    private static int lvls = 0;
    private static int lvlReduce = 0;
    static private double expBonus = 1;
    static private double totalDamage;
    private static void updatePlayerCapabilities(ServerPlayer player) {
        player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(playerLevel -> player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(experience -> ModMessages.sendToPlayer(new PlayerSyncPacket(player),player)))))))))))))));

    }
    private static void updateMobCapabilities(LivingEntity livingEntity) {
        livingEntity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(customLevel -> livingEntity.getCapability(RankProvider.ENTITY_RANK).ifPresent(rank -> livingEntity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> livingEntity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> livingEntity.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> livingEntity.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> livingEntity.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> livingEntity.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> livingEntity.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> livingEntity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> livingEntity.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> livingEntity.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> livingEntity.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> livingEntity.getCapability(ExperienceRewardProvider.ENTITY_EXPERIENCE_REWARD).ifPresent(experienceReward -> ModMessages.sendToTracking(new EntitySyncPacket(livingEntity),livingEntity)))))))))))))));
    }
    private static int getRandomRank(){
        double[] RANK_PERCENTAGES = {0.4, 0.25, 0.15, 0.09, 0.05, 0.03, 0.02, 0.01, 0.005, 0.0005, 0.00005};
        final int MAX_RANK = 11;
        Random random = new Random();
        double totalPercentage = 0.0;
        double randomNumber = random.nextDouble();

        for (int rank = 1; rank <= MAX_RANK; rank++) {
            totalPercentage += RANK_PERCENTAGES[rank - 1];
            if (randomNumber <= totalPercentage) {
                return rank;
            }
        }
        return MAX_RANK;
    }
    private static String formatDouble(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.###", symbols);
        return decimalFormat.format(value);
    }

    private static void levelUpPlayer(ServerPlayer player,double lifeIncrease,double manaIncrease,
                                double lifeRegenerationIncrease,double manaRegenerationIncrease,
                                double defenseIncrease,double magicDefenseIncrease,double intelligenceIncrease,
                                double dexterityIncrease, double strengthIncrease, double agilityIncrease,
                                double commandIncrease, double luckIncrease){
        player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> {
            life.increaseMax(lifeIncrease);
            life.set(life.getMax());
            mana.increaseMax(manaIncrease);
            mana.set(mana.getMax());
            lifeRegeneration.add(lifeRegenerationIncrease);
            manaRegeneration.add(manaRegenerationIncrease);
            defense.add(defenseIncrease);
            magicDefense.add(magicDefenseIncrease);
            intelligence.add(intelligenceIncrease);
            dexterity.add(dexterityIncrease);
            strength.add(strengthIncrease);
            agility.add(agilityIncrease);
            command.add(commandIncrease);
            luck.add(luckIncrease);
            updatePlayerCapabilities(player);
        }))))))))))));
    }

    private static void levelUpMob(LivingEntity entity, int multiplier,int rank){
        entity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> entity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> entity.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> entity.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> entity.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> entity.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> entity.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> entity.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> entity.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> entity.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> entity.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> {
            double MIN_VALUE;
            double MAX_VALUE;

            Random random = new Random();
            switch (rank){
                case 6 ->{
                    MIN_VALUE = 0.0096;
                    MAX_VALUE = 1.56;
                }
                case 7 ->{
                    MIN_VALUE = 0.032;
                    MAX_VALUE = 1.75;
                }
                case 8 ->{
                    MIN_VALUE = 0.075;
                    MAX_VALUE = 2;
                }
                case 9 ->{
                    MIN_VALUE = 0.1;
                    MAX_VALUE = 5;
                }
                case 10 ->{
                    MIN_VALUE = 0.5;
                    MAX_VALUE = 10;
                }
                case 11 ->{
                    MIN_VALUE = 1;
                    MAX_VALUE = 20;
                }
                default -> {
                    MIN_VALUE = 0.00033;
                    MAX_VALUE = 1.5;
                }
            }

            life.increaseMax(multiplier*(MIN_VALUE + (MAX_VALUE - MIN_VALUE)));
            mana.increaseMax(2*(MIN_VALUE + (MAX_VALUE - MIN_VALUE))*multiplier);
            life.set(life.getMax());
            mana.set(mana.getMax());
            lifeRegeneration.add(0.000025);
            manaRegeneration.add(0.0005);
            defense.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
            magicDefense.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
            intelligence.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
            dexterity.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
            strength.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
            agility.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
            command.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
            luck.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
            updateMobCapabilities(entity);
        }))))))))))));
    }
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){
        if(event.getType() == ModVillagers.MAGIC_MASTER.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModBlocks.bloque_mana.get(),2);
            ItemStack stack2 = new ItemStack(ModItems.espada_muerte.get(),1);
            ItemStack stack3 = new ItemStack(ModItems.hacha_zafiro.get(),1);
            ItemStack stack4 = new ItemStack(ModBlocks.bloque_luz_magica.get(),1);
            ItemStack stack5 = new ItemStack(ModItems.BLUEBERRY_SEEDS.get(),12);

            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                    new ItemStack(ModItems.zafiro.get(),15),
                    stack,10,8,0.02F
            ));
            trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                    new ItemStack(ModBlocks.bloque_mineral_zafiro.get(),3),
                    stack2,10,8,0.02F
            ));
            trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                    new ItemStack(ModItems.zafiro_crudo.get(),1),
                    stack3,10,8,0.02F
            ));
            trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                    new ItemStack(ModBlocks.bloque_profundo_mineral_zafiro.get(),50),
                    stack4,10,8,0.02F
            ));
            trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                    new ItemStack(ModBlocks.bloque_mana.get(),1),
                    stack5,10,8,0.02F
            ));
        }
    }
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(Customlevel.class);
        event.register(Experience.class);
        event.register(Mana.class);
        event.register(ManaRegeneration.class);
        event.register(Life.class);
        event.register(LifeRegeneration.class);
        event.register(Agility.class);
        event.register(Defense.class);
        event.register(Luck.class);
        event.register(Strength.class);
        event.register(Command.class);
        event.register(Dexterity.class);
        event.register(Intelligence.class);
        event.register(Rank.class);
        event.register(MagicDefense.class);
        event.register(ExperienceReward.class);
    }
    @SubscribeEvent
    public static void onHitCriticalChance(CriticalHitEvent event){
        LivingEntity entity = event.getEntity();
        if(entity != null){
            if(entity.getCapability(LuckProvider.ENTITY_LUCK).isPresent())
                entity.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> entity.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> {
                    event.setDamageModifier(calculateCriticalDamageModifier(agility.get()));
                    boolean flag = calculateCriticalHit(luck.get());
                    if (flag) {
                        event.setResult(Event.Result.ALLOW);
                    } else {
                        event.setResult(Event.Result.DENY);
                    }
                }));
            else{
                event.setResult(Event.Result.DENY);
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerPickUpItem(EntityItemPickupEvent event){
        // create logic for player pick up notification
    }
    @SubscribeEvent
    public static void onAttachCapabilityEntity(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() != null){
            Entity entity = event.getObject();
            if(entity instanceof LivingEntity livingEntity) {
                if (livingEntity instanceof Player player) {

                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "experience"), new ExperienceProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "level"), new CustomLevelProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "command"), new CommandProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "agility"), new AgilityProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "defense"), new DefenseProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "dexterity"), new DexterityProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "intelligence"), new IntelligenceProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "life"), new LifeProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "liferegeneration"), new LifeRegenerationProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "magicdefense"), new MagicDefenseProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "mana"), new ManaProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "manaregeneration"), new ManaRegenerationProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "strength"), new StrengthProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "luck"), new LuckProvider());

                    updatePlayerCapabilities((ServerPlayer)player);

                } else {
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "rank"), new RankProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "level"), new CustomLevelProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "command"), new CommandProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "agility"), new AgilityProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "defense"), new DefenseProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "dexterity"), new DexterityProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "intelligence"), new IntelligenceProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "life"), new LifeProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "liferegeneration"), new LifeRegenerationProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "magicdefense"), new MagicDefenseProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "mana"), new ManaProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "manaregeneration"), new ManaRegenerationProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "strength"), new StrengthProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "luck"), new LuckProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"experiencereward"), new ExperienceRewardProvider());

                    updateMobCapabilities(livingEntity);
                }
            }
        }

    }
    private static void setCapabilities(LivingEntity livingEntity){
        int entityRank;
        Level level = livingEntity.getLevel();
        int entityClass;
        long levelTime = level.getLevelData().getDayTime();
        int timeLevel = (int) (levelTime / 24000) / 5;
        double naturalSpawnX = level.getSharedSpawnPos().getX();
        double naturalSpawnZ = level.getSharedSpawnPos().getZ();
        int distanceLevel = (int) (Math.abs(livingEntity.getX() - naturalSpawnX) + Math.abs(livingEntity.getZ() - naturalSpawnZ)) / 100;
        Difficulty difficulty = level.getDifficulty();
        int difficultyLevel = switch (difficulty) {
            case PEACEFUL -> 1;
            case EASY -> 2;
            case NORMAL -> 4;
            case HARD -> 12;
        };
        int connectedPlayers = 1;
        if (level.players().size() != 0) {
            connectedPlayers = level.players().size();
        }

        if (livingEntity.getType() == EntityType.ENDER_DRAGON) {
            entityRank = 11;
            double bonus = setBonusValue(entityRank);
            entityClass = 100 * (int) (11 + difficultyLevel + bonus);
        } else if (livingEntity.getType() == EntityType.WITHER) {
            entityRank = 11;
            double bonus = setBonusValue(entityRank);
            entityClass = 50 * (int) (11 + difficultyLevel + bonus);
        } else {
            entityRank = getRandomRank();
            double bonus = setBonusValue(entityRank);
            if (entityRank < 10) {
                entityClass = (int) (entityRank + difficultyLevel + bonus);
            } else {
                entityClass = 25 * (int) (entityRank + difficultyLevel + bonus);
            }

        }

        int dimensionLevel = 0;
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.dimension().equals(Level.NETHER)) {
                dimensionLevel = difficultyLevel * 50;
            } else if (serverLevel.dimension().equals(Level.END)) {
                dimensionLevel = difficultyLevel * 100;
            }
        }

        int hardcoreLevel = level.getLevelData().isHardcore() ? 5 : 0;

        int totalCustomLevels = level.players().stream()
                .mapToInt(player -> {
                    player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(playerLevel -> {
                        if (playerLevel.get() != 0) {
                            lvls = playerLevel.get();
                        }
                    });
                    return lvls;
                })
                .sum();
        int averageCustomLevel = totalCustomLevels / connectedPlayers;

        int finalLevel = entityClass + (int) Math.floor(distanceLevel + difficultyLevel + timeLevel + connectedPlayers + dimensionLevel + hardcoreLevel + (float) (averageCustomLevel / 10));

        if (timeLevel < 2) {
            lvlReduce = finalLevel;
        }
        Random random = new Random();
        int fnLvl = (finalLevel - lvlReduce) != 0 ? (finalLevel + entityRank * 5) : random.nextInt(20)*2+random.nextInt(8)*3;

        livingEntity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(customLevel -> {
            customLevel.setLevel(fnLvl);
            livingEntity.getCapability(RankProvider.ENTITY_RANK).ifPresent(rank -> {
                rank.set(entityRank);
                levelUpMob(livingEntity,customLevel.get(),rank.get());
                switch (entityRank){
                    case 2 -> eliteModify(livingEntity);
                    case 3 -> brutalModify(livingEntity);
                    case 4 -> championModify(livingEntity);
                    case 5 -> heroModify(livingEntity);
                    case 6 -> demonModify(livingEntity);
                    case 7 -> legendaryModify(livingEntity);
                    case 8 -> mythicalModify(livingEntity);
                    case 9 -> uniqueModify(livingEntity);
                    case 10 -> semiBossModify(livingEntity);
                    case 11 -> bossModify(livingEntity);
                }
            });
        });
    }
    public static boolean setChance(int numero) {
        if (numero == 11) {
            return true;
        } else {
            int probabilidad = 11 - numero;
            int random = (int) (Math.random() * 10) + 1;
            return random <= probabilidad;
        }
    }
    public static MobEffect[] selectEffects(int amount){
        Random random = new Random();
        MobEffect[] effects = {
                MobEffects.WITHER,
                MobEffects.POISON,
                MobEffects.WEAKNESS,
                MobEffects.HUNGER,
                MobEffects.DIG_SLOWDOWN,
                MobEffects.DARKNESS,
                MobEffects.CONFUSION,
                MobEffects.BLINDNESS,
                MobEffects.GLOWING,
                MobEffects.LEVITATION,
                MobEffects.UNLUCK,
                MobEffects.MOVEMENT_SLOWDOWN
        };
        MobEffect[] result = new MobEffect[amount];

        for (int i = 0; i < amount; i++) {
            MobEffect res;
            boolean alreadyExist;

            do {
                res = effects[random.nextInt(effects.length)];
                alreadyExist = false;

                for (int j = 0; j < i; j++) {
                    if (result[j] == res) {
                        alreadyExist = true;
                        break;
                    }
                }
            } while (alreadyExist);

            result[i] = res;
        }


        return result;
    }
    public static int[] applyDuration(int amount){
        int[] durations = new int[amount];

        Random random = new Random();

        for (int i = 0; i < amount; i++) {
            int duration = random.nextInt(26) + 5;
            durations[i] = duration;
        }

        return durations;
    }
    public static int[] applyLevel(int amount){
        int[] levels = new int[amount];

        Random random = new Random();

        for(int i = 0; i < amount; i++){
            int level = random.nextInt(10)+1;
            levels[i] = level;
        }

        return levels;
    }
    public static int setAmount(int rank) {
        Random random = new Random();

        int amount = 0;

        double maxRank = 12.0;
        double rankFactor = (maxRank - rank) / maxRank;

        double[] probabilities = new double[12];
        for (int i = 0; i < 12; i++) {
            double baseProbability = (double) (i + 1) / (11);
            probabilities[i] = baseProbability * rankFactor;
        }

        double totalProbability = 0.0;
        for (double probability : probabilities) {
            totalProbability += probability;
        }

        double randomNumber = random.nextDouble() * totalProbability;

        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomNumber < cumulativeProbability) {
                amount = i;
                break;
            }
        }

        return amount;
    }
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if(!event.getEntity().getLevel().isClientSide() && event.getEntity() != null){
            event.getOriginal().getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(oldStore -> event.getEntity().getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(oldStore -> event.getEntity().getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(ManaProvider.ENTITY_MANA).ifPresent(oldStore -> event.getEntity().getCapability(ManaProvider.ENTITY_MANA).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(oldStore -> event.getEntity().getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(LifeProvider.ENTITY_LIFE).ifPresent(oldStore -> event.getEntity().getCapability(LifeProvider.ENTITY_LIFE).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(oldStore -> event.getEntity().getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(LuckProvider.ENTITY_LUCK).ifPresent(oldStore -> event.getEntity().getCapability(LuckProvider.ENTITY_LUCK).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(oldStore -> event.getEntity().getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(oldStore -> event.getEntity().getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(oldStore -> event.getEntity().getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(oldStore -> event.getEntity().getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(oldStore -> event.getEntity().getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(oldStore -> event.getEntity().getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(newStore -> newStore.copyFrom(oldStore)));

            event.getOriginal().getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(oldStore -> event.getEntity().getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(newStore -> newStore.copyFrom(oldStore)));
        }
    }
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event){
        if(!event.getEntity().getLevel().isClientSide() && event.getEntity() != null) {
            Entity attacker = event.getSource().getEntity();
            LivingEntity target = event.getEntity();
            double damage = event.getAmount();
            event.setAmount(0.0f);
            DamageSource source = event.getSource();
            if (target != null) {
                if (attacker instanceof LivingEntity livingAttacker) {
                    int attackerLevel = livingAttacker.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1);
                    int targetLevel = target.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1);
                    double damageReduce = 0;

                    if (attackerLevel > targetLevel && livingAttacker instanceof Player && target instanceof Player) {
                        damageReduce = attackerLevel * 0.5 > targetLevel ? livingAttacker.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(500.0) : 0;
                    }

                    double armor = target.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(1.0);
                    double percentDamageReduce = (armor + damageReduce) / (damage + armor + damageReduce);
                    double magicArmor = target.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).map(MagicDefense::get).orElse(1.0);
                    double percentMagicDamageReduce = (magicArmor + damageReduce) / (damage + magicArmor + damageReduce);

                    if (source.isMagic() || source.isFire()) {
                        double attackerMagicDamage = livingAttacker.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).map(Intelligence::get).orElse(1.0);
                        damage = (((damage + attackerMagicDamage) * percentMagicDamageReduce));
                    } else if (source.isProjectile()) {
                        double attackerRangeDamage = livingAttacker.getCapability(DexterityProvider.ENTITY_DEXTERITY).map(Dexterity::get).orElse(1.0);
                        damage = ((damage + attackerRangeDamage) * percentDamageReduce);
                    } else if (source.isExplosion()) {
                        double attackerAgility = livingAttacker.getCapability(AgilityProvider.ENTITY_AGILITY).map(Agility::get).orElse(1.0);
                        damage = ((damage * 2 + attackerAgility) * ((percentDamageReduce * 0.25) + (percentMagicDamageReduce * 0.25)));
                    } else if (source.isCreativePlayer()) {
                        damage = 0;
                    } else {
                        double attackerStrength = livingAttacker.getCapability(StrengthProvider.ENTITY_STRENGTH).map(Strength::get).orElse(1.0);
                        damage = ((damage + attackerStrength) * percentDamageReduce);
                    }
                    if (livingAttacker instanceof Player && target instanceof Player) {
                        totalDamage = attackerLevel * 0.2 > targetLevel ? -111111 : damage - damageReduce;
                    } else {
                        totalDamage = damage;
                    }

                    target.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> {
                        if (totalDamage == -111111) {
                            livingAttacker.sendSystemMessage(Component.literal("Your level is higher than " + target.getName().getString() + ", setting damage to 0").withStyle(ChatFormatting.DARK_RED));
                        } else {
                            if (livingAttacker.getCapability(RankProvider.ENTITY_RANK).isPresent()) {
                                int rank = livingAttacker.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(1);
                                int amount = setAmount(rank);
                                boolean shouldApply = setChance(rank);

                                if(shouldApply){
                                    MobEffect[] effects = selectEffects(amount);
                                    int[] durations = applyDuration(amount);
                                    int[] levels = applyLevel(amount);

                                    for (int i = 0; i < amount; i++) {
                                        target.addEffect(new MobEffectInstance(effects[i], durations[i], levels[i]));
                                    }
                                }

                            } else {
                                livingAttacker.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(customlevel -> {
                                    int amount = 0;
                                    int extraAmount = 0;
                                    int count = customlevel.get();
                                    if (count >= 10000) {
                                        do {
                                            count = count - 10000;
                                            amount++;
                                        } while (count > 10000 && amount < 10);
                                    }

                                    Random random = new Random();
                                    int value = random.nextInt(3);
                                    if(value > 0){
                                        extraAmount = setAmount(value);
                                    }


                                    amount = amount + extraAmount;

                                            boolean shouldApply = setChance(random.nextInt(12)+1);

                                            if(shouldApply && amount > 0) {
                                                MobEffect[] effects = selectEffects(amount);
                                                int[] durations = applyDuration(amount);
                                                int[] levels = applyLevel(amount);

                                                for (int i = 0; i < amount; i++) {
                                                    target.addEffect(new MobEffectInstance(effects[i], durations[i], levels[i]));
                                                }
                                            }
                                });
                            }
                            if(totalDamage != -111111 && totalDamage < 1){
                                totalDamage = 1;
                            }
                            life.consumeLife(totalDamage);
                            reduceArmorDurability(target, totalDamage);
                        }
                    });
                } else {
                    double defense = target.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(1.0);
                    double magicDefense = target.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).map(MagicDefense::get).orElse(1.0);
                    totalDamage = (defense / (damage + defense)) + (magicDefense / (damage + magicDefense));
                    target.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> {
                        if(totalDamage != -111111 && totalDamage < 1){
                            totalDamage = 1;
                        }
                        life.consumeLife(totalDamage);
                        reduceArmorDurability(target, totalDamage);
                    });
                }
            }
        }
    }
    private static void reduceArmorDurability(LivingEntity entity, double damage) {
        for (ItemStack armorStack : entity.getArmorSlots()) {
            if (!armorStack.isEmpty()) {
                Item armorItem = armorStack.getItem();
                if (armorItem instanceof ArmorItem) {
                    int maxDurability = armorStack.getMaxDamage();
                    int currentDurability = armorStack.getDamageValue();
                    int newDurability = currentDurability - (int)(Math.ceil(damage)*0.0025);
                    if (newDurability >= maxDurability) {
                        entity.broadcastBreakEvent(EquipmentSlot.HEAD);
                        entity.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        entity.broadcastBreakEvent(EquipmentSlot.CHEST);
                        entity.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                        entity.broadcastBreakEvent(EquipmentSlot.LEGS);
                        entity.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
                        entity.broadcastBreakEvent(EquipmentSlot.FEET);
                        entity.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
                    } else {
                        armorStack.setDamageValue(newDurability);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onLivingHealEvent(LivingHealEvent event){
        if(event.getEntity() != null&& !event.getEntity().getLevel().isClientSide()){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onLivingEntityUpdate(LivingEvent.LivingTickEvent event){
            LivingEntity livingEntity = event.getEntity();
            if (livingEntity != null) {
                if(livingEntity instanceof ServerPlayer player){
                        player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> {
                            if (life.get() <= 0) {
                                player.setHealth(0.0f);
                                player.die(DamageSource.GENERIC);
                            }
                            if (life.get() < life.getMax() && life.get() > 0) {
                                life.add(lifeRegeneration.get() * 0.025);
                            }
                            if (mana.get() < mana.getMax()) {
                                mana.add(manaRegeneration.get() * 0.04);
                            }
                            updatePlayerCapabilities(player);
                    }))));
                }
                else{
                    if(livingEntity instanceof Animal || livingEntity instanceof Monster || livingEntity instanceof WaterAnimal || livingEntity instanceof Villager){
                        livingEntity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(customLevel -> livingEntity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> livingEntity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> livingEntity.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> livingEntity.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                            if (customLevel.hasEnteredLevel()) {
                                customLevel.setEnteredWorld();
                                setCapabilities(livingEntity);
                            }
                            if (life.get() <= 0) {
                                livingEntity.setHealth(0.0f);
                                livingEntity.die(DamageSource.GENERIC);
                            }
                            if (life.get() < life.getMax() && livingEntity.getHealth() > 0.1f) {
                                life.add(lifeRegeneration.get() * 0.025);
                            }
                            if (mana.get() < mana.getMax()) {
                                mana.add(manaRegeneration.get() * 0.04);
                            }
                            ////////////////////////////////////////////////////////////////////////////////////////////////////
                            if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.isClientSide() && Minecraft.getInstance().getConnection() != null) {
                                int id = dataNeeded();
                                if (id == livingEntity.getId()) {
                                    livingEntity.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> livingEntity.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> livingEntity.getCapability(RankProvider.ENTITY_RANK).ifPresent(rank1 -> setData(life.get(), life.getMax(), mana.get(), mana.getMax(), (defense.get()) + magicDefense.get(), rank1.get(), customLevel.get()))));
                            }
                        }
                            updateMobCapabilities(livingEntity);
                    })))));
                    }
            }
        }
    }
    @SubscribeEvent
    public static void onLivingExperienceDrop(LivingExperienceDropEvent event){
        if(!event.getEntity().getLevel().isClientSide() && event.getEntity() != null && event.getAttackingPlayer() != null) {
            LivingEntity target = event.getEntity();
            Player entity = event.getAttackingPlayer();

            if (!target.shouldDropExperience()) {
                expBonus = target.getCapability(ExperienceRewardProvider.ENTITY_EXPERIENCE_REWARD).map(ExperienceReward::get).orElse(1.0);
            }

            if (!target.getLevel().isClientSide() && entity != null) {
                entity.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(customExp -> entity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(playerLevel -> target.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(targetLevel -> {
                    Difficulty difficulty = event.getEntity().getLevel().getDifficulty();
                    double hardcoreXP = event.getEntity().getLevel().getLevelData().isHardcore() ? 3.5 : 1;

                    double difficultyXP = switch (difficulty) {
                        case PEACEFUL -> 1;
                        case EASY -> 2.3;
                        case NORMAL -> 6.5;
                        case HARD -> 12.5;
                    };
                    double droppedExperience = (event.getOriginalExperience() * difficultyXP) * hardcoreXP;
                    double playerLevelValue = playerLevel.get();
                    double targetLevelValue = targetLevel.get();
                    double multiplier = 0.0;

                    if (playerLevelValue < targetLevelValue) {
                        multiplier = 1 + ((targetLevelValue - playerLevelValue) / 100.0);
                    } else if (playerLevelValue - (playerLevelValue * 0.05) <= targetLevelValue && playerLevelValue - (playerLevelValue * 0.1) > targetLevelValue) {
                        multiplier = 1.0;
                    } else if (playerLevelValue - (playerLevelValue * 0.1) <= targetLevelValue && playerLevelValue - (playerLevelValue * 0.25) > targetLevelValue) {
                        multiplier = 0.75;
                    } else if (playerLevelValue - (playerLevelValue * 0.25) <= targetLevelValue && playerLevelValue - (playerLevelValue * 0.45) > targetLevelValue) {
                        multiplier = 0.4;
                    }

                    double exp = (droppedExperience + expBonus) * targetLevelValue;
                    event.setDroppedExperience((int) (exp * multiplier));
                    if (multiplier == 0.0) {
                        entity.sendSystemMessage(Component.translatable(MESSAGE_LEVEL_TOO_HIGH).withStyle(ChatFormatting.DARK_RED));
                    }
                })));
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerGetExperience(PlayerXpEvent.PickupXp event) {
        if (!event.getEntity().getLevel().isClientSide() && event.getEntity() != null && event.getOrb() != null) {
            int xp = event.getOrb().getValue();
            Player player = event.getEntity();

            player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(level -> player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(experience -> {
                experience.add(xp);
                if (experience.get() >= level.experienceNeeded()) {
                    boolean flag = true;
                    int count = 0;
                    do {
                        experience.consume(level.experienceNeeded());
                        count++;
                        level.add(count);
                        int opt = randomInt(6,0);
                        switch (opt){
                            case 1 -> {
                                levelUpPlayer((ServerPlayer) player, 1, 1, 0.00025, 0.0005, 2, 2, 1, 6, 0, 8, 8, 8);
                                player.sendSystemMessage(Component.literal("You have level up, and receive Arches bless"));
                            }

                            case 2 -> {
                                levelUpPlayer((ServerPlayer) player, 8, 0, 0.00045, 0.0, 8, 0, 0, 1, 7, 3, 0, 1);
                                player.sendSystemMessage(Component.literal("You have level up, and receive Warriors bless"));
                            }

                            case 3 -> {
                                levelUpPlayer((ServerPlayer) player, 2.5, 8, 0.00025, 0.005, 2, 7, 8, 2, 0, 0, 3, 3);
                                player.sendSystemMessage(Component.literal("You have level up, and receive Mages bless"));
                            }

                            case 4 -> {
                                levelUpPlayer((ServerPlayer) player, 10, 50, 0.0025, 0.005, 4.45, 7.55, 8, 8, 12, 7, 14, 9);
                                player.sendSystemMessage(Component.literal("You have level up, and receive Gods bless"));
                            }

                            default -> {
                                levelUpPlayer((ServerPlayer) player, 0.5, 1, 0.00025, 0.0005, 1, 1, 1, 5, 1, 1, 1, 5);
                                player.sendSystemMessage(Component.literal("You have level up."));
                            }
                        }
                        //play sound event level_up
                        if (experience.get() < level.experienceNeeded()) {
                            flag = false;
                        }
                    } while (flag);

                }
            }));
            player.sendSystemMessage(Component.literal("Obtained experience: " + formatDouble(xp)));
        }
    }
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity() != null) {
            if (event.getEntity() instanceof ServerPlayer player) {
                updatePlayerCapabilities(player);
            }
        }
    }
}
