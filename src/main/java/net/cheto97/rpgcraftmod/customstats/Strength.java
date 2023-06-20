package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Strength {
    private double strength = 1;
    private final double MIN_STRENGTH_VALUE = 1;

    public double get(){
        return strength;
    }
    public double getMin(){
        return MIN_STRENGTH_VALUE;
    }
    public void resetStat(){
        strength = 1;
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
}
