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
//import net.cheto97.rpgcraftmod.util.GuiSettingsMod;
import net.cheto97.rpgcraftmod.villager.ModVillagers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

@Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID)
public class ModEvents {
    private static final String MESSAGE_LEVEL_TOO_HIGH = "message.rpgcraftmod.level_too_high";
    private static final String MESSAGE_LEVEL_UP = "message.rpgcraft.level_up";
    private static int lvls = 0;
    private static int lvlReduce = 0;

    private static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }
    private static int selectTier() {
        double[] tierPercentages = {0.4, 0.25, 0.15, 0.09, 0.05, 0.03, 0.02, 0.01, 0.005, 0.0005, 0.00005};

        double totalPercentage = 0.0;
        for (double percentage : tierPercentages) {
            totalPercentage += percentage;
        }

        Random random = new Random();
        double randomNumber = random.nextDouble() * totalPercentage;

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
        var entity = event.getObject();
        if( entity instanceof Player | entity instanceof Monster || entity instanceof Animal ){
            if(!entity.getCapability(ManaProvider.ENTITY_MANA).isPresent()){
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"level"), new CustomLevelProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"experience"), new ExperienceProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"mana"), new ManaProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"manaregeneration"),new ManaRegenerationProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"life"), new LifeProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"liferegeneration"), new LifeRegenerationProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"luck"), new LuckProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"defense"), new DefenseProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"strength"), new StrengthProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"dexterity"), new DexterityProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"command"), new CommandProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"agility"), new AgilityProvider());
                event.addCapability(new ResourceLocation(RpgcraftMod.MOD_ID,"intelligence"), new IntelligenceProvider());
            }
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
    public static void onEntityAttack(LivingAttackEvent event){
        var entity = event.getEntity();

        if(entity != null){
                event.getSource().setScalesWithDifficulty();
        }
    }
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event){
        event.setCanceled(true);
        var entity = event.getEntity();
        var source = event.getSource();
        if(entity != null){
            if(source.isMagic()){
                entity.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(stat ->{
                    event.setAmount(event.getAmount() * (float)(stat.get()/1.5));
                    entity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(hp ->{
                        hp.consumeLife(event.getAmount());
                    });
                });
            }else{
                entity.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(stat ->{
                    event.setAmount(event.getAmount() * (float)(stat.get()/2.4));
                    entity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(hp ->{
                        hp.consumeLife(event.getAmount());
                    });
                });
            }
        }
    }
    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event){
        event.setCanceled(true);
        var entity = event.getEntity();
        var damage = event.getAmount();
        if(entity != null){
            entity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> {
                entity.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(defense -> {
                    entity.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(intelligence ->{
                        double total_damage;
                        if(event.getSource().isFall() || event.getSource().isExplosion() || event.getSource().isProjectile()){
                            total_damage = damage - (defense.get()*0.40);
                        }else if(event.getSource().isFire() || event.getSource().isMagic()){
                            total_damage = damage - (intelligence.get()*0.5);
                        }else{
                            total_damage = damage - defense.get();
                        }

                        if(total_damage < 0){
                            total_damage = 0;
                        }

                        life.consumeLife(total_damage);
                    });
                });
            });
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
    }
    /*
   ** @SubscribeEvent
    public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event){
        Player entity = event.getEntity();
        int opt = selectTier();
        double extra = 0;

        if(entity != null && !entity.getLevel().isClientSide()){
            TooltipFlag flag = () -> false;

            String name = event.getCrafting().getDisplayName().getString();

            switch (opt) {
                case 1 -> {
                    Component iQualityComponent = Component.literal("[Crafted - Common] "+name).withStyle(ChatFormatting.GRAY);
                    event.getCrafting().setHoverName(iQualityComponent);
                    extra = 0.25;
                }
                case 2 -> {
                    Component iQualityComponent2 = Component.literal("[Crafted - Uncommon] "+name).withStyle(ChatFormatting.DARK_GRAY);
                    event.getCrafting().setHoverName(iQualityComponent2);
                    * extra = 0.25;
                }
                case 3 -> {
                    Component iQualityComponent3 = Component.literal("[Crafted - Very Uncommon] "+name).withStyle(ChatFormatting.WHITE);
                    event.getCrafting().setHoverName(iQualityComponent3);
                    extra = 0.28;
                }
                case 4 -> {
                    Component iQualityComponent4 = Component.literal("[Crafted - Rare] "+name).withStyle(ChatFormatting.AQUA);
                    event.getCrafting().setHoverName(iQualityComponent4);
                    extra = 0.32;
                }
                case 5 -> {
                    Component iQualityComponent5 = Component.literal("[Crafted - Very Rare] "+name).withStyle(ChatFormatting.GREEN);
                    event.getCrafting().setHoverName(iQualityComponent5);
                    extra = 0.35;
                }
                case 6 -> {
                    Component iQualityComponent6 = Component.literal("[Crafted - Ultra Rare] "+name).withStyle(ChatFormatting.DARK_AQUA);
                    event.getCrafting().setHoverName(iQualityComponent6);
                    extra = 0.39;
                }
                case 7 -> {
                    Component iQualityComponent7 = Component.literal("[Crafted - Ultra Really Rare] "+name).withStyle(ChatFormatting.DARK_GREEN);
                    event.getCrafting().setHoverName(iQualityComponent7);
                    extra = 0.45;
                }
                case 8 -> {
                    Component iQualityComponent8 = Component.literal("[Crafted - Epic] "+name).withStyle(ChatFormatting.DARK_PURPLE);
                    event.getCrafting().setHoverName(iQualityComponent8);
                    extra = 0.55;
                }
                case 9 -> {
                    Component iQualityComponent9 = Component.literal("[Crafted - Legendary] "+name).withStyle(ChatFormatting.GOLD);
                    event.getCrafting().setHoverName(iQualityComponent9);
                    extra = 0.67;
                }
                case 10 -> {
                    Component iQualityComponent10 = Component.literal("[Crafted - Mythic] "+name).withStyle(ChatFormatting.LIGHT_PURPLE);
                    event.getCrafting().setHoverName(iQualityComponent10);
                    extra = 1.35;
                }
                case 11 -> {
                    Component iQualityComponent11 = Component.literal("[Crafted - GodLike] "+name).withStyle(ChatFormatting.RED);
                    event.getCrafting().setHoverName(iQualityComponent11);
                    extra = 2.125;
                }
            }

            if (event.getCrafting().getItem() instanceof BowItem) {
                entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(extraDamage -> {
                    var customDamage = (1 + (2+(extraDamage.get()*0.0015))*(4.15*0.35)*1.1)+(1.1*extra);

                    event.getCrafting().setDamageValue((int)customDamage);
                });
            }else if(event.getCrafting().getItem() instanceof CrossbowItem){
                entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(extraDamage -> {
                    var customDamage = (1 + (2+(extraDamage.get()*0.0015))*(4.15*0.35)*1.1)+(1.1*extra);
                    event.getCrafting().setDamageValue((int)customDamage);

                });
            }else if(event.getCrafting().getItem() instanceof TridentItem){
                entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(extraDamage -> {

                    var customDamage = ((1+(extraDamage.get()*0.0025))*(2.15*0.25)*1.9)++(1.9*extra);
                    event.getCrafting().setDamageValue((int)customDamage);
                });
            }else{
                event.getCrafting().getItem().appendHoverText(event.getCrafting(),event.getEntity().getLevel(),components,flag);
            }
        }
    }
     */
    @SubscribeEvent
    public static void onLivingHealEvent(LivingHealEvent event){
        if(event.getEntity() != null&& !event.getEntity().getLevel().isClientSide()){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onLivingEntityUpdate(LivingEvent.LivingTickEvent event){
        if(event.getEntity() != null && !event.getEntity().getLevel().isClientSide()){
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player || entity instanceof Monster || entity instanceof Animal) {
                entity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(customLevel ->{
                    entity.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(exp ->{

                        if(entity instanceof Player){
                            if(((Player)entity).experienceLevel < customLevel.get()){
                                int lvl = (customLevel.get() - ((Player)entity).experienceLevel);
                                ((Player) entity).giveExperienceLevels(lvl);
                            }
                        }

                        if(exp.get() >= customLevel.experienceNeeded()){
                            double need = customLevel.experienceNeeded();
                            exp.consume(need);
                            customLevel.add();
                            customLevel.setPreviousLevelExp(need);
                            if(entity instanceof Player){
                                ((Player) entity).giveExperienceLevels(1);
                                entity.sendSystemMessage(Component.translatable(MESSAGE_LEVEL_UP));
                                entity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life ->{
                                    life.increaseMax(0.025);
                                });

                                entity.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility ->{
                                    agility.add((agility.get()*0.000005));
                                });

                                entity.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana ->{
                                    mana.increaseMax(Math.random()+0.1);
                                });

                                entity.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(stat ->{
                                    stat.add(Math.random());
                                });

                                entity.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(stat ->{
                                    stat.add(Math.random());
                                });

                                entity.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(stat ->{
                                    stat.add(Math.random());
                                });

                                entity.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(stat ->{
                                    stat.add(Math.random());
                                });

                                entity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(stat ->{
                                    stat.add(0.0025);
                                });

                                entity.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(stat ->{
                                    stat.add(Math.random());
                                });

                                entity.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(stat ->{
                                    stat.add(0.0055);
                                });

                                entity.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(stat ->{
                                    stat.add(Math.random());
                                });
                            }
                        }
                    });
                });
                entity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(hp ->{
                    if(hp.get() <= 0){
                        entity.setHealth(0.0f);
                        entity.die(DamageSource.GENERIC);
                    }
                });
                entity.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent( mpRegen ->
                        entity.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                    if(mana.get() < mana.getMax()){
                        mana.add(mpRegen.get()*0.05);
                    }
                }));
                entity.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent( hpRegen ->
                        entity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life -> {
                    if(life.get() < life.getMax()){
                        life.add(hpRegen.get()*0.05);
                    }}));
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER){
            event.player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(stat ->{
                ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            /* **
            event.player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(stat ->{
                ModMessages.sendToPlayer(new AgilityDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(stat ->{
                ModMessages.sendToPlayer(new CommandDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(stat ->{
                ModMessages.sendToPlayer(new DefenseDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(stat ->{
                ModMessages.sendToPlayer(new DexterityDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(stat ->{
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(stat ->{
                ModMessages.sendToPlayer(new LifeRegenerationDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(stat ->{
                ModMessages.sendToPlayer(new LuckDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(stat ->{
                ModMessages.sendToPlayer(new ManaRegenerationDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(stat ->{
                ModMessages.sendToPlayer(new StrengthDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(stat ->{
                ModMessages.sendToPlayer(new CustomLevelDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
            event.player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(stat ->{
                ModMessages.sendToPlayer(new ExperienceDataSyncS2CPacket(stat.get()),((ServerPlayer)event.player));
            });
             */
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
                            double droppedExperience = event.getOriginalExperience();
                            double playerLevelValue = playerLevel.get();
                            double targetLevelValue = targetLevel.get();
                            double multiplier = 0.0;

                            if (playerLevelValue < targetLevelValue) {
                                multiplier = 1 + ((targetLevelValue - playerLevelValue) / 1000.0);
                            } else if (playerLevelValue - 10 <= targetLevelValue) {
                                multiplier = 1.0;
                            } else if (playerLevelValue - 1000 <= targetLevelValue) {
                                multiplier = 0.75;
                            } else if (playerLevelValue - 10000 <= targetLevelValue) {
                                multiplier = 0.4;
                            } else if (playerLevelValue - 20000 <= targetLevelValue) {
                                multiplier = 0.01;
                            }

                            double exp = droppedExperience * targetLevelValue;
                            customExp.add(exp * multiplier);
                            event.setDroppedExperience((int) (exp * multiplier));

                            if (multiplier == 0.0) {
                                entity.sendSystemMessage(Component.translatable(MESSAGE_LEVEL_TOO_HIGH).withStyle(ChatFormatting.DARK_RED));
                            }else{
                                entity.sendSystemMessage(Component.literal("Obtained experience: " + formatDouble(exp * multiplier)));
                            }
                    });
                });
            });
        }
    }
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()){
            if (!(event.getEntity() instanceof Player) && event.getEntity() instanceof LivingEntity livingEntity) {

                Level level = livingEntity.getLevel();
                long levelTime = level.getLevelData().getDayTime();
                int timeLevel = (int) (levelTime / 24000) / 5;
                double naturalSpawnX = level.getSharedSpawnPos().getX();
                double naturalSpawnZ = level.getSharedSpawnPos().getZ();
                int distanceLevel = (int) (Math.abs(livingEntity.getX() - naturalSpawnX) + Math.abs(livingEntity.getZ() - naturalSpawnZ)) / 100;
                Difficulty difficulty = level.getDifficulty();
                int difficultyLevel = switch (difficulty) {
                    case PEACEFUL -> 1;
                    case EASY -> 3;
                    case NORMAL -> 5;
                    case HARD -> 9;
                };
                int connectedPlayers = 1;
                if(level.players().size() != 0){
                    connectedPlayers = level.players().size();
                }
                int entityClass = 1;
                if(containsIgnoreCase(event.getEntity().getType().toString(),"dragon") ){
                    entityClass = 50;
                }else if(containsIgnoreCase(event.getEntity().getType().toString(), "wither")){
                    if(containsIgnoreCase(event.getEntity().getName().getString(),"skeleton")){
                        entityClass = 2;
                    }else{
                        entityClass = 25;
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
                                    lvls = (int)playerLevel.get();
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

                livingEntity.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(entityLevel ->{

                    livingEntity.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life ->{
                        Random random = new Random();
                        int randomNumber = random.nextInt(10) + 1;
                        entityLevel.setLevel((finalLevel-lvlReduce)+randomNumber);
                    });
                    event.getEntity().getCapability(LifeProvider.ENTITY_LIFE).ifPresent(life ->{
                        life.setMax(((LivingEntity) event.getEntity()).getHealth()+(entityLevel.get()*0.01));
                    });

                    event.getEntity().getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(agility ->{
                        agility.add(agility.get()+entityLevel.get()*0.0005);
                    });

                    event.getEntity().getCapability(ManaProvider.ENTITY_MANA).ifPresent(stat ->{
                        stat.increaseMax(entityLevel.get());
                    });

                    event.getEntity().getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(stat ->{
                        stat.add(entityLevel.get()*0.05);
                    });

                    event.getEntity().getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(stat ->{
                        stat.add(entityLevel.get()*0.01);
                    });

                    event.getEntity().getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(stat ->{
                        stat.add(entityLevel.get()*0.04);
                        event.getEntity().getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(hpRegen ->{
                            hpRegen.add((stat.get()*0.0025)+(entityLevel.get()*0.001));
                        });
                    });

                    event.getEntity().getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(stat ->{
                        stat.add(entityLevel.get()*0.04);
                        event.getEntity().getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(mpRegen ->{
                            mpRegen.add((stat.get()*0.001)+(entityLevel.get()*0.035));
                        });
                    });

                    event.getEntity().getCapability(LuckProvider.ENTITY_LUCK).ifPresent(stat ->{

                        stat.add(entityLevel.get()*0.1);
                    });

                    event.getEntity().getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(stat ->{
                        stat.add(entityLevel.get()*0.04);
                    });

                });
            }
            else if(event.getEntity() instanceof ServerPlayer player){
                player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                    ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.get()),player);
                });
                player.getCapability(LifeProvider.ENTITY_LIFE).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                /* **
                player.getCapability(AgilityProvider.ENTITY_AGILITY).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(CommandProvider.ENTITY_COMMAND).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(DefenseProvider.ENTITY_DEFENSE).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(DexterityProvider.ENTITY_DEXTERITY).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(IntelligenceProvider.ENTITY_INTELLIGENCE).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(LifeRegenerationProvider.ENTITY_LIFEREGENERATION).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(LuckProvider.ENTITY_LUCK).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(StrengthProvider.ENTITY_STRENGTH).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });
                player.getCapability(ExperienceProvider.ENTITY_EXPERIENCE).ifPresent(stat ->{
                    ModMessages.sendToPlayer(new LifeDataSyncS2CPacket(stat.get()),player);
                });

                 */
            }
        }
    }

}
