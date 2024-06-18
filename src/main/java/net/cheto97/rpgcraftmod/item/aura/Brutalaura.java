package net.cheto97.rpgcraftmod.item.aura;

import net.cheto97.rpgcraftmod.item.prefabs.Commonaura;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class Brutalaura extends Commonaura {

    public Brutalaura(Properties properties) {
        super(properties);
    }
    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {return 2;}
    @Override
    public int getLootingLevel(SlotContext slotContext, DamageSource source, LivingEntity target, int baseLooting, ItemStack stack) {return 2;}

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        components.add(Component.literal("Loot Level: 2").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal(""));
        components.add(Component.literal("Fortune Level: 2").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal("you can walk through Powdered Snow").withStyle(ChatFormatting.DARK_AQUA));
        components.add(Component.literal(""));

        super.appendHoverText(stack, level, components, flag);
    }
}
