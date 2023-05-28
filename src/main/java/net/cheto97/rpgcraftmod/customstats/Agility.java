package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Agility {
    private double agility = 1;
    public double get(){
        return agility;
    }
    public double getMin(){
        return 0;
    }
    public void add(){
        agility++;
    }
    public void resetStat(){
        agility = 1;
    }
    public void add(double value){
            this.agility = this.agility + value;
    }
    public void copyFrom(Agility source){
        this.agility = source.agility;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("value",agility);
    }
    public void loadNBTData(CompoundTag nbt){
        agility = nbt.getDouble("value");
    }
}
