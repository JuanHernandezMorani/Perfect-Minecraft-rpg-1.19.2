package net.cheto97.rpgcraftmod.item.prefabs;

import net.cheto97.rpgcraftmod.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class CustomWingsItem extends AbstractWingCurioItem {
    private final WingDefinition definition;

    public CustomWingsItem(String registryName) {
        this(WingDefinition.of(registryName));
    }

    public CustomWingsItem(WingDefinition definition) {
        super();
        this.definition = definition;
    }

    @Override
    public WingDefinition getWingDefinition(ItemStack stack) {
        return definition;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(ModItems.espada_muerte.get());
    }
}
