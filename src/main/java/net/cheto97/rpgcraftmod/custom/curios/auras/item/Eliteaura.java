package net.cheto97.rpgcraftmod.custom.curios.auras.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class Eliteaura extends Commonaura {

    public Eliteaura(Properties properties) {
        super(properties);
    }
    @Override
    public int getLootingLevel(SlotContext slotContext, DamageSource source, LivingEntity target, int baseLooting, ItemStack stack) {return 1;}


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        components.add(Component.literal("Loot Level: 1").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal(""));
        components.add(Component.literal("you can walk through Powdered Snow").withStyle(ChatFormatting.DARK_AQUA));


        super.appendHoverText(stack, level, components, flag);
    }
}
