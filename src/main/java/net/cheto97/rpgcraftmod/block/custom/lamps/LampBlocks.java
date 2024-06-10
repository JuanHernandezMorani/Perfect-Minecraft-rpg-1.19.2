package net.cheto97.rpgcraftmod.block.custom.lamps;

import net.cheto97.rpgcraftmod.util.ModChatFormatting;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class LampBlocks extends Block {
    private Integer color = 0xC0C0C0;
    protected abstract String getSystemMessage();
    protected abstract String getEffectDescription();
    protected abstract ChatFormatting getSystemMessageStyle();
    protected abstract Integer getHex();
    protected abstract MobEffect getEffect();
    protected abstract int getLevel();
    protected abstract int getDuration();
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public LampBlocks(Properties p) {
        super(p);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            level.setBlock(pos, state.cycle(LIT), 3);

            if(isOn(state) && hasEffect()) activeEffect(player);
        }

        return super.use(state, level, pos, player, hand, result);
    }
    private void activeEffect(Player player){
        player.sendSystemMessage(Component.literal(getSystemMessage()).withStyle(getSystemMessageStyle()));
        player.addEffect(new MobEffectInstance(getEffect(), getDuration(),getLevel()));
    }

    private boolean isOn(BlockState state){
        return state.getValue(LIT);
    }
    private boolean hasEffect(){
        return getEffect() != null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter getter, @NotNull List<Component> list, @NotNull TooltipFlag tooltip) {
        color = getDuration() < 120 ? setStyle(0xFFC0CB) : getDuration() < 220 ? setStyle(0xDDA0DD) : getDuration() < 320 ? setStyle(0xDA70D6) : getDuration() < 420 ? setStyle(0xFF00FF) : getDuration() < 520 ? setStyle(0x800080) :  setStyle(0xFFFFFF);

        if(Screen.hasShiftDown()){
            list.add(Component.literal(""));
            list.add(Component.literal(getEffectDescription()).withStyle(style -> style.withColor(setStyle())));
            list.add(Component.literal(""));
            list.add(Component.literal(""));
            list.add(Component.literal("This lamp has the " + getEffect().getDisplayName().getString() + " effect.").withStyle(style -> style.withColor(setStyle(0xDB7093))));
            list.add(Component.literal(""));
            list.add(Component.literal("Effect duration: " + (getDuration()/20) + " seconds").withStyle(style -> style.withColor(color)));
            list.add(Component.literal(""));
            list.add(Component.literal("Effect power level: " + getLevel()).withStyle(ChatFormatting.GOLD));

        }
        else{
            list.add(Component.literal(""));
            list.add(Component.literal(getEffectDescription()).withStyle(style -> style.withColor(setStyle())));
            list.add(Component.literal(""));
            list.add(Component.literal(""));
            list.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, getter, list, tooltip);
    }

    private Integer setStyle(){
        Integer hex = ModChatFormatting.isValidHex(getHex()) ? getHex() : 0xFFFFFF;
        return setStyle(hex);
    }

    private Integer setStyle(Integer hex){
        return ModChatFormatting.fromHexColor(hex).getColor();
    }
}
