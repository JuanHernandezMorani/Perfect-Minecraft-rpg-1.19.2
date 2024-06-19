package net.cheto97.rpgcraftmod.item.aura;

import net.cheto97.rpgcraftmod.item.prefabs.Commonaura;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class Legendaryaura extends Commonaura {

    public Legendaryaura(Properties properties) {
        super(properties);
    }
    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }
    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {return 6;}
    @Override
    public int getLootingLevel(SlotContext slotContext, DamageSource source, LivingEntity target, int baseLooting, ItemStack stack) {return 6;}
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity player = slotContext.entity();
        if(player != null){
            player.removeEffect(MobEffects.FIRE_RESISTANCE);
            player.removeEffect(MobEffects.DAMAGE_BOOST);
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
            player.removeEffect(MobEffects.JUMP);
        }
    }
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity player = slotContext.entity();
        if(player != null){
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,1000000,10));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,1000000,2));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,1000000,2));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP,1000000,1));
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Loot Level: 6").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal(""));
            components.add(Component.literal("Fortune Level: 6").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal(""));
            components.add(Component.literal("Piglins are neutral").withStyle(ChatFormatting.GOLD));
            components.add(Component.literal(""));
            components.add(Component.literal("Fire Resistance X").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal(""));
            components.add(Component.literal("Damage Boost II").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal(""));
            components.add(Component.literal("Increased Speed II").withStyle(ChatFormatting.DARK_GREEN));
            components.add(Component.literal(""));
            components.add(Component.literal("Jump Boost I").withStyle(ChatFormatting.DARK_GREEN));

        }else{
            components.add(Component.literal("Loot Level: 6").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal(""));
            components.add(Component.literal("Fortune Level: 6").withStyle(ChatFormatting.YELLOW));
            components.add(Component.literal(""));
            components.add(Component.literal("you can walk through Powdered Snow").withStyle(ChatFormatting.DARK_AQUA));
            components.add(Component.literal(""));
            components.add(Component.literal(""));
            components.add(Component.literal(""));
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }
}
