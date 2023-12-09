package net.cheto97.rpgcraftmod.event;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.customstats.*;
import net.cheto97.rpgcraftmod.entity.ModEntityTypes;
import net.cheto97.rpgcraftmod.entity.custom.*;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.menu.PlayerClassSelectMenu;
import net.cheto97.rpgcraftmod.modSounds.ModSoundsRPG;
import net.cheto97.rpgcraftmod.modsystem.*;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.S2C.PlayerSyncPacket;
import net.cheto97.rpgcraftmod.providers.*;
import net.cheto97.rpgcraftmod.util.ExperienceReward;
import net.cheto97.rpgcraftmod.util.levelConfig.utils.ConfigSyncing;
import net.cheto97.rpgcraftmod.villager.ModVillagers;
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
import static net.cheto97.rpgcraftmod.util.AuraDropChances.generateRandomValue;
import static net.cheto97.rpgcraftmod.util.AuraDropChances.willDropAura;
import static net.cheto97.rpgcraftmod.util.CriticalDamageModifierCalculator.calculateCriticalDamageModifier;
import static net.cheto97.rpgcraftmod.util.CriticalHitCalculator.calculateCriticalHit;
import static net.cheto97.rpgcraftmod.util.Effects.Helper.*;
import static net.cheto97.rpgcraftmod.util.NumberUtils.*;
import static net.cheto97.rpgcraftmod.util.RPGutil.setBonusValue;
import static net.cheto97.rpgcraftmod.item.ModItems.*;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.List;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID)
   public static class ForgeEvents{
       private static final String MESSAGE_LEVEL_TOO_HIGH = "message.rpgcraftmod.level_too_high";
       private static int lvls = 0;
       private static int result = 0;
       private static int lvlReduce = 0;
       static private final int expBonus = 1;
       static private double totalDamage;
       static private double regEffectBonus = 0.0;
       static private boolean sendMSG = true;
       private static void updatePlayerCapabilities(ServerPlayer player) {
           ModMessages.sendToPlayer(new PlayerSyncPacket(player),player);
       }
       private static int getRandomRank(LivingEntity entity) {
           double[] RANK_PERCENTAGES = {0.4, 0.25, 0.15, 0.094, 0.055, 0.033, 0.022, 0.013, 0.01, 0.005, 0.0005};
           final int MAX_RANK = 11;
           Random random = new Random();
           double totalPercentage = 0.0;
           double randomNumber = random.nextDouble();

           double[] probabilitiesByDimension = getProbabilitiesByDimension(entity);

           for (int rank = 1; rank <= MAX_RANK; rank++) {
               totalPercentage += ((RANK_PERCENTAGES[rank] + 1) * (probabilitiesByDimension[rank] + 1)) - 1;
               if (randomNumber <= totalPercentage) {
                   return rank;
               }
           }
           return 1;
       }

       private static double[] getProbabilitiesByDimension(LivingEntity entity) {
           double[] baseProbabilities = {0.25, 0.025, 0.25, 0.025, 0.12, 0.012, 0.12, 0.007, 0.07, 0.07, 0.05};
           Level level = entity.getLevel();

           if (level instanceof ServerLevel serverLevel) {
               if (serverLevel.dimension().equals(Level.OVERWORLD)) {
                   return new double[]{0.4, 0.3, 0.2, 0.1, 0.01, 0.07, 0.007, 0.005, 0.005, 0.005, 0.003};
               }
               else if (serverLevel.dimension().equals(Level.NETHER)) {
                   return new double[]{0.001, 0.001, 0.002, 0.002, 0.15, 0.15, 0.1, 0.07, 0.007, 0.005, 0.003};
               }
               else if (serverLevel.dimension().equals(Level.END)) {
                   return new double[]{0.0003, 0.005, 0.007, 0.007, 0.01, 0.01, 0.15, 0.15, 0.2, 0.2, 0.01};
               }
           }
           return baseProbabilities;
       }

       private static int dropExperience(LivingEntity target, Player entity,int originalExp){

           entity.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(customExp -> entity.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> entity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(playerLevel -> target.getCapability(RankProvider.ENTITY_RANK).ifPresent(rank -> target.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(targetLevel -> {
               Difficulty difficulty = target.getLevel().getDifficulty();

               MobEffectInstance luckEff = entity.hasEffect(MobEffects.LUCK) ? entity.getEffect(MobEffects.LUCK) : null;
               double luckBonus = luckEff != null ? calculateValue(luckEff, luck.get(),"add") : 0.0;

               double rankMultiplier = randomValueWithConstraints(randomDoubleWithLuck(randomDouble(9.65,4.26),randomDouble(4.25,1.000001) ,luck.get()+luckBonus),rank.get());

               double hardcoreXP = target.getLevel().getLevelData().isHardcore() ? 3.5 : 1;

               double difficultyXP = switch (difficulty) {
                   case PEACEFUL -> 1;
                   case EASY -> 2.3;
                   case NORMAL -> 6.5;
                   case HARD -> 12.5;
               };
               double droppedExperience = (originalExp * difficultyXP) * hardcoreXP;
               double playerLevelValue = playerLevel.get();
               double targetLevelValue = targetLevel.get();
               double multiplier = 0.0;

               if(playerLevelValue > 10){
                   if (playerLevelValue < targetLevelValue) {
                       multiplier = 1 + ((targetLevelValue - playerLevelValue) / 100.0);
                   }
                   else if (playerLevelValue - (playerLevelValue * 0.05) <= targetLevelValue && playerLevelValue - (playerLevelValue * 0.1) > targetLevelValue) {
                       multiplier = 1.0;
                   }
                   else if (playerLevelValue - (playerLevelValue * 0.1) <= targetLevelValue && playerLevelValue - (playerLevelValue * 0.25) > targetLevelValue) {
                       multiplier = 0.75;
                   }
                   else if (playerLevelValue - (playerLevelValue * 0.25) <= targetLevelValue && playerLevelValue - (playerLevelValue * 0.45) > targetLevelValue) {
                       multiplier = 0.4;
                   }
               }else{
                   if (playerLevelValue < targetLevelValue && playerLevelValue * 2 <= targetLevelValue) {
                       multiplier = 1 + ((targetLevelValue - playerLevelValue) / 100.0);
                   }else{
                       multiplier = 1.0;
                   }
               }


               double exp = (droppedExperience + expBonus) * targetLevelValue;
               result = (int) (exp * multiplier * rankMultiplier);
               if (multiplier == 0.0) {
                   entity.sendSystemMessage(Component.translatable(MESSAGE_LEVEL_TOO_HIGH).withStyle(ChatFormatting.DARK_RED));
               }
           })))));
           return result;
       }
       private static void levelUpPlayer(ServerPlayer player,double lifeIncrease,double manaIncrease,
                                         double lifeRegenerationIncrease,double manaRegenerationIncrease,
                                         double defenseIncrease,double magicDefenseIncrease,double intelligenceIncrease,
                                         double dexterityIncrease, double strengthIncrease, double agilityIncrease,
                                         double commandIncrease, double luckIncrease){
           player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> player.getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(stats -> player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax ->{
               lifeMax.add(lifeIncrease);
               life.set(lifeMax.get());
               manaMax.add(manaIncrease);
               mana.set(manaMax.get());
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

               int difficultyLevel = switch(player.getLevel().getDifficulty()){
                   case PEACEFUL -> 1;
                   case EASY -> 2;
                   case NORMAL -> 5;
                   case HARD -> 8;
               };
               stats.add(difficultyLevel);
               updatePlayerCapabilities(player);
           })))))))))))))));
       }

       private static void levelUpMob(LivingEntity entity, int multiplier,int rank){
           entity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> entity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> entity.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> entity.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> entity.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> entity.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> entity.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> entity.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> entity.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> entity.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> entity.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> entity.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> entity.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
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

               lifeMax.add(multiplier*(MIN_VALUE + (MAX_VALUE - MIN_VALUE)));
               manaMax.add(2*(MIN_VALUE + (MAX_VALUE - MIN_VALUE))*multiplier);
               life.set(lifeMax.get());
               mana.set(manaMax.get());
               lifeRegeneration.add(0.000025);
               manaRegeneration.add(0.0005);
               defense.add(((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier)*0.20);
               magicDefense.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
               intelligence.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
               dexterity.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
               strength.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
               agility.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
               command.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
               luck.add((MIN_VALUE + (MAX_VALUE - MIN_VALUE) * random.nextDouble())*multiplier);
           }))))))))))))));
       }
       @SubscribeEvent
       public static void addCustomTrades(VillagerTradesEvent event){
           if(event.getType() == ModVillagers.MAGIC_MASTER.get()){
               Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
               ItemStack stack = new ItemStack(ModBlocks.bloque_mana.get(),1);
               ItemStack stack2 = new ItemStack(epicsword.get(),1);
               ItemStack stack3 = new ItemStack(hacha_zafiro.get(),1);
               ItemStack stack4 = new ItemStack(ModBlocks.bloque_luz_magica.get(),1);
               ItemStack stack5 = new ItemStack(BLUEBERRY_SEEDS.get(),12);
               ItemStack stack6 = new ItemStack(ModBlocks.bloque_mana.get(),8);
               ItemStack costo = new ItemStack(ModBlocks.bloque_zafiro.get(),7);

               HashMap<Integer,ItemStack> wings = new HashMap<>();

               fillMap(wings);

               int villagerLevel = 1;

               trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                       new ItemStack(ModItems.zafiro.get(),15),
                       stack,10,Integer.MAX_VALUE,0.02F
               ));
               trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                       new ItemStack(ModBlocks.bloque_mineral_zafiro.get(),3),
                       stack2,10,8,0.02F
               ));
               trades.get(2).add((trader,rand) -> new MerchantOffer(
                       new ItemStack(ModItems.zafiro_crudo.get(),1),
                       stack3,10,8,0.02F
               ));
               trades.get(2).add((trader,rand) -> new MerchantOffer(
                       new ItemStack(ModBlocks.bloque_profundo_mineral_zafiro.get(),50),
                       stack4,10,8,0.02F
               ));
               trades.get(3).add((trader,rand) -> new MerchantOffer(
                       new ItemStack(ModBlocks.bloque_mana.get(),1),
                       stack5,10,8,0.02F
               ));
               trades.get(3).add((trader,rand) -> new MerchantOffer(
                       stack6,
                       new ItemStack(ModBlocks.GEM_INFUSING_STATION.get(),1),10,8,0.02F
               ));
               for (Map.Entry<Integer, ItemStack> entry : wings.entrySet()) {
                   ItemStack item = entry.getValue();
                   for (int i = 0; i < 17; i++) {
                       trades.get(4).add((trader, rand) -> new MerchantOffer(
                               costo, item, 10, 2, 0.02F
                       ));
                   }
               }
           }
       }
       private static void fillMap(HashMap<Integer, ItemStack> wingsMap) {
           wingsMap.put(0, WHITE_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(1, ORANGE_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(2, MAGENTA_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(3, LIGHT_BLUE_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(4, YELLOW_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(5, LIME_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(6, PINK_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(7, GREY_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(8, LIGHT_GREY_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(9, CYAN_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(10, PURPLE_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(11, BLUE_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(12, BROWN_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(13, GREEN_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(14, RED_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(15, BLACK_LIGHT_WINGS.get().getDefaultInstance());
           wingsMap.put(16, ZANZAS_WINGS.get().getDefaultInstance());
       }
       @SubscribeEvent
       public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
           event.register(Customlevel.class);
           event.register(Experience.class);
           event.register(Mana.class);
           event.register(ManaMax.class);
           event.register(ManaRegeneration.class);
           event.register(Life.class);
           event.register(LifeMax.class);
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
           event.register(StatPoint.class);
           event.register(FirstJoin.class);
           event.register(CustomClass.class);
           event.register(RegenerationDelay.class);
           event.register(Reset.class);
       }
       @SubscribeEvent
       public static void onHitCriticalChance(CriticalHitEvent event){
           LivingEntity entity = event.getEntity();
           if(entity != null){
               if(entity.getCapability(LuckProvider.ENTITY_LUCK).isPresent())
                   entity.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> entity.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> {

                       MobEffectInstance luckEff = entity.hasEffect(MobEffects.LUCK) ? entity.getEffect(MobEffects.LUCK) : null;
                       double luckBonus = luckEff != null ? calculateValue(luckEff, luck.get(),"add") : 0.0;

                       event.setDamageModifier(calculateCriticalDamageModifier(agility.get()));
                       boolean flag = calculateCriticalHit(luck.get()+luckBonus);
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
       public static void onAttachCapabilityEntity(AttachCapabilitiesEvent<Entity> event){
           if(event.getObject() != null){
               Entity entity = event.getObject();
               if(entity instanceof LivingEntity livingEntity) {
                   if (livingEntity instanceof Player) {
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "experience"), new ExperienceProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "level"), new CustomLevelProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "command"), new CommandProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "agility"), new AgilityProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "defense"), new DefenseProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "dexterity"), new DexterityProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "intelligence"), new IntelligenceProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "life"), new LifeProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"lifemax"), new LifeMaxProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "liferegeneration"), new LifeRegenerationProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "magicdefense"), new MagicDefenseProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "mana"), new ManaProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"manamax"),new ManaMaxProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "manaregeneration"), new ManaRegenerationProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "strength"), new StrengthProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "luck"), new LuckProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"statpoint"),new StatPointProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"join"),new FirstJoinProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"customclass"), new CustomClassProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "regenerationdelay"), new RegenerationDelayProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"reset"), new ResetProvider());
                   } else {
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "rank"), new RankProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "level"), new CustomLevelProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "command"), new CommandProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "agility"), new AgilityProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "defense"), new DefenseProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "dexterity"), new DexterityProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "intelligence"), new IntelligenceProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "life"), new LifeProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"lifemax"),new LifeMaxProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "liferegeneration"), new LifeRegenerationProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "magicdefense"), new MagicDefenseProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "mana"), new ManaProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"manamax"), new ManaMaxProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "manaregeneration"), new ManaRegenerationProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "strength"), new StrengthProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "luck"), new LuckProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"experiencereward"), new ExperienceRewardProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"join"),new FirstJoinProvider());
                       event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "regenerationdelay"), new RegenerationDelayProvider());
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
           } else if (livingEntity.getType() == EntityType.WITHER || livingEntity.getType() == ModEntityTypes.MUTANT_GOLEM.get()) {
               entityRank = 10;
               double bonus = setBonusValue(entityRank);
               entityClass = 50 * (int) (11 + difficultyLevel + bonus);
           } else {
               entityRank = getRandomRank(livingEntity);
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

           int hardcoreLevel = level.getLevelData().isHardcore() ? 15 : 1;

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
           int fnLvl = (finalLevel - lvlReduce) != 0 ? randomInt(finalLevel + entityRank * 5+(int)(dimensionLevel + hardcoreLevel + (float) (averageCustomLevel / 10)),1) : randomInt(7,1);

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
           double random = Math.random() * 100;

           return switch (numero) {
               case 1 -> random >= 1 && random <= 3;
               case 2 -> random > 3 && random <= 5;
               case 3 -> random > 5 && random <= 7;
               case 4 -> random > 7 && random <= 12;
               case 5 -> random > 12 && random <= 15;
               case 6 -> random > 15 && random <= 20;
               case 7 -> random > 20 && random <= 27;
               case 8 -> random > 27 && random <= 35;
               case 9 -> random > 40 && random <= 55;
               case 10 -> random > 55 && random <= 80;
               case 11 -> random > 80 && random <= 95;
               default -> false;
           };
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
           if(!event.getEntity().getLevel().isClientSide() && event.getOriginal() != null && event.getEntity() != null){
               event.getOriginal().reviveCaps();
               event.getOriginal().setHealth(10.0f);
               event.getOriginal().getFoodData().setFoodLevel(20);
               event.getOriginal().getCapability(FirstJoinProvider.ENTITY_FIRST_JOIN).ifPresent(oldStore -> event.getEntity().getCapability(FirstJoinProvider.ENTITY_FIRST_JOIN).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(oldStore -> event.getEntity().getCapability(CustomClassProvider.PLAYER_CLASS).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(oldStore -> event.getEntity().getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(ResetProvider.ENTITY_RESET).ifPresent(oldStore -> event.getEntity().getCapability(ResetProvider.ENTITY_RESET).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(oldStore -> event.getEntity().getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(ManaProvider.ENTITY_MANA).ifPresent(oldStore -> event.getEntity().getCapability(ManaProvider.ENTITY_MANA).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(oldStore -> event.getEntity().getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(oldStore -> event.getEntity().getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(oldStore -> event.getEntity().getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(newStore -> {
                   newStore.copyFrom(oldStore);
                   event.getEntity().getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> life.set(newStore.get()));
               }));
               event.getOriginal().getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(oldStore -> event.getEntity().getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(LuckProvider.ENTITY_LUCK).ifPresent(oldStore -> event.getEntity().getCapability(LuckProvider.ENTITY_LUCK).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(oldStore -> event.getEntity().getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(oldStore -> event.getEntity().getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(oldStore -> event.getEntity().getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(oldStore -> event.getEntity().getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(oldStore -> event.getEntity().getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(oldStore -> event.getEntity().getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(oldStore -> event.getEntity().getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(oldStore -> event.getEntity().getCapability(StatPointProvider.ENTITY_STATPOINT).ifPresent(newStore -> newStore.copyFrom(oldStore)));
               event.getOriginal().invalidateCaps();
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
                   double armor = target.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(1.0);
                   MobEffectInstance defInstance = target.getEffect(MobEffects.DAMAGE_RESISTANCE);

                   armor = target.hasEffect(MobEffects.DAMAGE_RESISTANCE) && defInstance != null ?  armor + calculateValue(defInstance,armor,"add") : armor;

                   if (attacker instanceof LivingEntity livingAttacker) {
                       MobEffectInstance damageBoostEffect = livingAttacker.getEffect(MobEffects.DAMAGE_BOOST);
                       MobEffectInstance damageDecreaseEffect = livingAttacker.getEffect(MobEffects.WEAKNESS);
                       double dmg = livingAttacker.getCapability(StrengthProvider.ENTITY_STRENGTH).map(Strength::get).orElse(0.001);

                       double dmgEff = damageBoostEffect != null && damageDecreaseEffect != null ? calculateDamageAndReduce(damageBoostEffect,damageDecreaseEffect,dmg) : damageBoostEffect != null ? calculateValue(damageBoostEffect,dmg,"add") : damageDecreaseEffect != null ? calculateValue(damageDecreaseEffect,dmg,"reduce") : 0.0;

                       int attackerLevel = livingAttacker.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1);
                       int targetLevel = target.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1);
                       double damageReduce = 0;

                       if (attackerLevel > targetLevel && livingAttacker instanceof Player && target instanceof Player) {
                           damageReduce = attackerLevel * 0.5 > targetLevel ? livingAttacker.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(500.0) : 0;
                       }

                       double percentDamageReduce = (armor + damageReduce) / (damage + armor + damageReduce);
                       double magicArmor = target.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).map(MagicDefense::get).orElse(1.0);
                       double percentMagicDamageReduce = (magicArmor + damageReduce) / (damage + magicArmor + damageReduce);

                       if (source.isMagic() || source.isFire()) {
                           double attackerMagicDamage = livingAttacker.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).map(Intelligence::get).orElse(1.0);
                           damage = (((damage + attackerMagicDamage) * percentMagicDamageReduce));

                           Collection<MobEffectInstance> activeEffects = target.getActiveEffects();
                           for (MobEffectInstance effectInstance : activeEffects) {
                               MobEffect effect = effectInstance.getEffect();
                               if (effect == MobEffects.POISON || effect == MobEffects.HARM) {
                                   int amplification = effectInstance.isAmbient() ? effectInstance.getAmplifier() + 1 : effectInstance.getAmplifier();
                                   damage = damage * amplification;
                                   break;
                               }
                           }
                       } else if (source.isProjectile()) {
                           double attackerRangeDamage = livingAttacker.getCapability(DexterityProvider.ENTITY_DEXTERITY).map(Dexterity::get).orElse(1.0) + dmgEff;
                           damage = ((damage + attackerRangeDamage) * percentDamageReduce);
                       } else if (source.isExplosion()) {
                           double attackerAgility = livingAttacker.getCapability(AgilityProvider.ENTITY_AGILITY).map(Agility::get).orElse(1.0);
                           damage = ((damage * 2 + attackerAgility) * ((percentDamageReduce * 0.25) + (percentMagicDamageReduce * 0.25)));
                       } else if (source.isCreativePlayer()) {
                           damage = 0;
                       } else {
                           double attackerStrength = livingAttacker.getCapability(StrengthProvider.ENTITY_STRENGTH).map(Strength::get).orElse(1.0);
                           damage = ((damage + attackerStrength + dmgEff) * percentDamageReduce);
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
                                       int entityRank = livingAttacker.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(0);
                                       int amount = 1;
                                       int count = customlevel.get();
                                       if (count >= 10000) {
                                           do {
                                               count = count - 10000;
                                               amount++;
                                           } while (count > 10000 && amount < 12);
                                       }
                                       boolean shouldApply = setChance(amount);

                                       if(shouldApply) {
                                           MobEffect[] effects = selectEffects(amount);
                                           int[] durations = applyDuration(amount);
                                           int[] levels = applyLevel(amount);

                                           for (int i = 0; i < entityRank; i++) {
                                               target.addEffect(new MobEffectInstance(effects[i], durations[i], levels[i]));
                                           }
                                       }
                                   });
                               }
                               if(totalDamage != -111111 && totalDamage < 1){
                                   totalDamage = 1;
                               }
                               if(target instanceof Player && livingAttacker instanceof Player){
                                   target.getCapability(RegenerationDelayProvider.ENTITY_REGENERATION_DELAY).ifPresent(RegenerationDelay::set);
                               }else if(!(target instanceof Player)){
                                   target.getCapability(RegenerationDelayProvider.ENTITY_REGENERATION_DELAY).ifPresent(RegenerationDelay::set);
                               }

                               life.consumeLife(totalDamage);
                               reduceArmorDurability(target, (totalDamage * 0.012));
                           }
                       });
                   }
                   else {
                       double defense = target.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(1.0);
                       double magicDefense = target.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).map(MagicDefense::get).orElse(1.0);
                       totalDamage = (defense / (damage + defense)) + (magicDefense / (damage + magicDefense));
                       if(source.isFall()){
                           double y = target.getY() < 0 ? (target.getY()*(-1)) : target.getY();
                           double oldY = target.yOld < 0 ? (target.yOld*(-1)) : target.yOld;

                           totalDamage = totalDamage*(target.fallDistance+1)+((oldY-y)*2);

                           target.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                               if(target instanceof Player && mana.get() != 0.0){
                                   if(mana.get() >= totalDamage){
                                       mana.consumeMana(totalDamage);
                                       totalDamage = 0.0;
                                       target.sendSystemMessage(Component.literal("You consume your mana, to complete decrease fall damage"));
                                   }else{
                                       totalDamage = totalDamage - mana.get();
                                       mana.consumeMana(totalDamage);
                                       target.sendSystemMessage(Component.literal("You run out of mana, you receive: "+doubleToString(totalDamage)+" of damage."));
                                   }
                               }
                           });
                       }
                       if(source.isMagic()){
                           Collection<MobEffectInstance> activeEffects = target.getActiveEffects();
                           for (MobEffectInstance effectInstance : activeEffects) {
                               MobEffect effect = effectInstance.getEffect();

                               if (effect == MobEffects.POISON || effect == MobEffects.HARM) {
                                   int amplification = effectInstance.isAmbient() ? effectInstance.getAmplifier() + 1 : effectInstance.getAmplifier();
                                   totalDamage = totalDamage * amplification;
                                   break;
                               }
                           }
                       }
                       if(source ==  DamageSource.OUT_OF_WORLD) {
                           totalDamage = target.getCapability(LifeProvider.ENTITY_LIFE).map(Life::get).orElse(10.0);
                       }
                       target.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> {
                           if(!source.isFall() && totalDamage < 1){
                               totalDamage = 1;
                           }

                           target.getCapability(RegenerationDelayProvider.ENTITY_REGENERATION_DELAY).ifPresent(RegenerationDelay::set);

                           life.consumeLife(totalDamage);
                           reduceArmorDurability(target, (totalDamage * 0.012));
                       });
                   }
               }
           }
       }
       private static void reduceArmorDurability(LivingEntity entity, double damage) {
           for (ItemStack armorStack : entity.getArmorSlots()) {
               if (!armorStack.isEmpty()) {
                   Item item = armorStack.getItem();
                   if ( item instanceof ArmorItem armorItem) {
                       int maxDurability = armorStack.getMaxDamage();
                       int currentDurability = armorStack.getDamageValue();
                       int newDurability = currentDurability - (int)(Math.ceil(damage)*0.0025);
                       EquipmentSlot equipmentSlot = armorItem.getSlot();
                       if (newDurability >= maxDurability) {
                           entity.broadcastBreakEvent(equipmentSlot);
                           entity.setItemSlot(equipmentSlot, ItemStack.EMPTY);
                       } else {
                           armorStack.setDamageValue(newDurability);
                       }
                   }
               }
           }
       }
       private static void dropChest(NonNullList<ItemStack> chest, double x, double y, double z,Level level) {
           level.setBlockAndUpdate(new BlockPos(x, y, z), Blocks.CHEST.defaultBlockState());
           BlockEntity blockEntity = level.getBlockEntity(new BlockPos(x, y, z));
           if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
               for (int slot = 0; slot < chest.size(); slot++) {
                   ItemStack stack = chest.get(slot);
                   if (!stack.isEmpty()) {
                       chestBlockEntity.setItem(slot, stack);
                   }
               }
               if(chestBlockEntity.isEmpty()){
                   chestBlockEntity.setRemoved();
               }
           }
       }
       private static @NotNull NonNullList<ItemStack> createChestContents(ItemStack itemStack) {
           NonNullList<ItemStack> chest = NonNullList.create();
           chest.add(itemStack);
           return chest;
       }
       @SubscribeEvent
       public static void onLivingEntityDeath(LivingDeathEvent event) {
           if(!event.getEntity().getLevel().isClientSide()){
               if(!(event.getEntity() instanceof Player) && !(event.getEntity() instanceof ServerPlayer)){
                   LivingEntity livingEntity = event.getEntity();
                   boolean auraChest = livingEntity.getCapability(LifeProvider.ENTITY_LIFE).map(Life::getDie).orElse(true);
                   if(!auraChest){
                       livingEntity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> life.setDie(true));
                       int rank = livingEntity.getCapability(RankProvider.ENTITY_RANK).map(Rank::get).orElse(0);
                       double dropRate = generateRandomValue(rank);

                       ItemStack auraItemStack;

                       switch (rank){
                           case 2 -> auraItemStack = ModItems.ELITE_AURA.get().getDefaultInstance();
                           case 3 -> auraItemStack = ModItems.BRUTAL_AURA.get().getDefaultInstance();
                           case 4 -> auraItemStack = ModItems.CHAMPION_AURA.get().getDefaultInstance();
                           case 5 -> auraItemStack = ModItems.HERO_AURA.get().getDefaultInstance();
                           case 6 -> auraItemStack = ModItems.DEMON_AURA.get().getDefaultInstance();
                           case 7 -> auraItemStack = ModItems.LEGENDARY_AURA.get().getDefaultInstance();
                           case 8 -> auraItemStack = ModItems.MYTHICAL_AURA.get().getDefaultInstance();
                           case 9 -> auraItemStack = ModItems.UNIQUE_AURA.get().getDefaultInstance();
                           case 10 -> auraItemStack = ModItems.SEMI_BOSS_AURA.get().getDefaultInstance();
                           case 11 -> auraItemStack = ModItems.BOSS_AURA.get().getDefaultInstance();
                           default -> auraItemStack = ModItems.COMMON_AURA.get().getDefaultInstance();
                       }

                       if(willDropAura(dropRate) && livingEntity.getKillCredit() instanceof Player){
                           dropChest(createChestContents(auraItemStack), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getLevel());
                       }
                   }
               }
               if (event.getEntity() instanceof ServerPlayer player) {
                   Style deathStyle = Style.EMPTY;

                   ClickEvent msgClick =new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp "+ player.getName().getString() + " " + player.getX() + " " + player.getY() + " " + player.getZ());
                   HoverEvent msgHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT,Component.literal("Click here to go there."));
                   TextColor msgColor = TextColor.fromRgb(0xFFD700);

                   deathStyle.withColor(msgColor);
                   deathStyle.withClickEvent(msgClick);
                   deathStyle.withHoverEvent(msgHover);
                   deathStyle.withInsertion("Click here to go there.");

                   player.sendSystemMessage(Component.literal("You die at [X: "+player.getX()+", Y: "+player.getY()+", Z: "+player.getZ()+"].").withStyle(deathStyle));
               }
           }
       }
       @SubscribeEvent
       public static void onLivingEntityUpdate(LivingEvent.LivingTickEvent event){
           LivingEntity livingEntity = event.getEntity();
           if (livingEntity != null) {
               MobEffectInstance regenerationEffect = livingEntity.hasEffect(MobEffects.REGENERATION) ? livingEntity.getEffect(MobEffects.REGENERATION) : null;
               regEffectBonus = regenerationEffect != null && regenerationEffect.getDuration() > 1 ? calculateValue(regenerationEffect,livingEntity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).map(LifeRegeneration::get).orElse(1.0),"add") : 0.0;

               if(livingEntity instanceof ServerPlayer player){
                   player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> player.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                       if(mana.get() != manaMax.get() || life.get() != lifeMax.get()){
                           updatePlayerCapabilities(player);
                       }
                       if (life.get() <= 0 && !life.getDie()) {
                           player.skipDropExperience();
                           life.setDie(true);
                           player.setHealth(0.0f);
                           player.die(DamageSource.GENERIC);
                       }
                       int delay = player.getCapability(RegenerationDelayProvider.ENTITY_REGENERATION_DELAY).map(RegenerationDelay::get).orElse(0);
                       if (life.get() < lifeMax.get() && life.get() > 0 && delay == 0) {
                           life.add((lifeRegeneration.get()+regEffectBonus) * 0.05);
                       }else{
                           int cooldown = player.getCapability(RegenerationDelayProvider.ENTITY_REGENERATION_DELAY).map(RegenerationDelay::get).orElse(0);
                           if(cooldown == 100 && sendMSG){
                               player.sendSystemMessage(Component.literal("You have receive damage, regeneration disable for 5 seconds, if you receive damage again cooldown will be reset to 5 seconds.").withStyle(ChatFormatting.RED));
                               sendMSG = false;
                           }
                           if(cooldown == 1 && !sendMSG){
                               sendMSG = true;
                               player.sendSystemMessage(Component.literal("Regeneration enable again").withStyle(ChatFormatting.GOLD));
                           }
                           player.getCapability(RegenerationDelayProvider.ENTITY_REGENERATION_DELAY).ifPresent(RegenerationDelay::decrease);
                       }
                       if (mana.get() < manaMax.get()) {
                           mana.add(manaRegeneration.get() * 0.05);
                       }
                       if(mana.get() > manaMax.get()){
                           mana.set(manaMax.get());
                       }
                       if(life.get() > lifeMax.get()){
                           life.set(lifeMax.get());
                       }
                   }))))));

               }
               else{
                   if(livingEntity instanceof Animal || livingEntity instanceof Monster || livingEntity instanceof WaterAnimal || livingEntity instanceof Villager){
                       livingEntity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(customLevel -> livingEntity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> livingEntity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> livingEntity.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> livingEntity.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> livingEntity.getCapability(FirstJoinProvider.ENTITY_FIRST_JOIN).ifPresent(join -> livingEntity.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> livingEntity.getCapability(LifeMaxProvider.ENTITY_LIFE_MAX).ifPresent(lifeMax -> livingEntity.getCapability(RankProvider.ENTITY_RANK).ifPresent(rank -> {
                           if (!join.get()) {
                               setCapabilities(livingEntity);
                               join.set();
                           }
                           if (life.get() <= 0) {
                               livingEntity.setHealth(0.0f);
                               livingEntity.die(DamageSource.GENERIC);
                           }
                           if(livingEntity.getCapability(RegenerationDelayProvider.ENTITY_REGENERATION_DELAY).map(RegenerationDelay::get).orElse(0) == 0){
                               if (life.get() < lifeMax.get() && life.get() > 0) {
                                   switch (rank.get()){
                                       case 11 -> life.add((lifeRegeneration.get())+regEffectBonus);
                                       case 10 -> life.add((lifeRegeneration.get()*0.88)+regEffectBonus);
                                       case 6,7,8,9 -> life.add((lifeRegeneration.get() * 0.75)+regEffectBonus);
                                       case 3,4,5 -> life.add((lifeRegeneration.get() * 0.5)+regEffectBonus);
                                       default -> life.add((lifeRegeneration.get() * 0.25)+regEffectBonus);
                                   }
                               }
                           }else{
                               livingEntity.getCapability(RegenerationDelayProvider.ENTITY_REGENERATION_DELAY).ifPresent(RegenerationDelay::decrease);
                           }

                           if (mana.get() < manaMax.get()) {
                               mana.add(manaRegeneration.get() * 0.5);
                           }
                       })))))))));
                   }
               }
           }
       }
       @SubscribeEvent
       public static void onLivingExperienceDrop(LivingExperienceDropEvent event){
           if(!event.getEntity().getLevel().isClientSide() && event.getEntity() != null && event.getAttackingPlayer() != null) {
               LivingEntity target = event.getEntity();
               Player entity = event.getAttackingPlayer();
               event.setDroppedExperience(dropExperience(target,entity,event.getOriginalExperience()));
           }
       }
       @SubscribeEvent
       public static void onPlayerGetExperience(PlayerXpEvent.PickupXp event) {
           Random random = new Random();
           if (!event.getEntity().getLevel().isClientSide() && event.getEntity() != null && event.getOrb() != null) {
               int xp = event.getOrb().getValue();
               if (event.getEntity() instanceof ServerPlayer player) {
                   player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(level -> player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(experience -> {
                       experience.add(xp);
                       if (experience.get() >= level.experienceNeeded() && random.nextFloat() > 0.05f) {
                           boolean flag = true;
                           do {
                               experience.consume(level.experienceNeeded());
                               level.setPreviousLevelExp(level.get());
                               level.add();

                               int opt = player.getCapability(CustomClassProvider.PLAYER_CLASS).map(CustomClass::getPlayerClass).orElse(0);
                               switch (opt) {
                                   case 1 -> levelUpPlayer(player,
                                           1, 1,
                                           0.00025, 0.0005,
                                           0.5, 0.25, 1,
                                           6, 0, 8, 8, 8);

                                   case 2 -> levelUpPlayer(player, 0.25, 5,
                                           0.00045, 0.025, 0.01,
                                           0.03, 1, 0, 0, 1, 0, 0.1);

                                   case 3 -> levelUpPlayer(player, 2.5, 1, 0.00025, 0.0005, 2, 2, 0, 0.5, 1, 0.025, 0, 0.03);

                                   case 4 -> levelUpPlayer(player, 0, 0, 0.00025, 0.0005, 0, 0, 0.01, 0.025, 2.25, 4.23, 0.01, 9.15);

                                   case 6 -> levelUpPlayer(player, 0.25, 1,
                                           0.00045, 0.025, 0.1,
                                           0.3, 1, 0.25, 0.04, 0.1, 8, 0.1);

                                   case 7 -> levelUpPlayer(player, 0.65, 5,
                                           0.0005, 0.015, 0.31,
                                           0.43, 1, 0, 0, 1, 0, 0.1);

                                   case 8 -> levelUpPlayer(player, 1.25, 0,
                                           0.0045, 0.00025, 0.71,
                                           0.33, 0.01, 0.025, 0.41, 0.1, 0, 0.15);
                                   default -> levelUpPlayer(player, 0.5, 1, 0.00025, 0.0005, 1, 1, 1, 5, 1, 1, 1, 5);
                               }
                               player.playSound(ModSoundsRPG.LEVEL_UP_SOUND.get(),0.2f,0.2f);
                               if (experience.get() < level.experienceNeeded()) {
                                   flag = false;
                               }
                           } while (flag);

                       }
                   }));
               }
           }
       }
       @SubscribeEvent
       public static void onPlayerJoin(EntityJoinLevelEvent event) {
           if (!event.getLevel().isClientSide() && event.getEntity() != null) {
               if (event.getEntity() instanceof ServerPlayer player) {
                   player.getCapability(FirstJoinProvider.ENTITY_FIRST_JOIN).ifPresent(firstJoin -> {
                       if(!firstJoin.get()){
                           BlockPos pos = new BlockPos(player.getX(),player.getY(),player.getZ());
                           NetworkHooks.openScreen(player, new MenuProvider() {
                               @Override
                               public @NotNull Component getDisplayName() {
                                   return Component.literal("Select Class Menu");
                               }

                               @Override
                               public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
                                   return new PlayerClassSelectMenu(id,inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                               }
                           },pos);
                       }
                   });
                   ConfigSyncing.syncAllConfigsToOneClient(player);
                   updatePlayerCapabilities(player);
               }
           }
       }
   }

    @Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.MUTANT_GOLEM.get(), MutantGolemEntity.setAttributes());
            event.put(ModEntityTypes.DRAKE.get(), DrakeV1Entity.setAttributes());
            event.put(ModEntityTypes.DRAKE_2.get(), DrakeV2Entity.setAttributes());
            event.put(ModEntityTypes.DRAKE_3.get(), DrakeV3Entity.setAttributes());
            event.put(ModEntityTypes.DRAKE_4.get(), DrakeV4Entity.setAttributes());
            event.put(ModEntityTypes.DRAKE_5.get(), DrakeV5Entity.setAttributes());
            event.put(ModEntityTypes.DRAKE_6.get(), DrakeV6Entity.setAttributes());
            event.put(ModEntityTypes.DRAKE_7.get(), DrakeV7Entity.setAttributes());
        }
    }

}