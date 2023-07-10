package net.cheto97.rpgcraftmod.util;

import net.minecraft.world.item.Item;

public interface WingsValues {
    Item getWings();

    float getArmourSlowMultiplier();

    float getSpeed();

    boolean canFly();

    boolean doesArmourSlow();
}
