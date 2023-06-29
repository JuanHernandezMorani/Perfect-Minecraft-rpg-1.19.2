package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class ManaMax {
    private double manaMax = 10;
    public double get(){
        return this.manaMax;
    }
    public void resetStat(){
        manaMax = 10;
    }
    public void add(double value){
            this.manaMax = this.manaMax + value;
    }
    public void add(){
        this.manaMax++;
    }
    public void set(double value){this.manaMax = value;}
    public void copyFrom(ManaMax source){
        this.manaMax = source.manaMax;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("manaMax",manaMax);
    }
    public void loadNBTData(CompoundTag nbt){
        manaMax = nbt.getDouble("manaMax");
    }
}
