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
import net.cheto97.rpgcraftmod.villager.ModVillagers;

import static net.cheto97.rpgcraftmod.ModHud.Elements.vanilla.HudElementViewVanilla.*;
import static net.cheto97.rpgcraftmod.util.RPGutil.setBonusValue;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
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
    static private double extra = 0;
    static private double totalDamage;

    private static int selectTier(double inversionFactor) {
        double[] tierPercentages = {0.4, 0.25, 0.15, 0.09, 0.05, 0.03, 0.02, 0.01, 0.005, 0.0005, 0.00005};

        double totalPercentage = 0.0;
        for (double percentage : tierPercentages) {
            totalPercentage += percentage;
        }

        double maxInversionFactor = 10000.0;
        double invertedPercentage = totalPercentage * (maxInversionFactor - inversionFactor) / maxInversionFactor;

        Random random = new Random();
        double randomNumber = random.nextDouble() * (totalPercentage + invertedPercentage);

        double cumulativePercentage = 0.0;
        int selectedTier = 1;
        for (int i = 0; i < tierPercentages.length; i++) {
            cumulativePercentage += tierPercentages[i];
            if (randomNumber < cumulativePercentage) {
                selectedTier = i + 1;
                break;
            }
        }
        return selectedTier;
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
    public static void onAttachCapabilityEntity(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if(entity instanceof LivingEntity livingEntity){
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
            if(level.players().size() != 0){
                connectedPlayers = level.players().size();
            }

            if(livingEntity.getType() == EntityType.ENDER_DRAGON){
                entityRank = 11;
                double bonus = setBonusValue(entityRank);
                entityClass = 100*(int)(11*difficultyLevel+bonus);
            }else if(livingEntity.getType() == EntityType.WITHER){
                entityRank = 11;
                double bonus = setBonusValue(entityRank);
                entityClass = 50*(int)(11*difficultyLevel+bonus);
            }else{
                entityRank = getRandomRank();
                double bonus = setBonusValue(entityRank);
                if(entityRank < 10){
                    entityClass = (int)(entityRank*difficultyLevel+bonus);
                }else{
                    entityClass = 25*(int)(entityRank*difficultyLevel+bonus);
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
                        player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(playerLevel ->{
                            if(playerLevel.get() != 0){
                                lvls = playerLevel.get();
                            }
                        });
                        return lvls;
                    })
                    .sum();
            int averageCustomLevel = totalCustomLevels / connectedPlayers;

            int finalLevel = entityClass + (int)Math.floor(distanceLevel + difficultyLevel + timeLevel + connectedPlayers + dimensionLevel + hardcoreLevel + (float)(averageCustomLevel / 10));

            if(timeLevel < 2){
                lvlReduce = finalLevel;
            }


            int opt = 1;
            if(livingEntity instanceof Player){
                opt = 2;
            }

            int fnLvl = (finalLevel - lvlReduce) != 0 ? (finalLevel+entityRank*50):0;


            switch (opt){
                case 1 ->{
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"rank"),new RankProvider(entityRank));
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"level"), new CustomLevelProvider(fnLvl));
                }
                case 2 ->{
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"experience"), new ExperienceProvider());
                    event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"level"), new CustomLevelProvider(1));
                }
            }
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"command"), new CommandProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"agility"), new AgilityProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"defense"), new DefenseProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"dexterity"), new DexterityProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"intelligence"), new IntelligenceProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"life"), new LifeProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"lifeRegeneration"), new LifeRegenerationProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"magicDefense"), new MagicDefenseProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"mana"), new ManaProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"manaRegeneration"),new ManaRegenerationProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"strength"), new StrengthProvider(livingEntity));
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID, "luck"), new LuckProvider());
        }
    }
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if(event.isWasDeath() && !event.getEntity().getLevel().isClientSide()){
            event.getOriginal().getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(oldStore -> {
                event.getOriginal().getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(ManaProvider.ENTITY_MANA).ifPresent(oldStore -> {
                event.getOriginal().getCapability(ManaProvider.ENTITY_MANA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(oldStore -> {
                event.getOriginal().getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(LifeProvider.ENTITY_LIFE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(LifeProvider.ENTITY_LIFE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(oldStore -> {
                event.getOriginal().getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(LuckProvider.ENTITY_LUCK).ifPresent(oldStore -> {
                event.getOriginal().getCapability(LuckProvider.ENTITY_LUCK).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(oldStore -> {
                event.getOriginal().getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(oldStore -> {
                event.getOriginal().getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(oldStore -> {
                event.getOriginal().getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(oldStore -> {
                event.getOriginal().getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event){
        event.setCanceled(true);

        Entity attacker = event.getSource().getEntity();
        LivingEntity target = event.getEntity();
        float damage = event.getAmount();
        DamageSource source = event.getSource();

        if(target != null && attacker instanceof LivingEntity livingAttacker){
            int attackerLevel = livingAttacker.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1);
            int targetLevel = target.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1);
            double damageReduce = 0;

            if( attackerLevel > targetLevel && livingAttacker instanceof Player && target instanceof Player){
                damageReduce =  attackerLevel * 0.5 > targetLevel ?  livingAttacker.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(500.0) : 0;
            }

            double armor = target.getCapability(DefenseProvider.ENTITY_DEFENSE).map(Defense::get).orElse(1.0);
            double percentDamageReduce = armor+damageReduce / (damage+armor+damageReduce);
            double magicArmor = target.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).map(MagicDefense::get).orElse(1.0);
            double percentMagicDamageReduce = magicArmor+damageReduce / (damage+magicArmor+damageReduce);

            if(source.isMagic() || source.isFire()){
                double attackerMagicDamage = livingAttacker.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).map(Intelligence::get).orElse(1.0);
                damage = (float)(((damage+attackerMagicDamage)*percentMagicDamageReduce)-damageReduce);
            }
            else if(source.isProjectile()){
                double attackerRangeDamage = livingAttacker.getCapability(DexterityProvider.ENTITY_DEXTERITY).map(Dexterity::get).orElse(1.0);
                damage = (float)((damage+attackerRangeDamage)*percentDamageReduce-damageReduce);
            }
            else if(source.isExplosion()){
                 double attackerAgility = livingAttacker.getCapability(AgilityProvider.ENTITY_AGILITY).map(Agility::get).orElse(1.0);
                 damage = (float)((damage*2+attackerAgility)*((percentDamageReduce*0.25)+(percentMagicDamageReduce*0.25))-damageReduce);
            }
            else if(source.isCreativePlayer()){
                damage = 0;
            }else{
                double attackerStrength = livingAttacker.getCapability(StrengthProvider.ENTITY_STRENGTH).map(Strength::get).orElse(1.0);
                damage = (float)((damage+attackerStrength)*percentDamageReduce-damageReduce);
            }
            if(livingAttacker instanceof Player && target instanceof Player){
                totalDamage = attackerLevel * 0.2 > targetLevel ? -1.1 : damage;
            }
            target.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life ->{
                if(totalDamage == -1.1){
                    livingAttacker.sendSystemMessage(Component.literal("Your level is higher than "+target.getName().getString()+", setting damage to 0").withStyle(ChatFormatting.DARK_RED));
                }else{
                    BlockPos entityPos = new BlockPos(target.getBlockX(),target.getBlockY(),target.getBlockZ());
                    life.consumeLife(totalDamage);
                    reduceArmorDurability(target,totalDamage);
                    if(livingAttacker instanceof Player player2 && target instanceof Player player){
                        ModMessages.sendToPlayer(new PlayerSyncPacket(player.getName().getString(),player.getId(),
                                -1,null,-1,-1,-1,-1,-1,
                                -1,-1,-1,-1,
                                -1,-1, -1,-1,
                                -1),(ServerPlayer) player2);

                        ModMessages.sendToPlayer(new PlayerSyncPacket(player.getName().getString(),player.getId(),
                                -1,null,-1,-1,-1,-1,-1,
                                -1,-1,-1,-1,
                                -1,-1, -1,-1,
                                -1),(ServerPlayer) player);
                    }
                    else if(!(livingAttacker instanceof Player) && target instanceof Player player){
                        ModMessages.sendToPlayer(new PlayerSyncPacket(player.getName().getString(),player.getId(),
                                -1,null,-1,-1,-1,-1,-1,
                                -1,-1,-1,-1,
                                -1,-1, -1,-1,
                                -1),(ServerPlayer) player);
                    }else if(livingAttacker instanceof Player player){
                        ModMessages.sendToPlayer(new EntitySyncPacket(target.getName().getString(),target.getId(),
                                -1,entityPos,-1,-1,-1,-1,-1,
                                -1,-1,-1,-1,
                                -1,-1, -1,-1,
                                -1),(ServerPlayer) player);
                    }
                }

            });
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
    }
    @SubscribeEvent
    public static void onCraftedItem(PlayerEvent.ItemCraftedEvent event) {
        Player entity = event.getEntity();
        int level = entity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1);
        double luck = entity.getCapability(LuckProvider.ENTITY_LUCK).map(Luck::get).orElse(1.0);
        ItemStack output = event.getCrafting();
        if(luck > 10000){
            luck = 10000;
        }
        int opt = selectTier(luck-1);

        if(event.getEntity().getLevel().isClientSide()){
            String name = output.getDisplayName().getString();

            switch (opt) {
                case 1 -> {
                    Component iQualityComponent = Component.literal("[Crafted - Common] - {"+level+"} "+name).withStyle(ChatFormatting.GRAY);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent);
                    extra = 0.1*(double)((level/10000)+1);
                }

                case 2 -> {
                    Component iQualityComponent2 = Component.literal("[Crafted - Uncommon] - {"+level+"} "+name).withStyle(ChatFormatting.DARK_GRAY);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent2);
                    extra = 0.25*(double)((level/10000)+1);
                }
                case 3 -> {
                    Component iQualityComponent3 = Component.literal("[Crafted - Very Uncommon] - {"+level+"} "+name).withStyle(ChatFormatting.WHITE);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent3);
                    extra = 0.28*(double)((level/10000)+1);
                }
                case 4 -> {
                    Component iQualityComponent4 = Component.literal("[Crafted - Rare] - {"+level+"} "+name).withStyle(ChatFormatting.AQUA);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent4);
                    extra = 0.32*(double)((level/10000)+1);
                }
                case 5 -> {
                    Component iQualityComponent5 = Component.literal("[Crafted - Very Rare] - {"+level+"} "+name).withStyle(ChatFormatting.GREEN);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent5);
                    extra = 0.35*(double)((level/10000)+1);
                }
                case 6 -> {
                    Component iQualityComponent6 = Component.literal("[Crafted - Ultra Rare] - {"+level+"} "+name).withStyle(ChatFormatting.DARK_AQUA);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent6);
                    extra = 0.39*(double)((level/10000)+1);
                }
                case 7 -> {
                    Component iQualityComponent7 = Component.literal("[Crafted - Ultra Really Rare] - {"+level+"} "+name).withStyle(ChatFormatting.DARK_GREEN);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent7);
                    extra = 0.45*(double)((level/10000)+1);
                }
                case 8 -> {
                    Component iQualityComponent8 = Component.literal("[Crafted - Epic] - {"+level+"} "+name).withStyle(ChatFormatting.DARK_PURPLE);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent8);
                    extra = 0.55*(double)((level/10000)+1);
                }
                case 9 -> {
                    Component iQualityComponent9 = Component.literal("[Crafted - Legendary] - {"+level+"} "+name).withStyle(ChatFormatting.GOLD);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent9);
                    extra = 0.67*(double)((level/10000)+1);
                }
                case 10 -> {
                    Component iQualityComponent10 = Component.literal("[Crafted - Mythic] - {"+level+"} "+name).withStyle(ChatFormatting.LIGHT_PURPLE);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent10);
                    extra = 1.55*(double)((level/10000)+1);
                }
                case 11 -> {
                    Component iQualityComponent11 = Component.literal("[Crafted - OverPowered] - {"+level+"} "+name).withStyle(ChatFormatting.RED);
                    output.setHoverName(null);
                    output.setHoverName(iQualityComponent11);
                    extra =3.125*(double)((level/10000)+1);
                }
            }

            Item item = output.getItem();

            double lvlExtra = (level*0.00025)+1;


            if (item instanceof BowItem) {
                entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(extraDamage -> {
                    var customDamage = lvlExtra*(1 + (2+(extraDamage.get()*0.0015))*(4.15*0.35)*1.1)+(1.1*extra);
                    output.setDamageValue((int)customDamage);
                    event.setCanceled(true);
                    event.getInventory().setItem(1,output);
                });
            }else if(item instanceof CrossbowItem){
                entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(extraDamage -> {
                    var customDamage = lvlExtra*(1 + (2+(extraDamage.get()*0.0015))*(4.15*0.35)*1.1)+(1.1*extra);
                    output.setDamageValue((int)customDamage);
                    event.setCanceled(true);
                    event.getInventory().setItem(1,output);
                });
            }else if(item instanceof TridentItem){
                entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(extraDamage -> {
                    var customDamage = lvlExtra*((1+(extraDamage.get()*0.0025))*(2.15*0.25)*1.9)+(1.9*extra);
                    output.setDamageValue((int)customDamage);
                    event.setCanceled(true);
                    event.getInventory().setItem(1,output);
                });
            }else if(item instanceof PickaxeItem){
                entity.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(stat ->{
                    var customDamage = lvlExtra*((1+(stat.get()*0.0025))*(2.15*0.25)*1.9)+(1.9*extra);
                    output.setDamageValue((int)customDamage);
                    event.setCanceled(true);
                    event.getInventory().setItem(1,output);
                });
            }else if(item instanceof AxeItem){
                entity.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(stat ->{
                    var customDamage = lvlExtra*((1+(stat.get()*0.0025))*(2.15*0.25)*1.9)+(1.9*extra);
                    output.setDamageValue((int)customDamage);
                    event.setCanceled(true);
                    event.getInventory().setItem(1,output);
                });
            }else if(item instanceof SwordItem){
                entity.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(stat ->{
                    var customDamage = lvlExtra*((1+(stat.get()*0.0025))*(2.15*0.25)*1.9)+(1.9*extra);
                    output.setDamageValue((int)customDamage);
                    event.setCanceled(true);
                    event.getInventory().setItem(1,output);
                });
            }else if(item instanceof HoeItem){
                entity.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(stat ->{
                    var customDamage = lvlExtra*((1+(stat.get()*0.0025))*(2.15*0.25)*1.9)+(1.9*extra);
                    output.setDamageValue((int)customDamage);
                    event.setCanceled(true);
                    event.getInventory().setItem(1,output);
                });
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
        if(!event.getEntity().getLevel().isClientSide()){
            LivingEntity entity = event.getEntity();
            if (entity != null) {
                BlockPos pos = new BlockPos(entity.getBlockX(),entity.getBlockY(),entity.getBlockZ());

                entity.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(mpRegen ->
                        entity.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                            if (mana.get() < mana.getMax()) {
                                mana.add(mpRegen.get() * 0.04);
                               if( entity instanceof Player){
                                        ModMessages.sendToPlayer(new PlayerSyncPacket(entity.getName().getString(),entity.getId(),
                                                -1,pos,-1,-1,mana.get(),-1,-1,
                                                -1,-1,-1,-1,
                                                -1,-1, -1,-1,
                                               -1),(ServerPlayer) entity);
                                }
                            }
                        }));
                entity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(hpRegen -> {
                    entity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> {
                        if (life.get() < life.getMax()) {
                            life.add(hpRegen.get() * 0.025);

                            if( entity instanceof Player){
                                ModMessages.sendToPlayer(new PlayerSyncPacket(entity.getName().getString(),entity.getId(),
                                        -1,pos,life.get(),-1,-1,-1,-1,
                                        -1,-1,-1,-1,
                                        -1,-1, -1,-1,
                                        -1),(ServerPlayer) entity);
                            }}
                        if (life.get() <= 0) {
                            entity.setHealth(0.0f);
                            entity.die(DamageSource.GENERIC);
                        }
                        });

                    });
            }
        }
    }
    @SubscribeEvent
    public static void onLivingExperienceDrop(LivingExperienceDropEvent event){
        LivingEntity target = event.getEntity();
        Player entity = event.getAttackingPlayer();

        if(!target.getLevel().isClientSide() && entity != null){
            entity.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(customExp ->{
                entity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(playerLevel -> {
                    target.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(targetLevel ->{
                        Difficulty difficulty = event.getEntity().getLevel().getDifficulty();
                        double hardcoreXP = event.getEntity().getLevel().getLevelData().isHardcore() ? 3.5 : 1;

                        double difficultyXP = switch (difficulty) {
                            case PEACEFUL -> 1;
                            case EASY -> 2.3;
                            case NORMAL -> 6.5;
                            case HARD -> 12.5;
                        };
                            double droppedExperience = (event.getOriginalExperience()*difficultyXP)*hardcoreXP;
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

                            double exp = droppedExperience * targetLevelValue;
                            event.setDroppedExperience((int) (exp * multiplier));
                        if (multiplier == 0.0) {
                            entity.sendSystemMessage(Component.translatable(MESSAGE_LEVEL_TOO_HIGH).withStyle(ChatFormatting.DARK_RED));
                        }
                    });
                });
            });
        }
    }
    @SubscribeEvent
    public static void onPlayerGetExperience(PlayerXpEvent.PickupXp event){
        int xp = event.getOrb().getValue();
        Player player = event.getEntity();

        player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(level ->{
            player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(experience ->{
                experience.add(xp);

                BlockPos playerPos = new BlockPos(player.getBlockX(),player.getBlockY(),player.getBlockZ());

                if(experience.get() >= level.experienceNeeded()){
                    boolean flag = true;
                    int count = 0;
                    do {
                        experience.consume(level.experienceNeeded());
                        count++;
                        level.add(count);
                        ModMessages.sendToPlayer(new PlayerSyncPacket(player.getName().getString(),player.getId(),
                                level.get(),playerPos,-1,-1,-1,-1,-1,
                                -1,-1,-1,-1,
                                -1,-1, -1,-1,
                                experience.get()),(ServerPlayer) player);
                        //play sound event level_up
                        player.sendSystemMessage(Component.literal("You have level up."));
                        if(experience.get() < level.experienceNeeded()){
                            flag = false;
                        }
                    }while (flag);

                }

                ModMessages.sendToPlayer(new PlayerSyncPacket(player.getName().getString(),player.getId(),
                        -1,playerPos,-1,-1,-1,-1,-1,
                        -1,-1,-1,-1,
                        -1,-1, -1,-1,
                        experience.get()),(ServerPlayer) player);
            });
        });
        player.sendSystemMessage(Component.literal("Obtained experience: " + formatDouble(xp)));
    }
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide() && event.getEntity() != null){
            if(event.getEntity() instanceof ServerPlayer player){
                BlockPos playerPos = new BlockPos(player.getBlockX(),player.getBlockY(),player.getBlockZ());
               player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(level ->{
                   player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life ->{
                       player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(lifeRegeneration -> {
                           player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana ->{
                               player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(manaRegeneration -> {
                                   player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> {
                                       player.getCapability(MagicDefenseProvider.ENTITY_MAGIC_DEFENSE).ifPresent(magicDefense -> {
                                           player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence -> {
                                               player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(dexterity -> {
                                                   player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(strength -> {
                                                       player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(command -> {
                                                           player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility -> {
                                                               player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(luck -> {
                                                                   player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(experience -> {
                                                                       ModMessages.sendToPlayer(new PlayerSyncPacket(player.getName().getString(),player.getId(),
                                                                               level.get(),playerPos,life.get(),life.getMax(),mana.get(),mana.getMax(),agility.get(),
                                                                               command.get(),defense.get(),magicDefense.get(),dexterity.get(),
                                                                               intelligence.get(),lifeRegeneration.get(), manaRegeneration.get(), strength.get(),
                                                                               experience.get()),player);
                                                                   });
                                                               });
                                                           });
                                                       });
                                                   });
                                               });
                                           });
                                       });
                                   });
                               });
                           });
                       });
                   });
               });
            }
        }
    }
}
