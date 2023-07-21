package net.cheto97.rpgcraftmod.custom.curios.auras.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class Semibossaura extends Commonaura {

    public Semibossaura(Properties properties) {
        super(properties);
    }
    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }
    @Override
    public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
        return true;
    }
    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {return 8;}
    @Override
    public int getLootingLevel(SlotContext slotContext, DamageSource source, LivingEntity target, int baseLooting, ItemStack stack) {return 8;}
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        assert player != null;

            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,1000000,10));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,1000000,3));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,1000000,4));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP,1000000,1));
    }
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity player = slotContext.entity();
        assert player != null;

        player.removeEffect(MobEffects.FIRE_RESISTANCE);
        player.removeEffect(MobEffects.DAMAGE_BOOST);
        player.removeEffect(MobEffects.MOVEMENT_SPEED);
        player.removeEffect(MobEffects.JUMP);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Loot Level: 8").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal(""));
            components.add(Component.literal("Fortune Level: 8").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal(""));
            components.add(Component.literal("you can walk through Powdered Snow").withStyle(ChatFormatting.DARK_AQUA));
            components.add(Component.literal(""));
            components.add(Component.literal("Piglins are neutral").withStyle(ChatFormatting.GOLD));
            components.add(Component.literal(""));
            components.add(Component.literal("Endermans won't get mad if you see them").withStyle(ChatFormatting.LIGHT_PURPLE));
            components.add(Component.literal(""));
            components.add(Component.literal("Fire Resistance X").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal(""));
            components.add(Component.literal("Damage Boost III").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal(""));
            components.add(Component.literal("Increased Speed IV").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal(""));
            components.add(Component.literal("Jump Boost I").withStyle(ChatFormatting.DARK_GREEN));

        }else{
            components.add(Component.literal("Loot Level: 8").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal(""));
            components.add(Component.literal("Fortune Level: 8").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal(""));
            components.add(Component.literal("you can walk through Powdered Snow").withStyle(ChatFormatting.DARK_AQUA));
            components.add(Component.literal(""));
            components.add(Component.literal(""));
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }
}
