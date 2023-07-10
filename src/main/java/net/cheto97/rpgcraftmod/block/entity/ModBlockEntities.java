package net.cheto97.rpgcraftmod.block.entity;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.screen.renderer.tile.ToolLevelingTableRenderer;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.Names;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.cheto97.rpgcraftmod.block.ModBlocks.TLT_BLOCK;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
    DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RpgcraftMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<WizardTableBlockEntity>> WIZARD_TABLE =
            BLOCK_ENTITIES.register("wizard_table", () ->
        BlockEntityType.Builder.of(WizardTableBlockEntity::new,
                ModBlocks.wizard_table.get()).build(null));

    public static final RegistryObject<BlockEntityType<GemInfusingStationBlockEntity>> GEM_INFUSING_STATION =
            BLOCK_ENTITIES.register("gem_infusing_station", () ->
                    BlockEntityType.Builder.of(GemInfusingStationBlockEntity::new,
                            ModBlocks.GEM_INFUSING_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<ToolLevelingTableBlockEntity>> TLT_TILE_ENTITY =
            BLOCK_ENTITIES.register(Names.TABLE, () ->
                    BlockEntityType.Builder.of(ToolLevelingTableBlockEntity::new, TLT_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
