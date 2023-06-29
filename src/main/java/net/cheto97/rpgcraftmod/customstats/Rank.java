package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Rank {
    private int rank = 0;

    public int get(){
        return rank;
    }
    public void set(int rank){
        this.rank = rank;
    }
    public void copyFrom(Rank source){
        this.rank = source.rank;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("rank",rank);
    }
    public void loadNBTData(CompoundTag nbt){
        rank = nbt.getInt("rank");
    }
}
