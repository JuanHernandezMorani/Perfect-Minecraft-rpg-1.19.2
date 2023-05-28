package net.cheto97.rpgcraftmod.modsystem;

import net.minecraft.nbt.CompoundTag;

public class Customlevel {
    private int customlevel = 1;
    private double previousLevelExp = 0;

    private int resetLevel = 0;

    public double get(){
        return customlevel;
    }
    public double getMin(){
        return 1;
    }
    public void add(){
        customlevel++;
    }
    public void makeReset(){
        customlevel = 1;
        previousLevelExp = 0;
        resetLevel++;
    }
    public void setPreviousLevelExp(double exp){
        previousLevelExp = exp;
    }
    public double experienceNeeded(){
        return (50*customlevel)+previousLevelExp;
    }
    public void setLevel(int value){
        customlevel = value;
    }
    public void add(int value){
        customlevel = customlevel + value;
    }
    public void copyFrom(Customlevel source){
        customlevel = source.customlevel;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("value",customlevel);
    }
    public void loadNBTData(CompoundTag nbt){
        customlevel = nbt.getInt("value");
    }
}
