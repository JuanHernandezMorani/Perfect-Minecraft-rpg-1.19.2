package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Luck {
    private double luck = 1;

    public double get(){
        return luck;
    }
    public double getMin(){
        return 0;
    }
    public void add(){
        this.luck++;
    }
    public void resetStat(){
        luck = 1;
    }
    public void add(double value){
            this.luck = this.luck + value;
    }
    public void copyFrom(Luck source){
        this.luck = source.luck;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble ("value",luck);
    }
    public void loadNBTData(CompoundTag nbt){
        luck = nbt.getDouble ("value");
    }
}
