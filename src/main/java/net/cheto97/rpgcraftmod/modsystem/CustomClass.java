package net.cheto97.rpgcraftmod.modsystem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CustomClass {
    private int playerClass;
    
    public int getPlayerClass(){return playerClass;}
    
    public void set(int pClass){this.playerClass = pClass;}

    public void copyFrom(CustomClass source){
        this.playerClass = source.playerClass;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("playerClass",playerClass);
    }
    public void loadNBTData(CompoundTag nbt){
        playerClass = nbt.getInt("playerClass");
    }

    public ItemStack getIcon() {
        return Items.BARRIER.getDefaultInstance();
    }
}
