package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Dexterity {
    private double dexterity = 1;
    public double get(){
        return dexterity;
    }
    public double getMin(){
        return 0;
    }
    public void add(){
        dexterity++;
    }
    public void set(double value){
        this.dexterity = value;
    }
    public void resetStat(){
        dexterity = 1;
    }
    public void add(double value){
        this.dexterity = dexterity + value;
    }
    public void copyFrom(Dexterity source){
        this.dexterity = source.dexterity;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("dexterity",dexterity);
    }
    public void loadNBTData(CompoundTag nbt){
        dexterity = nbt.getDouble("dexterity");
    }
}
