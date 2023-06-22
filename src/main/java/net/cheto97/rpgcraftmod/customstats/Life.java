package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Life {
    private double life = 20;
    private double MAX_LIFE_VALUE = 20;
public Life(double value){
    set(value);
}
    public double get(){
        return life;
    }
    public double getMax(){
        return MAX_LIFE_VALUE;
    }
    public void set(double value){
        life = value;
    }
    public void setMax(double value){MAX_LIFE_VALUE = value;}
    public void increaseMax(double value){
        MAX_LIFE_VALUE = MAX_LIFE_VALUE + value;
    }
    public void increaseMax(){
        MAX_LIFE_VALUE++;
    }
    public void resetStat(){
        life = 20;
        MAX_LIFE_VALUE = 20;
    }
    public void add(double value){
        double check = this.life + value;
        if(this.life < MAX_LIFE_VALUE){
            if(check > MAX_LIFE_VALUE){
                check = MAX_LIFE_VALUE;
            }
            this.life = check;
        }
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
