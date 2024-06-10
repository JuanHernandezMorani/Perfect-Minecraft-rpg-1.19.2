package net.cheto97.rpgcraftmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DeathContainerBlockEntity  extends BlockEntity {
    public DeathContainerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.DEATH_CONTAINER_BLOCK_ENTITY.get(), blockPos, blockState);
    }
}
