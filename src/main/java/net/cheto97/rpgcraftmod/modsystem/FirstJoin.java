package net.cheto97.rpgcraftmod.modsystem;

import net.minecraft.nbt.CompoundTag;

public class FirstJoin {
    private boolean join = false;

    public boolean get(){return join;}
    public void set(){this.join = true;}
    public void copyFrom(FirstJoin source){
        this.join = source.join;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putBoolean("join",join);
    }
    public void loadNBTData(CompoundTag nbt){
        join = nbt.getBoolean("join");
    }
}
