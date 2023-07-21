package net.cheto97.rpgcraftmod.util;

import net.cheto97.rpgcraftmod.item.ModItems;
import net.minecraft.world.item.Item;

public class DefaultWingsValues implements WingsValues {
    public static final WingsValues INSTANCE = new DefaultWingsValues();

    @Override
    public Item getWings() {
        return ModItems.WHITE_LIGHT_WINGS.get();
    }

    @Override
    public float getArmourSlowMultiplier() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return 0.0124F;
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
