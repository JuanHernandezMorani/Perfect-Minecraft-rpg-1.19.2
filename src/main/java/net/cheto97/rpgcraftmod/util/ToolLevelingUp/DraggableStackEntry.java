package net.cheto97.rpgcraftmod.util.ToolLevelingUp;

import net.minecraft.world.item.ItemStack;

public class DraggableStackEntry {
    private ItemStack itemStack;
    private int initialX;
    private int initialY;
    private int offsetX;
    private int offsetY;

    public DraggableStackEntry(ItemStack itemStack, int initialX, int initialY) {
        this.itemStack = itemStack;
        this.initialX = initialX;
        this.initialY = initialY;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void updatePosition(int mouseX, int mouseY) {
        this.offsetX = mouseX - this.initialX;
        this.offsetY = mouseY - this.initialY;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
