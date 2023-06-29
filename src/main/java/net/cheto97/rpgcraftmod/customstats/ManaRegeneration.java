package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class ManaRegeneration {
    private double manaregeneration = 1;

    public double get(){
        return manaregeneration;
    }
    public void set(double value){
        this.manaregeneration = value;
    }
    public void add(){
        manaregeneration++;
    }
    public void resetStat(){
        manaregeneration = 1;
    }
    public void add(double value){
            this.manaregeneration =  this.manaregeneration + value;
    }
    public void copyFrom(ManaRegeneration source){
        this.manaregeneration = source.manaregeneration;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("manaregeneration",manaregeneration);
    }
    public void loadNBTData(CompoundTag nbt){
        manaregeneration = nbt.getDouble("manaregeneration");
    }

}
