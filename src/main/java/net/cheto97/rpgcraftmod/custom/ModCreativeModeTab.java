package net.cheto97.rpgcraftmod.custom;

import net.cheto97.rpgcraftmod.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
    public static final CreativeModeTab RPGCRAFT_TAB = new CreativeModeTab("rpgcrafttab") {

        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.zafiro.get());
        }
    };

    public static final CreativeModeTab RPGCRAFT_WINGS_TAB = new CreativeModeTab("rpgcraftwingstab") {

        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.VOLT_WINGS.get());
        }
    };
}
