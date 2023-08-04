package net.cheto97.rpgcraftmod.util.OV;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.custom.ModCreativeModeTab;
import net.cheto97.rpgcraftmod.item.OV.WildfireHelmetItem;
import net.cheto97.rpgcraftmod.item.OV.WildfireShieldItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemsOV {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RpgcraftMod.MOD_ID);

    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ModCreativeModeTab.RPGCRAFT_TAB).fireResistant();

    // Spawn Eggs
    public static final RegistryObject<ForgeSpawnEggItem> WILDFIRE_SPAWN_EGG = ITEMS.register("wildfire_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.WILDFIRE, 0xF6B201, 0x000000, ITEM_PROPERTIES.stacksTo(64)));
    public static final RegistryObject<ForgeSpawnEggItem> GLUTTON_SPAWN_EGG = ITEMS.register("glutton_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GLUTTON, 0xF0D786, 0x000000, ITEM_PROPERTIES.stacksTo(64)));
    public static final RegistryObject<ForgeSpawnEggItem> BARNACLE_SPAWN_EGG = ITEMS.register("barnacle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BARNACLE, 0x5B872E, 0x000000, ITEM_PROPERTIES.stacksTo(64)));
    // Items
    public static final RegistryObject<Item> WILDFIRE_HELMET = ITEMS.register("wildfire_helmet", WildfireHelmetItem::new);
    public static final RegistryObject<Item> WILDFIRE_SHIELD = ITEMS.register("wildfire_shield", WildfireShieldItem::new);
    public static final RegistryObject<Item> WILDFIRE_SHIELD_PART = ITEMS.register("wildfire_shield_part", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> WILDFIRE_PIECE = ITEMS.register("wildfire_piece", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> VOID_HEART = ITEMS.register("void_heart", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> BARNACLE_TOOTH = ITEMS.register("barnacle_tooth", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> PRISMARINE_ROD = ITEMS.register("prismarine_rod", () -> new Item(ITEM_PROPERTIES));}
