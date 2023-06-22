package net.cheto97.rpgcraftmod.networking.data;

import net.minecraft.core.BlockPos;

public class ClientNameData {
    private static String entityData;
    private static int entityId;

    public static void set(String value){
        ClientNameData.entityData = value;
    }
    public static void setId(int id){
        ClientNameData.entityId = id;}

    public static String getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
