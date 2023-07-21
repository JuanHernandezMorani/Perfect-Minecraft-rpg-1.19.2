package net.cheto97.rpgcraftmod.modsystem;

import net.minecraft.nbt.CompoundTag;

public class FirstJoin {
    private boolean join = false;
    private boolean chest = false;
    public boolean get(){return join;}
    public boolean getChest(){return chest;}
    public void set(){this.join = true;}
    public void setChest(boolean change){this.chest = change;}
    public void copyFrom(FirstJoin source){
        this.join = source.join;
        this.chest = source.chest;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putBoolean("join",join);
        nbt.putBoolean("chest",chest);
    }
    public void loadNBTData(CompoundTag nbt){
        join = nbt.getBoolean("join");
        chest = nbt.getBoolean("chest");
    }
}
