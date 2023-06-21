package net.cheto97.rpgcraftmod.item;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.custom.EspadaMuerteItem;
import net.cheto97.rpgcraftmod.custom.ModCreativeModeTab;
import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RpgcraftMod.MOD_ID);

    public static final RegistryObject<Item> zafiro = ITEMS.register("zafiro",
            () -> new Item(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .stacksTo(64)
            ));

    public static final RegistryObject<Item> zafiro_crudo = ITEMS.register("zafiro_crudo",
            () -> new Item(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(64)
            ));

    public static final RegistryObject<Item> hacha_zafiro = ITEMS.register("hacha_zafiro",
            () -> new AxeItem(
                    Tiers.NETHERITE,14.0f,-2.0f,
                    new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .durability(9999)
                    .rarity(Rarity.EPIC)
            ));

    public static final RegistryObject<Item> epicsword = ITEMS.register("epicsword",
            () -> new SwordItem(
                    Tiers.DIAMOND,12,2.2f,
                    new Item.Properties()
                            .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                            .fireResistant()
                            .durability(9999)
                            .rarity(Rarity.EPIC)
            ));

    public static final RegistryObject<Item> espada_muerte = ITEMS.register("espada_muerte",
            () -> new EspadaMuerteItem(new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .durability(9999)
                    .rarity(Rarity.EPIC),
                    Tiers.NETHERITE
            ));

    public static final RegistryObject<Item> BLUEBERRY_SEEDS = ITEMS.register("blueberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_CROP.get(),
                    new Item.Properties().tab(ModCreativeModeTab.RPGCRAFT_TAB)));

    public static final RegistryObject<Item> BLUEBERRY = ITEMS.register("blueberry",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .stacksTo(64)
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(3f).build())));

    public static final RegistryObject<Item> MANA_BUCKET = ITEMS.register("mana_bucket",
            () -> new BucketItem(ModFluids.SOURCE_MANA, new Item.Properties()
                    .tab(ModCreativeModeTab.RPGCRAFT_TAB)
                    .fireResistant()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)
                    .craftRemainder(Items.BUCKET)
            ));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
