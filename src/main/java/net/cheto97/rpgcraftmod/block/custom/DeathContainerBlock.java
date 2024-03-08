package net.cheto97.rpgcraftmod.block.custom;

import net.cheto97.rpgcraftmod.util.Dropper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeathContainerBlock extends Block {
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public DeathContainerBlock(Properties properties) {
        super(properties);
    }
    private final List<ItemStack> items = new ArrayList<>();
    public void add(ItemStack item){
        items.add(item);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
       if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND && !items.isEmpty()){
           for(ItemStack item : items) Dropper.dropInWorld(item,level,pos);
           player.sendSystemMessage(Component.literal("Death chest open by "+player.getName()).withStyle(ChatFormatting.AQUA));
           level.destroyBlock(pos,true);
       }

        return super.use(state, level, pos, player, hand, result);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

}