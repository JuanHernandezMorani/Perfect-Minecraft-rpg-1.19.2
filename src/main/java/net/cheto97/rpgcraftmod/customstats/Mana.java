package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Mana {
    private double mana = 10;
    private double MAX_MANA_VALUE = 10;
public Mana(double value){
    set(value);
}
    public double get(){
        return mana;
    }
    public double getMax(){
        return MAX_MANA_VALUE;
    }
    public void set(double value){
        mana = value;
    }
    public void setMax(double value){
        this.MAX_MANA_VALUE = value;
    }
    public void increaseMax(double value){
        MAX_MANA_VALUE = MAX_MANA_VALUE + value;
    }
    public void increaseMax(){
        MAX_MANA_VALUE++;
    }
    public void resetStat(){
        mana = 0;
        MAX_MANA_VALUE = 10;
    }
    public void add(double value){
        double check = mana + value;
        if(mana < MAX_MANA_VALUE){
            if(check > MAX_MANA_VALUE){
                check = MAX_MANA_VALUE;
            }
           this.mana = check;
        }
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
