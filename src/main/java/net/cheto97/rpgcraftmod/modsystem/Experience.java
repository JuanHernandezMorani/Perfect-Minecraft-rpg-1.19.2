package net.cheto97.rpgcraftmod.modsystem;

import net.minecraft.nbt.CompoundTag;

public class Experience {
    private double experience = 0;

    public double get(){
        return experience;
    }
    public double getMin(){
        return 0;
    }
    public void add(){
        this.experience++;
    }
    public void resetStat(){
        experience = 0;
    }
    public void add(double value){
        this.experience = this.experience + value;
    }
    public void consume(double value){
        if(experience - value <= 0){
            experience = 0;
        }
        experience = experience - value;
    }
    public void copyFrom(Experience source){
        this.experience = source.experience;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("value",experience);
    }
    public void loadNBTData(CompoundTag nbt){
        experience = nbt.getDouble("value");
    }
}
