package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Intelligence {
    private double intelligence = 1;

    public double get(){
        return intelligence;
    }
    public double getMin(){
        return 0;
    }
    public void resetStat(){
        intelligence = 1;
    }
    public void add(double value){
        this.intelligence =  this.intelligence + value;
    }
    public void add(){
        this.intelligence++;
    }
    public void copyFrom(Intelligence source){
        this.intelligence = source.intelligence;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("value",intelligence);
    }
    public void loadNBTData(CompoundTag nbt){
        intelligence = nbt.getDouble("value");
    }
}
