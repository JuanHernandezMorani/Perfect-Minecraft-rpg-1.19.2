package net.cheto97.rpgcraftmod.modsystem;

import net.minecraft.nbt.CompoundTag;

public class Reset {
    private int reset = 0;

    public int get(){return reset;}

    public void setReset(int value){
        this.reset = value;
    }
    public void make(){
        reset++;
    }

    public void copyFrom(Reset source){this.reset = source.reset;}

    public void saveNBTData(CompoundTag nbt){nbt.putInt("reset",reset);}

    public void loadNBTData(CompoundTag nbt){reset = nbt.getInt("reset");}
}
