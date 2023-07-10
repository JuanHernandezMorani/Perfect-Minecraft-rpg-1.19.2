package net.cheto97.rpgcraftmod.item;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.custom.EspadaMuerteItem;
import net.cheto97.rpgcraftmod.custom.ModCreativeModeTab;
import net.cheto97.rpgcraftmod.custom.curios.auras.item.*;
import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.cheto97.rpgcraftmod.item.wings.WingItem;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.Names;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.cheto97.rpgcraftmod.block.ModBlocks.TLT_BLOCK;

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

    public static final RegistryObject<Item> hacha_zafiro = registerItems("hacha_zafiro",
            () -> new AxeItem(
                    Tiers.NETHERITE,14.0f,-2.0f,
                    new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .durability(9999)
                    .rarity(Rarity.EPIC)
            ));

    public static final RegistryObject<Item> epicsword = registerItems("epicsword",
            () -> new SwordItem(
                    Tiers.DIAMOND,12,2.2f,
                    new Item.Properties()
                            .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                            .fireResistant()
                            .durability(9999)
                            .rarity(Rarity.EPIC)
            ));

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
                    .stacksTo(1)
                    .craftRemainder(Items.BUCKET)
            ));

    public static final RegistryObject<Item> COMMON_AURA = registerItems("commonaura",
            () -> new Commonaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.COMMON)
                    .stacksTo(1)));

    public static final RegistryObject<Item> BOSS_AURA = registerItems("bossaura",
            () -> new Bossaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)));

    public static final RegistryObject<Item> BRUTAL_AURA = registerItems("brutalaura",
            () -> new Brutalaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)));

    public static final RegistryObject<Item> CHAMPION_AURA = registerItems("championaura",
            () -> new Championaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)));

    public static final RegistryObject<Item> DEMON_AURA = registerItems("demonaura",
            () -> new Demonaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)));

    public static final RegistryObject<Item> ELITE_AURA = registerItems("eliteaura",
            () -> new Eliteaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)));

    public static final RegistryObject<Item> HERO_AURA = registerItems("heroaura",
            () -> new Heroaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)));

    public static final RegistryObject<Item> LEGENDARY_AURA = registerItems("legendaryaura",
            () -> new Legendaryaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)));

    public static final RegistryObject<Item> MYTHICAL_AURA = registerItems("mythicalaura",
            () -> new Mythicalaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)));

    public static final RegistryObject<Item> SEMI_BOSS_AURA = registerItems("semibossaura",
            () -> new Semibossaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)));

    public static final RegistryObject<Item> UNIQUE_AURA = registerItems("uniqueaura",
            () -> new Uniqueaura(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)));

    public static final RegistryObject<Item> WHITE_FEATHERED_WINGS = registerItems("white_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> ORANGE_FEATHERED_WINGS = registerItems("orange_feathered_wings", () -> new WingItem(DyeColor.ORANGE, DyeColor.ORANGE, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> MAGENTA_FEATHERED_WINGS = registerItems("magenta_feathered_wings", () -> new WingItem(DyeColor.MAGENTA, DyeColor.MAGENTA, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> LIGHT_BLUE_FEATHERED_WINGS = registerItems("light_blue_feathered_wings", () -> new WingItem(DyeColor.LIGHT_BLUE, DyeColor.LIGHT_BLUE, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> YELLOW_FEATHERED_WINGS = registerItems("yellow_feathered_wings", () -> new WingItem(DyeColor.YELLOW, DyeColor.YELLOW, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> LIME_FEATHERED_WINGS = registerItems("lime_feathered_wings", () -> new WingItem(DyeColor.LIME, DyeColor.LIME, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> PINK_FEATHERED_WINGS = registerItems("pink_feathered_wings", () -> new WingItem(DyeColor.PINK, DyeColor.PINK, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> GREY_FEATHERED_WINGS = registerItems("grey_feathered_wings", () -> new WingItem(DyeColor.GRAY, DyeColor.GRAY, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> LIGHT_GREY_FEATHERED_WINGS = registerItems("light_grey_feathered_wings", () -> new WingItem(DyeColor.LIGHT_GRAY, DyeColor.LIGHT_GRAY, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> CYAN_FEATHERED_WINGS = registerItems("cyan_feathered_wings", () -> new WingItem(DyeColor.CYAN, DyeColor.CYAN, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> PURPLE_FEATHERED_WINGS = registerItems("purple_feathered_wings", () -> new WingItem(DyeColor.PURPLE, DyeColor.PURPLE, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> BLUE_FEATHERED_WINGS = registerItems("blue_feathered_wings", () -> new WingItem(DyeColor.BLUE, DyeColor.BLUE, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> BROWN_FEATHERED_WINGS = registerItems("brown_feathered_wings", () -> new WingItem(DyeColor.BROWN, DyeColor.BROWN, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> GREEN_FEATHERED_WINGS = registerItems("green_feathered_wings", () -> new WingItem(DyeColor.GREEN, DyeColor.GREEN, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> RED_FEATHERED_WINGS = registerItems("red_feathered_wings", () -> new WingItem(DyeColor.RED, DyeColor.RED, WingItem.WingType.FEATHERED));
    public static final RegistryObject<Item> BLACK_FEATHERED_WINGS = registerItems("black_feathered_wings", () -> new WingItem(DyeColor.BLACK, DyeColor.BLACK, WingItem.WingType.FEATHERED));

    public static final RegistryObject<Item> WHITE_DRAGON_WINGS = registerItems("white_dragon_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> ORANGE_DRAGON_WINGS = registerItems("orange_dragon_wings", () -> new WingItem(DyeColor.ORANGE, DyeColor.ORANGE, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> MAGENTA_DRAGON_WINGS = registerItems("magenta_dragon_wings", () -> new WingItem(DyeColor.MAGENTA, DyeColor.PINK, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> LIGHT_BLUE_DRAGON_WINGS = registerItems("light_blue_dragon_wings", () -> new WingItem(DyeColor.LIGHT_BLUE, DyeColor.WHITE, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> YELLOW_DRAGON_WINGS = registerItems("yellow_dragon_wings", () -> new WingItem(DyeColor.YELLOW, DyeColor.YELLOW, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> LIME_DRAGON_WINGS = registerItems("lime_dragon_wings", () -> new WingItem(DyeColor.LIME, DyeColor.PINK, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> PINK_DRAGON_WINGS = registerItems("pink_dragon_wings", () -> new WingItem(DyeColor.PINK, DyeColor.WHITE, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> GREY_DRAGON_WINGS = registerItems("grey_dragon_wings", () -> new WingItem(DyeColor.GRAY, DyeColor.LIGHT_GRAY, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> LIGHT_GREY_DRAGON_WINGS = registerItems("light_grey_dragon_wings", () -> new WingItem(DyeColor.LIGHT_GRAY, DyeColor.WHITE, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> CYAN_DRAGON_WINGS = registerItems("cyan_dragon_wings", () -> new WingItem(DyeColor.CYAN, DyeColor.LIGHT_BLUE, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> PURPLE_DRAGON_WINGS = registerItems("purple_dragon_wings", () -> new WingItem(DyeColor.PURPLE, DyeColor.MAGENTA, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> BLUE_DRAGON_WINGS = registerItems("blue_dragon_wings", () -> new WingItem(DyeColor.BLUE, DyeColor.LIGHT_BLUE, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> BROWN_DRAGON_WINGS = registerItems("brown_dragon_wings", () -> new WingItem(DyeColor.BROWN, DyeColor.ORANGE, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> GREEN_DRAGON_WINGS = registerItems("green_dragon_wings", () -> new WingItem(DyeColor.GREEN, DyeColor.LIME, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> RED_DRAGON_WINGS = registerItems("red_dragon_wings", () -> new WingItem(DyeColor.RED, DyeColor.YELLOW, WingItem.WingType.DRAGON));
    public static final RegistryObject<Item> BLACK_DRAGON_WINGS = registerItems("black_dragon_wings", () -> new WingItem(DyeColor.BLACK, DyeColor.PURPLE, WingItem.WingType.DRAGON));

    public static final RegistryObject<Item> WHITE_MECHANICAL_FEATHERED_WINGS = registerItems("white_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> ORANGE_MECHANICAL_FEATHERED_WINGS = registerItems("orange_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.ORANGE, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> MAGENTA_MECHANICAL_FEATHERED_WINGS = registerItems("magenta_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.MAGENTA, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> LIGHT_BLUE_MECHANICAL_FEATHERED_WINGS = registerItems("light_blue_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.LIGHT_BLUE, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> YELLOW_MECHANICAL_FEATHERED_WINGS = registerItems("yellow_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.YELLOW, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> LIME_MECHANICAL_FEATHERED_WINGS = registerItems("lime_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.LIME, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> PINK_MECHANICAL_FEATHERED_WINGS = registerItems("pink_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.PINK, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> GREY_MECHANICAL_FEATHERED_WINGS = registerItems("grey_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.GRAY, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> LIGHT_GREY_MECHANICAL_FEATHERED_WINGS = registerItems("light_grey_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.LIGHT_GRAY, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> CYAN_MECHANICAL_FEATHERED_WINGS = registerItems("cyan_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.CYAN, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> PURPLE_MECHANICAL_FEATHERED_WINGS = registerItems("purple_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.PURPLE, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> BLUE_MECHANICAL_FEATHERED_WINGS = registerItems("blue_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.BLUE, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> BROWN_MECHANICAL_FEATHERED_WINGS = registerItems("brown_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.BROWN, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> GREEN_MECHANICAL_FEATHERED_WINGS = registerItems("green_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.GREEN, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> RED_MECHANICAL_FEATHERED_WINGS = registerItems("red_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.RED, WingItem.WingType.MECHANICAL_FEATHERED));
    public static final RegistryObject<Item> BLACK_MECHANICAL_FEATHERED_WINGS = registerItems("black_mechanical_feathered_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.BLACK, WingItem.WingType.MECHANICAL_FEATHERED));

    public static final RegistryObject<Item> WHITE_MECHANICAL_LEATHER_WINGS = registerItems("white_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> ORANGE_MECHANICAL_LEATHER_WINGS = registerItems("orange_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.ORANGE, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> MAGENTA_MECHANICAL_LEATHER_WINGS = registerItems("magenta_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.MAGENTA, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> LIGHT_BLUE_MECHANICAL_LEATHER_WINGS = registerItems("light_blue_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.LIGHT_BLUE, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> YELLOW_MECHANICAL_LEATHER_WINGS = registerItems("yellow_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.YELLOW, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> LIME_MECHANICAL_LEATHER_WINGS = registerItems("lime_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.LIME, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> PINK_MECHANICAL_LEATHER_WINGS = registerItems("pink_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.PINK, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> GREY_MECHANICAL_LEATHER_WINGS = registerItems("grey_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.GRAY, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> LIGHT_GREY_MECHANICAL_LEATHER_WINGS = registerItems("light_grey_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.LIGHT_GRAY, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> CYAN_MECHANICAL_LEATHER_WINGS = registerItems("cyan_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.CYAN, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> PURPLE_MECHANICAL_LEATHER_WINGS = registerItems("purple_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.PURPLE, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> BLUE_MECHANICAL_LEATHER_WINGS = registerItems("blue_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.BLUE, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> BROWN_MECHANICAL_LEATHER_WINGS = registerItems("brown_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.BROWN, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> GREEN_MECHANICAL_LEATHER_WINGS = registerItems("green_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.GREEN, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> RED_MECHANICAL_LEATHER_WINGS = registerItems("red_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.RED, WingItem.WingType.MECHANICAL_LEATHER));
    public static final RegistryObject<Item> BLACK_MECHANICAL_LEATHER_WINGS = registerItems("black_mechanical_leather_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.BLACK, WingItem.WingType.MECHANICAL_LEATHER));

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

    public static final RegistryObject<Item> FLANDRES_WINGS = registerItems("flandres_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.UNIQUE));
    public static final RegistryObject<Item> DISCORDS_WINGS = registerItems("discords_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.UNIQUE));
    public static final RegistryObject<Item> ZANZAS_WINGS = registerItems("zanzas_wings", () -> new WingItem(DyeColor.WHITE, DyeColor.WHITE, WingItem.WingType.UNIQUE));




    private static <T extends Item> RegistryObject<T> registerItems(String name, Supplier<T> block){
        RegistryObject<T> toReturn = ITEMS.register(name,block);
        return toReturn;
    }
    
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
