package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Defense {
    private double defense = 1;
    public double get(){
        return defense;
    }
    public double getMin(){
        return 0;
    }
    public void resetStat(){
        defense = 1;
    }
    public void add(double value){
       this.defense =  this.defense + value;
    }
    public void set(double value){
        this.defense = value;
    }
    public void add(){
        this.defense++;
    }
    public void copyFrom(Defense source){
        this.defense = source.defense;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("defense",defense);
    }
    public void loadNBTData(CompoundTag nbt){
        defense = nbt.getDouble("defense");
    }
}
