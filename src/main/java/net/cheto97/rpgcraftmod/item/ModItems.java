package net.cheto97.rpgcraftmod.item;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.custom.EspadaMuerteItem;
import net.cheto97.rpgcraftmod.custom.ModCreativeModeTab;
import net.cheto97.rpgcraftmod.custom.curios.auras.item.*;
import net.cheto97.rpgcraftmod.entity.ModEntityTypes;
import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.cheto97.rpgcraftmod.item.prefabs.CustomWingsItem;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS,RpgcraftMod.MOD_ID);

    //axes and battle-axes
    public static final RegistryObject<Item> hacha_zafiro = createAxe(selectTier(7),"hacha_zafiro",14,-3.0f);

    // pickaxes
    public static final RegistryObject<Item> SAPPHIRE_PICKAXE = createPickAxe(selectTier(7),"sapphire_pickaxe",2,-1.8f);

    // wings
    public static final RegistryObject<Item> ASURA_WINGS = createWings("asura_wings");
    public static final RegistryObject<Item> WING_1 = createWings("wings_1");
    public static final RegistryObject<Item> WING_2 = createWings("wings_2");
    public static final RegistryObject<Item> WING_3 = createWings("wings_3");
    public static final RegistryObject<Item> WING_4 = createWings("wings_4");
    public static final RegistryObject<Item> WING_5 = createWings("wings_5");
    public static final RegistryObject<Item> WING_6 = createWings("wings_6");
    public static final RegistryObject<Item> WING_7 = createWings("wings_7");
    public static final RegistryObject<Item> WING_8 = createWings("wings_8");
    public static final RegistryObject<Item> WING_9 = createWings("wings_9");
    public static final RegistryObject<Item> WING_10 = createWings("wings_10");
    public static final RegistryObject<Item> VOLT_WINGS = createWings("volt_wings");

    // swords
    public static final RegistryObject<Item> DARK_SWORD = createSword(selectTier(4),
            "sword_dark_queen",
            10,0.2f);
    public static final RegistryObject<Item> ASURA_SWORD = createSword(selectTier(5),
            "asura_sword",
            0,0);
    public static final RegistryObject<Item> espada_muerte = registerItems("espada_muerte",
            () -> new EspadaMuerteItem(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .durability(9999)
                    .rarity(Rarity.EPIC),
                    selectTier(6)
            ));
    public static final RegistryObject<Item> epicsword = createSword(selectTier(7),
            "epicsword",
            9,-2.2f);

    // auras
    public static final RegistryObject<Item> COMMON_AURA = registerItems("common_aura",
            () -> new Commonaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.COMMON)
                    .stacksTo(1)));

    public static final RegistryObject<Item> BOSS_AURA = registerItems("boss_aura",
            () -> new Bossaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)));

    public static final RegistryObject<Item> BRUTAL_AURA = registerItems("brutal_aura",
            () -> new Brutalaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)));

    public static final RegistryObject<Item> CHAMPION_AURA = registerItems("champion_aura",
            () -> new Championaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)));

    public static final RegistryObject<Item> DEMON_AURA = registerItems("demon_aura",
            () -> new Demonaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)));

    public static final RegistryObject<Item> ELITE_AURA = registerItems("elite_aura",
            () -> new Eliteaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)));

    public static final RegistryObject<Item> HERO_AURA = registerItems("hero_aura",
            () -> new Heroaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)));

    public static final RegistryObject<Item> LEGENDARY_AURA = registerItems("legendary_aura",
            () -> new Legendaryaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)));

    public static final RegistryObject<Item> MYTHICAL_AURA = registerItems("mythical_aura",
            () -> new Mythicalaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)));

    public static final RegistryObject<Item> SEMI_BOSS_AURA = registerItems("semi_boss_aura",
            () -> new Semibossaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)));

    public static final RegistryObject<Item> UNIQUE_AURA = registerItems("unique_aura",
            () -> new Uniqueaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)));


    // spawn eggs
    public static final RegistryObject<Item> MUTANT_GOLEM_SPAWN_EGG = createSpawnEgg("mutant_golem",ModEntityTypes.MUTANT_GOLEM,0xFFFFFF, 0x808080);
    public static final RegistryObject<Item> DRAKE_1_SPAWN_EGG = createSpawnEgg("drake_red", ModEntityTypes.DRAKE, 0xFF0000, 0x800000);
    public static final RegistryObject<Item> DRAKE_2_SPAWN_EGG = createSpawnEgg("drake_purple", ModEntityTypes.DRAKE_2, 0x8903C1, 0x483D8B);
    public static final RegistryObject<Item> DRAKE_3_SPAWN_EGG = createSpawnEgg("drake_dark_red", ModEntityTypes.DRAKE_3, 0x8B0000, 0x4F0005);
    public static final RegistryObject<Item> DRAKE_4_SPAWN_EGG = createSpawnEgg("drake_dark_purple", ModEntityTypes.DRAKE_4, 0x4B0082, 0x2C0065);
    public static final RegistryObject<Item> DRAKE_5_SPAWN_EGG = createSpawnEgg("drake_grey", ModEntityTypes.DRAKE_5, 0x808080, 0x696969);
    public static final RegistryObject<Item> DRAKE_6_SPAWN_EGG = createSpawnEgg("drake_aqua", ModEntityTypes.DRAKE_6, 0x00FFFF, 0x008B8B);
    public static final RegistryObject<Item> DRAKE_7_SPAWN_EGG = createSpawnEgg("drake_black", ModEntityTypes.DRAKE_7, 0x000000, 0x212121);
    public static final RegistryObject<Item> KOBOLD_WARRIOR_SPAWN_EGG = createSpawnEgg("kobold_warrior",ModEntityTypes.KOBOLD_WARRIOR,0x5E2F07, 0x22b341);

    // others
    public static final RegistryObject<Item> zafiro = registerItems("zafiro",
            () -> new Item(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(64)
            ));

    public static final RegistryObject<Item> ULTIMATE_COAL = registerItems("ultimate_coal",
            () -> new Item(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(64)
            ));

    public static final RegistryObject<Item> zafiro_crudo = registerItems("zafiro_crudo",
            () -> new Item(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(64)
            ));
    public static final RegistryObject<Item> BLUEBERRY_SEEDS = registerItems("blueberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_CROP.get(),
                    new Item.Properties().tab(ModCreativeModeTab.RPGCRAFT_TAB)));

    public static final RegistryObject<Item> BLUEBERRY = registerItems("blueberry",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .stacksTo(64)
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(3f).build())));

    public static final RegistryObject<Item> MANA_BUCKET = registerItems("mana_bucket",
            () -> new BucketItem(ModFluids.SOURCE_MANA, new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(32)
                    .craftRemainder(Items.BUCKET)
            ));

    private static <T extends Item> RegistryObject<T> registerItems(String name, Supplier<T> item){
        return ITEMS.register(name,item);
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    // builders
    private static RegistryObject<Item> createSpawnEgg(String entityName,Supplier<? extends EntityType<? extends Mob>> entityType,int backgroundColor, int highlightColor){
        return ITEMS.register(entityName+"_spawn_egg",
                () -> new ForgeSpawnEggItem(entityType, backgroundColor, highlightColor,
                        new Item.Properties()
                              .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .stacksTo(64)
                                .rarity(Rarity.UNCOMMON)));
    }
    private static RegistryObject<Item> createSword(Tier tier,String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new SwordItem(
                        tier,damage,attackSpeed,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createSword(String name,int damage, float attackSpeed){
        return createSword(selectTier(4),name,damage,attackSpeed);
    }
    private static RegistryObject<Item> createAxe(Tier tier,String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new AxeItem(
                        tier,damage,attackSpeed,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createPickAxe(Tier tier,String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new PickaxeItem(
                        tier,damage,attackSpeed,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createShovel(Tier tier,String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new ShovelItem(
                        tier,damage,attackSpeed,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createHoe(Tier tier,String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new HoeItem(
                        tier,damage,attackSpeed,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createBow(String name){
        return registerItems(name,
                () -> new BowItem(
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createCrossBow(String name){
        return registerItems(name,
                () -> new CrossbowItem(
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createTrident(String name){
        return registerItems(name,
                () -> new TridentItem(
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createWings(String name){
        return registerItems(name,
                () ->  new CustomWingsItem(name));
    }

    /*
    private static RegistryObject<Item> createDaggers(String name){
    return registerItems(name,
                () ->  new DaggersItem(name));
    }
    private static RegistryObject<Item> createScythes(String name){
    return registerItems(name,
                () ->  new ScythesItem(name));
    }
    private static RegistryObject<Item> createStaff(String name){
    return registerItems(name,
                () ->  new StaffItem(name));
    }
    private static RegistryObject<Item> createBattleAxe(String name){
    return registerItems(name,
                () ->  new BattleAxeItem(name));
    }
     */

    //selectors
    private static Tier selectTier(int tier){
        Tier selectedTier = Tiers.WOOD;

        switch (tier){
           case 2 -> selectedTier = Tiers.STONE;
           case 3 -> selectedTier = Tiers.GOLD;
           case 4 -> selectedTier = Tiers.IRON;
           case 5 -> selectedTier = Tiers.DIAMOND;
           case 6 -> selectedTier = Tiers.NETHERITE;
           case 7 -> selectedTier = ModToolTier.SAPPHIRE;
        }
        return selectedTier;
    }
}
