package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Mana {
    private double mana = 10;
    public double get(){
        return mana;
    }
    public void set(double value){
        mana = value;
    }
    public void resetStat(){
        mana = 10;
    }
    public void add(double value){
           this.mana = this.mana+value;
    }
    public void copyFrom(Mana source){
        this.mana = source.mana;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("mana",mana);
    }
    public void loadNBTData(CompoundTag nbt){
        mana = nbt.getDouble("mana");
    }
    public void consumeMana(double value){
        double check = mana - value;
        if(check < 0){
            check = 0;
        }
        this.mana = check;
    }
}
