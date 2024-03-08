package net.cheto97.rpgcraftmod.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

    public class Dropper {

        public static void dropInWorld(Item item, Level world, BlockPos position) {
            if (world.isClientSide) {
                return;
            }
            ItemStack itemStack = new ItemStack(item, 1);
            net.minecraft.world.entity.item.ItemEntity entity = new net.minecraft.world.entity.item.ItemEntity(world, position.getX(), position.getY(), position.getZ(), itemStack);
            world.addFreshEntity(entity);
        }
        public static void dropInWorld(ItemStack itemStack, Level world, BlockPos position) {
            if (world.isClientSide) {
                return;
            }
            net.minecraft.world.entity.item.ItemEntity entity = new net.minecraft.world.entity.item.ItemEntity(world, position.getX(), position.getY(), position.getZ(), itemStack);
            world.addFreshEntity(entity);
        }
    }
