package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Life {
    private double life = 20;
    public double get(){
        return life;
    }
    public void set(double value){
        life = value;
    }
    public void resetStat(){
        life = 20;
    }
    public void add(double value){
            this.life = this.life+value;
    }
    public void copyFrom(Life source){
        this.life = source.life;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("life",life);
    }
    public void loadNBTData(CompoundTag nbt){
        life = nbt.getDouble("life");
    }
    public void consumeLife(double value){
        double check = life - value;
        if(check < 0){
            check = 0;
        }
        this.life = check;
    }
}
