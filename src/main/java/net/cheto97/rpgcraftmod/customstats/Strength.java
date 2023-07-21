package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Strength {
    private double strength = 1;
    public double get(){
        return strength;
    }
    public double getMin(){
        return 1;
    }
    public void resetStat(){
        strength = 1;
    }
    public void set(double value){
        this.strength = value;
    }
    public void add(){
        this.strength++;
    }

    public void add(double value){
            this.strength = this.strength + value;
    }
    public void copyFrom(Strength source){
        this.strength = source.strength;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("strength",strength);
    }
    public void loadNBTData(CompoundTag nbt){
        strength = nbt.getDouble("strength");
    }
    public void reduce(double damageBoost) {
        if(this.strength - damageBoost < 0.01){
            this.strength = 0.01;
        }
        this.strength = this.strength - damageBoost;
    }
}
