package net.cheto97.rpgcraftmod.register_renderers;

import net.cheto97.rpgcraftmod.block.entity.ModBlockEntities;
import net.cheto97.rpgcraftmod.block.entity.renderer.CraftingTableBlockEntityRenderer;
import net.cheto97.rpgcraftmod.block.entity.renderer.DeathContainerBlockEntityRenderer;
import net.cheto97.rpgcraftmod.block.entity.renderer.GemInfusingStationBlockEntityRenderer;
import net.cheto97.rpgcraftmod.block.entity.renderer.WizardTableBlockEntityRenderer;
import net.cheto97.rpgcraftmod.screen.renderer.tile.ToolLevelingTableRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class BlockEntityRender {
    public static void register(){
        BlockEntityRenderers.register(ModBlockEntities.TLT_TILE_ENTITY.get(), ToolLevelingTableRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.GEM_INFUSING_STATION.get(), GemInfusingStationBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.WIZARD_TABLE.get(), WizardTableBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.CRAFTING_TABLE_BLOCK_ENTITY.get(), CraftingTableBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.DEATH_CONTAINER_BLOCK_ENTITY.get(), DeathContainerBlockEntityRenderer::new);

    }
}
