package net.cheto97.rpgcraftmod.networking.data;

import net.minecraft.core.BlockPos;

public class ClientPosData {
    private static BlockPos entityData;
    private static int entityId;

    public static void set(BlockPos value){
        ClientPosData.entityData = value;
    }
    public static void setId(int id){
        ClientPosData.entityId = id;}

    public static BlockPos getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
