package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class LifeMax {
    private double lifeMax = 20;
    public double get(){
        return this.lifeMax;
    }
    public void resetStat(){
        lifeMax = 20;
    }
    public void add(double value){
        this.lifeMax = this.lifeMax + value;
    }
    public void add(){
        this.lifeMax++;
    }
    public void set(double value){this.lifeMax = value;}
    public void copyFrom(LifeMax source){
        this.lifeMax = source.lifeMax;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("lifeMax",lifeMax);
    }
    public void loadNBTData(CompoundTag nbt){
        lifeMax = nbt.getDouble("lifeMax");
    }
}
