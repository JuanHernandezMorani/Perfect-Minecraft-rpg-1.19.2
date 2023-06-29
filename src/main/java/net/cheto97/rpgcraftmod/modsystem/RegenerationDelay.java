package net.cheto97.rpgcraftmod.modsystem;

import net.minecraft.nbt.CompoundTag;

public class RegenerationDelay {
    private int cooldown = 0;

    public int get(){return cooldown;}
    public void set(){this.cooldown = 100;}
    public void decrease(){this.cooldown--;}
    public void copyFrom(RegenerationDelay source){
        this.cooldown = source.cooldown;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("cooldown",cooldown);
    }
    public void loadNBTData(CompoundTag nbt){
        cooldown = nbt.getInt("cooldown");
    }
}
