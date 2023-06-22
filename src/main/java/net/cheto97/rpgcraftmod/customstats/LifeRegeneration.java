package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class LifeRegeneration {
    private double liferegeneration = 1;
public LifeRegeneration(double value){
    set(value);
}
    public double get(){
        return liferegeneration;
    }
    public double getMin(){
        return 0;
    }
    public void resetStat(){
        liferegeneration = 1;
    }
    public void add(){
            this.liferegeneration++;
    }
    public void set(double value){
        this.liferegeneration = value;
    }
    public void cancelRegeneration(){
        this.liferegeneration = 0;
    }
    public void add(double value){
            this.liferegeneration = this.liferegeneration + value;
    }
    public void copyFrom(LifeRegeneration source){
        this.liferegeneration = source.liferegeneration;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("liferegeneration",liferegeneration);
    }
    public void loadNBTData(CompoundTag nbt){
        liferegeneration = nbt.getDouble("liferegeneration");
    }
}
