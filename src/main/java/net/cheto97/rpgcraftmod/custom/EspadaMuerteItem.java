package net.cheto97.rpgcraftmod.custom;

import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.PlayerSyncPacket;
import net.cheto97.rpgcraftmod.providers.ManaProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EspadaMuerteItem extends SwordItem {
    private static final String MESSAGE_NO_ENOUGH_MANA = "message.rpgcraftmod.not_enough_mana";

    public EspadaMuerteItem(Properties properties, Tier tier) {
        super(tier,4,-1.58f,properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                double manaCost = mana.getMax()*0.25;
                    if(mana.get() >= manaCost){

                        mana.consumeMana(manaCost);
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,400,3));
                        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,400,2));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,400,4));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,400,3));
                        player.addEffect(new MobEffectInstance(MobEffects.JUMP,400,1));
                        player.getCooldowns().addCooldown(this,60);
                    }else{
                        player.sendSystemMessage(Component.translatable(MESSAGE_NO_ENOUGH_MANA).withStyle(ChatFormatting.RED));
                    }
            });
        }

        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(!stack.isEnchanted()){
            stack.enchant(Enchantments.SHARPNESS, 10);
            stack.enchant(Enchantments.UNBREAKING, 50);
            stack.enchant(Enchantments.FIRE_ASPECT, 2);
        }
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Mana cost: 25% of maximum mana").withStyle(ChatFormatting.BLUE));
            components.add(Component.literal(""));
            components.add(Component.literal(""));
            components.add(Component.literal("Right click to get for 20 seconds:").withStyle(ChatFormatting.AQUA));
            components.add(Component.literal("Life Regeneration III").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal("Fire Resistance II").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal("Damage Boost IV").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal("Increased Speed III").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal("Jump Boost I").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal(""));
            components.add(Component.literal(""));
            components.add(Component.literal("Cooldown 3 seconds").withStyle(ChatFormatting.DARK_RED));

        }else{
            components.add(Component.literal("Mana cost: 25% of maximum mana").withStyle(ChatFormatting.BLUE));
            components.add(Component.literal(""));
            components.add(Component.literal(""));
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.GOLD));
        }


        super.appendHoverText(stack, level, components, flag);
    }
}
