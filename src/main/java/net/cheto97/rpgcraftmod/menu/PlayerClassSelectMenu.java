package net.cheto97.rpgcraftmod.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PlayerClassSelectMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    private final Map<Integer, Slot> customSlot = new HashMap<>();

    public PlayerClassSelectMenu(int id, Inventory inv, FriendlyByteBuf data){
        super(ModMenuTypes.PLAYER_CLASS_SELECT_MENU.get(),id);
    }

    @Override
    public Map<Integer, Slot> get() {
        return customSlot;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
