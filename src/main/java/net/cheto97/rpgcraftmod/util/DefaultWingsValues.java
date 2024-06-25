package net.cheto97.rpgcraftmod.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class DefaultWingsValues implements WingsValues {
    public static final WingsValues INSTANCE = new DefaultWingsValues();

    @Override
    public Item getWings() {
        return Items.AIR.asItem();
    }

    @Override
    public float getArmourSlowMultiplier() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return 0.0034F;
    }

    @Override
    public boolean canFly() {
        return true;
    }

    @Override
    public boolean doesArmourSlow() {
        return false;
    }
}
