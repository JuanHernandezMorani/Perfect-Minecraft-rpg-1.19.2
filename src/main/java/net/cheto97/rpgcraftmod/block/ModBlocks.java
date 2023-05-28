package net.cheto97.rpgcraftmod.block;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.custom.BlueberryCropBlock;
import net.cheto97.rpgcraftmod.block.custom.MagicLampBlock;
import net.cheto97.rpgcraftmod.block.custom.ManaBlock;
import net.cheto97.rpgcraftmod.custom.ModCreativeModeTab;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RpgcraftMod.MOD_ID);

    public static final RegistryObject<Block> bloque_mineral_zafiro = registerBlock("bloque_mineral_zafiro",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .explosionResistance(99.9f)
                    .sound(SoundType.ANCIENT_DEBRIS)
                    .strength(6f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(5,15)
            ));

    public static final RegistryObject<Block> bloque_profundo_mineral_zafiro = registerBlock("bloque_profundo_mineral_zafiro",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .explosionResistance(99.9f)
                    .sound(SoundType.POLISHED_DEEPSLATE)
                    .strength(6f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(5,15)
            ));

    public static final RegistryObject<Block> bloque_zafiro = registerBlock("bloque_zafiro",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .explosionResistance(99.9f)
                    .sound(SoundType.COPPER)
                    .strength(6f)
                    .requiresCorrectToolForDrops()
            ));

    public static final RegistryObject<Block> bloque_mana = registerBlock("bloque_mana",
            () -> new ManaBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .explosionResistance(99.9f)
                    .sound(SoundType.LARGE_AMETHYST_BUD)
                    .strength(2f)
                    .requiresCorrectToolForDrops()
            ));
    public static final RegistryObject<Block> bloque_luz_magica = registerBlock("bloque_luz_magica",
            () -> new MagicLampBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT)
                    .explosionResistance(99.9f)
                    .sound(SoundType.LARGE_AMETHYST_BUD)
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(MagicLampBlock.LIT) ? 15 : 0)
            ));
    public static final RegistryObject<Block> BLUEBERRY_CROP = BLOCKS.register("blueberry_crop",
            () -> new BlueberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn, ModCreativeModeTab.RPGCRAFT_TAB);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
