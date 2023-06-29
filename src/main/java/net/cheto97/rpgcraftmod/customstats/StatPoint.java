package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class StatPoint {
    private int stat = 0;

    public int get(){return stat;}
    public void add(int value){this.stat = this.stat + value;}
    public void add(){this.stat++;}
    public void add5(){this.stat = this.stat + 5;}
    public void resetStat(){this.stat = 0;}
    public void set(int stat){
        this.stat = stat;
    }
    public void consume(){this.stat--;}
    public void copyFrom(StatPoint source){this.stat = source.stat;}
    public void saveNBTData(CompoundTag nbt){nbt.putInt("statPoints",stat);}
    public void loadNBTData(CompoundTag nbt){stat = nbt.getInt("statPoints");}
}
