package net.cheto97.rpgcraftmod.item;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.custom.EspadaMuerteItem;
import net.cheto97.rpgcraftmod.custom.ModCreativeModeTab;
import net.cheto97.rpgcraftmod.custom.curios.auras.item.*;
import net.cheto97.rpgcraftmod.entity.ModEntityTypes;
import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.cheto97.rpgcraftmod.item.wings.WingItem;

import net.cheto97.rpgcraftmod.util.ColorUtil;
import net.cheto97.rpgcraftmod.util.ModChatFormatting;
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

import static net.cheto97.rpgcraftmod.util.ColorUtil.getCustomItemColor;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS,RpgcraftMod.MOD_ID);

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

    public static final RegistryObject<Item> hacha_zafiro = createAxe("hacha_zafiro",14,-3.0f);

    public static final RegistryObject<Item> epicsword = createSword("epicsword",9,-2.2f);

    public static final RegistryObject<Item> SAPPHIRE_PICKAXE = createPickAxe("sapphire_pickaxe",2,-1.8f);

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

    public static final RegistryObject<Item> DARK_SWORD = createSword("sword_dark_queen",10,0.2f);
    public static final RegistryObject<Item> ASURA_SWORD = createSword("asura_sword",15,-0.9f);
    public static final RegistryObject<Item> espada_muerte = registerItems("espada_muerte",
            () -> new EspadaMuerteItem(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .durability(9999)
                    .rarity(Rarity.EPIC),
                    Tiers.NETHERITE
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

    // AURAS
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

    // WINGS
    public static final RegistryObject<Item> WHITE_LIGHT_WINGS = registerItems("white_light_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> ORANGE_LIGHT_WINGS = registerItems("orange_light_wings", () -> new WingItem(DyeColor.ORANGE, DyeColor.ORANGE, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> MAGENTA_LIGHT_WINGS = registerItems("magenta_light_wings", () -> new WingItem(DyeColor.MAGENTA, DyeColor.MAGENTA, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> LIGHT_BLUE_LIGHT_WINGS = registerItems("light_blue_light_wings", () -> new WingItem(DyeColor.LIGHT_BLUE, DyeColor.LIGHT_BLUE, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> YELLOW_LIGHT_WINGS = registerItems("yellow_light_wings", () -> new WingItem(DyeColor.YELLOW, DyeColor.YELLOW, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> LIME_LIGHT_WINGS = registerItems("lime_light_wings", () -> new WingItem(DyeColor.LIME, DyeColor.LIME, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> PINK_LIGHT_WINGS = registerItems("pink_light_wings", () -> new WingItem(DyeColor.PINK, DyeColor.PINK, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> GREY_LIGHT_WINGS = registerItems("grey_light_wings", () -> new WingItem(DyeColor.GRAY, DyeColor.GRAY, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> LIGHT_GREY_LIGHT_WINGS = registerItems("light_grey_light_wings", () -> new WingItem(DyeColor.LIGHT_GRAY, DyeColor.LIGHT_GRAY, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> CYAN_LIGHT_WINGS = registerItems("cyan_light_wings", () -> new WingItem(DyeColor.CYAN, DyeColor.CYAN, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> PURPLE_LIGHT_WINGS = registerItems("purple_light_wings", () -> new WingItem(DyeColor.PURPLE, DyeColor.PURPLE, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> BLUE_LIGHT_WINGS = registerItems("blue_light_wings", () -> new WingItem(DyeColor.BLUE, DyeColor.BLUE, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> BROWN_LIGHT_WINGS = registerItems("brown_light_wings", () -> new WingItem(DyeColor.BROWN, DyeColor.BROWN, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> GREEN_LIGHT_WINGS = registerItems("green_light_wings", () -> new WingItem(DyeColor.GREEN, DyeColor.GREEN, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> RED_LIGHT_WINGS = registerItems("red_light_wings", () -> new WingItem(DyeColor.RED, DyeColor.RED, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> BLACK_LIGHT_WINGS = registerItems("black_light_wings", () -> new WingItem(DyeColor.BLACK, DyeColor.BLACK, WingItem.WingType.LIGHT));
    public static final RegistryObject<Item> ZANZAS_WINGS = registerItems("zanzas_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.UNIQUE));

    // SPAWN EGGS
    public static final RegistryObject<Item> MUTANT_GOLEM_SPAWN_EGG = createSpawnEgg("mutant_golem",ModEntityTypes.MUTANT_GOLEM,0xFFFFFF, 0x808080);
    public static final RegistryObject<Item> DRAKE_1_SPAWN_EGG = createSpawnEgg("drake_red", ModEntityTypes.DRAKE, 0xFF0000, 0x800000);
    public static final RegistryObject<Item> DRAKE_2_SPAWN_EGG = createSpawnEgg("drake_purple", ModEntityTypes.DRAKE_2, 0x800080, 0x4B0082);
    public static final RegistryObject<Item> DRAKE_3_SPAWN_EGG = createSpawnEgg("drake_dark_red", ModEntityTypes.DRAKE_3, 0x8B0000, 0x4F0005);
    public static final RegistryObject<Item> DRAKE_4_SPAWN_EGG = createSpawnEgg("drake_dark_purple", ModEntityTypes.DRAKE_4, 0x483D8B, 0x00008B);
    public static final RegistryObject<Item> DRAKE_5_SPAWN_EGG = createSpawnEgg("drake_grey", ModEntityTypes.DRAKE_5, 0x808080, 0x696969);
    public static final RegistryObject<Item> DRAKE_6_SPAWN_EGG = createSpawnEgg("drake_aqua", ModEntityTypes.DRAKE_6, 0x00FFFF, 0x008B8B);
    public static final RegistryObject<Item> DRAKE_7_SPAWN_EGG = createSpawnEgg("drake_black", ModEntityTypes.DRAKE_7, 0x000000, 0x212121);

    public static final RegistryObject<Item> KOBOLD_WARRIOR_SPAWN_EGG = createSpawnEgg("kobold_warrior",ModEntityTypes.KOBOLD_WARRIOR,0x19732e, 0x22b341);
    private static <T extends Item> RegistryObject<T> registerItems(String name, Supplier<T> item){
        return ITEMS.register(name,item);
    }
    private static RegistryObject<Item> createSpawnEgg(String entityName,Supplier<? extends EntityType<? extends Mob>> entityType,int backgroundColor, int highlightColor){
        return ITEMS.register(entityName+"_spawn_egg",
                () -> new ForgeSpawnEggItem(entityType, backgroundColor, highlightColor,
                        new Item.Properties()
                              .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .stacksTo(64)
                                .rarity(Rarity.UNCOMMON)));
    }
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
    private static RegistryObject<Item> createSword(String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new SwordItem(
                        ModToolTier.SAPPHIRE,damage,attackSpeed,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createAxe(String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new AxeItem(
                        ModToolTier.SAPPHIRE,damage,attackSpeed,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createPickAxe(String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new PickaxeItem(
                        ModToolTier.SAPPHIRE,damage,attackSpeed,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createShovel(String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new ShovelItem(
                        ModToolTier.SAPPHIRE,damage,attackSpeed,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(4500)
                ));
    }
    private static RegistryObject<Item> createHoe(String name,int damage, float attackSpeed){
        return registerItems(name,
                () -> new HoeItem(
                        ModToolTier.SAPPHIRE,damage,attackSpeed,
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
                () -> new ElytraItem(
                        new Item.Properties()
                                .tab(ModCreativeModeTab.RPGCRAFT_WINGS_TAB)
                                .fireResistant()
                                .rarity(Rarity.EPIC)
                                .stacksTo(1)
                                .durability(20000)
                ));
    }

}
