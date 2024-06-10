package net.cheto97.rpgcraftmod.block;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.custom.chests.DeathContainerBlock;
import net.cheto97.rpgcraftmod.block.custom.crops.BlueberryCropBlock;
import net.cheto97.rpgcraftmod.block.custom.lamps.MagicLampBlock;
import net.cheto97.rpgcraftmod.block.custom.others.ManaBlock;
import net.cheto97.rpgcraftmod.block.custom.others.ModFlammableRotatedPillarBlock;
import net.cheto97.rpgcraftmod.block.custom.stations.GemInfusingStationBlock;
import net.cheto97.rpgcraftmod.block.custom.stations.ToolLevelingTableBlock;
import net.cheto97.rpgcraftmod.block.custom.stations.WizardTableBlock;
import net.cheto97.rpgcraftmod.custom.ModCreativeModeTab;
import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.Names;
import net.cheto97.rpgcraftmod.world.feature.tree.RedMapleTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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
                    .sound(SoundType.MEDIUM_AMETHYST_BUD)
                    .strength(2f)
                    .requiresCorrectToolForDrops()
            ));

    public static final RegistryObject<Block> bloque_luz_magica = registerMagicLamp("bloque_luz_magica",
            () -> new MagicLampBlock(BlockBehaviour.Properties.of(Material.FROGLIGHT)
            .explosionResistance(99.9f)
            .sound(SoundType.LARGE_AMETHYST_BUD)
            .strength(2f)
            .lightLevel(state -> state.getValue(MagicLampBlock.LIT) ? 15 : 0)
    ));

    public static final RegistryObject<Block> DEATH_CONTAINER = registerBlock("death_container",
            () -> new DeathContainerBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .explosionResistance(50f)
                    .sound(SoundType.ANCIENT_DEBRIS)
                    .strength(1.3f)
                    .lightLevel(state -> state.getValue(DeathContainerBlock.LIT) ? 8 : 3)
            ));
    public static final RegistryObject<Block> wizard_table = registerBlock("wizard_table",
            () -> new WizardTableBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .explosionResistance(99.9f)
                    .sound(SoundType.COPPER)
                    .strength(6f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
            ));

    public static final RegistryObject<Block> GEM_INFUSING_STATION = registerBlock("gem_infusing_station",
            () -> new GemInfusingStationBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> BLUEBERRY_CROP = BLOCKS.register("blueberry_crop",
            () -> new BlueberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    public static final RegistryObject<LiquidBlock> LIQUID_MANA_BLOCK = BLOCKS.register( "liquid_mana_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MANA,
                    BlockBehaviour.Properties.of(Material.WATER)
                            .noCollission().strength(100.0F).noLootTable()
                    .noCollission()
                    .lightLevel(s -> 7)));

    public static final RegistryObject<Block> RED_MAPLE_LOG = registerBlock("red_maple_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RED_MAPLE_WOOD = registerBlock("red_maple_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STRIPPED_RED_MAPLE_LOG = registerBlock("stripped_red_maple_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STRIPPED_RED_MAPLE_WOOD = registerBlock("stripped_red_maple_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> RED_MAPLE_PLANKS = registerBlock("red_maple_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .requiresCorrectToolForDrops()) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }
            });

    public static final RegistryObject<Block> RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                    .requiresCorrectToolForDrops()){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }
            });

    public static final RegistryObject<Block> RED_MAPLE_SAPLING = registerBlock("red_maple_sapling",
            () -> new SaplingBlock(new RedMapleTreeGrower(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)
                            .lightLevel(s -> 5)));
    
    public static final RegistryObject<Block> TLT_BLOCK = registerBlock(Names.TABLE, ToolLevelingTableBlock::new);




    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn, ModCreativeModeTab.RPGCRAFT_TAB);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block>RegistryObject<T> registerMagicLamp(String name, Supplier<T> block){
        return registerBlock(name,block);
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
