package net.cheto97.rpgcraftmod.util;

import net.minecraft.nbt.CompoundTag;

public class ExperienceReward {
    private double experience = 0;

    public double get(){
        return experience;
    }

    public void set(double value){
        this.experience = value;
    }

    public void copyFrom(ExperienceReward source){
        this.experience = source.experience;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("experience",experience);
    }

    public void loadNBTData(CompoundTag nbt){
        experience = nbt.getDouble("experience");
    }
}
