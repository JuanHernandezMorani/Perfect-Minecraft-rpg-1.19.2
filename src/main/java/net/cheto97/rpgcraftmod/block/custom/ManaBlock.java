package net.cheto97.rpgcraftmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class ManaBlock extends Block {
    public ManaBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
       if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
           player.sendSystemMessage(Component.literal("Activando gran regeneracion temporal"));
           player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200,3));
       }
        return super.use(state, level, pos, player, hand, blockHitResult);
    }
}
