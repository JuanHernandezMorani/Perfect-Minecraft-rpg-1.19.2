package net.cheto97.rpgcraftmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.cheto97.rpgcraftmod.util.levelConfig.CommandConfig;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Collection;
import java.util.Map;

public final class SuperEnchantCommand {

    private static final DynamicCommandExceptionType NONLIVING_ENTITY_EXCEPTION;
    private static final DynamicCommandExceptionType INCOMPATIBLE_ENCHANTS_EXCEPTION;
    private static final DynamicCommandExceptionType WRONG_ENCHANTS_EXCEPTION;
    private static final DynamicCommandExceptionType ITEMLESS_EXCEPTION;
    private static final SimpleCommandExceptionType FAILED_EXCEPTION;

    static {
        NONLIVING_ENTITY_EXCEPTION = new DynamicCommandExceptionType((entityName) -> Component.translatable("commands.enchant.failed.entity", entityName));
        INCOMPATIBLE_ENCHANTS_EXCEPTION = new DynamicCommandExceptionType((itemName) -> Component.translatable("commands.superenchant.failed.incompatible", itemName));
        WRONG_ENCHANTS_EXCEPTION = new DynamicCommandExceptionType((itemName) -> Component.translatable("commands.superenchant.failed.wrong", itemName));
        ITEMLESS_EXCEPTION = new DynamicCommandExceptionType((entityName) -> Component.translatable("commands.enchant.failed.itemless", entityName));
        FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.enchant.failed"));
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext c) {
        dispatcher.register(Commands.literal("superenchant")
                .requires((player) -> player.hasPermission(3))
                .then(Commands.argument("targets", EntityArgument.entities())
                .then(Commands.argument("enchantment",ItemEnchantmentArgument.enchantment()).executes((context) ->
                        enchant(context.getSource(), EntityArgument.getEntities(context, "targets"),
                                Holder.direct(ItemEnchantmentArgument.getEnchantment(context.getLastChild(),"enchantment")), 1))
                        .then(Commands.argument("level", IntegerArgumentType.integer(0, Integer.MAX_VALUE))
                        .executes((context) -> enchant(context.getSource(), EntityArgument.getEntities(context, "targets"),
                                Holder.direct(ItemEnchantmentArgument.getEnchantment(context.getLastChild(),"enchantment")), IntegerArgumentType.getInteger(context, "level")))))));
    }

    private static int enchant(CommandSourceStack source, Collection<? extends Entity> targets, Holder<Enchantment> enchantmentIn, int level)
            throws CommandSyntaxException {
        int i = 0;
        Enchantment enchantment = enchantmentIn.value();

        for (Entity entity : targets) {
            if (entity instanceof LivingEntity livingentity) {
                ItemStack stack = livingentity.getMainHandItem();
                if (!stack.isEmpty()) {
                    if (enchantment.canEnchant(stack) || CommandConfig.allowWrongEnchantments.getValue()) {
                        if (EnchantmentHelper.isEnchantmentCompatible(EnchantmentHelper.getEnchantments(stack).keySet(), enchantment)
                                || CommandConfig.allowIncompatibleEnchantments.getValue()) {

                            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
                            if (enchantments.containsKey(enchantment) && level == 0) {
                                enchantments.remove(enchantment);
                            } else {
                                enchantments.put(enchantment, level);
                            }
                            EnchantmentHelper.setEnchantments(enchantments, stack);
                            i++;

                        } else if (targets.size() == 1) {
                            throw INCOMPATIBLE_ENCHANTS_EXCEPTION.create(stack.getItem().getName(stack).getString());
                        }
                    } else if (targets.size() == 1) {
                        throw WRONG_ENCHANTS_EXCEPTION.create(stack.getItem().getName(stack).getString());
                    }
                } else if (targets.size() == 1) {
                    throw ITEMLESS_EXCEPTION.create(livingentity.getName().getString());
                }
            } else if (targets.size() == 1) {
                throw NONLIVING_ENTITY_EXCEPTION.create(entity.getName().getString());
            }
        }

        if (i == 0) {
            throw FAILED_EXCEPTION.create();
        } else {
            if (targets.size() == 1) {
                source.sendSuccess(Component.translatable("commands.enchant.success.single", enchantment.getFullname(level), targets.iterator().next().getDisplayName()), true);
            } else {
                source.sendSuccess(Component.translatable("commands.enchant.success.multiple", enchantment.getFullname(level), targets.size()), true);
            }

            return i;
        }
    }

}