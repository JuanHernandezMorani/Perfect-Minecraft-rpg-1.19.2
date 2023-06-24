package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class MagicDefense {
    private double magicDefense = 1;

    public double get(){
        return magicDefense;
    }
    public double getMin(){
        return 0;
    }
    public void resetStat(){
        magicDefense = 1;
    }
    public void add(double value){
       this.magicDefense =  this.magicDefense + value;
    }
    public void set(double value){
        this.magicDefense = value;
    }
    public void add(){
        this.magicDefense++;
    }
    public void copyFrom(MagicDefense source){
        this.magicDefense = source.magicDefense;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("magicDefense",magicDefense);
    }
    public void loadNBTData(CompoundTag nbt){
        magicDefense = nbt.getDouble("magicDefense");
    }
}
