package net.cheto97.rpgcraftmod.customstats;

import net.minecraft.nbt.CompoundTag;

public class Command {
    private double command = 1;
    public double get(){
        return command;
    }
    public double getMin(){
        return 0;
    }
    public void add(){
        command++;
    }
    public void resetStat(){
        command = 1;
    }
    public void add(double value){
            this.command = command + value;
    }
    public void copyFrom(Command source){
        this.command = source.command;
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putDouble("command",command);
    }
    public void loadNBTData(CompoundTag nbt){
        command = nbt.getDouble("command");
    }
}
