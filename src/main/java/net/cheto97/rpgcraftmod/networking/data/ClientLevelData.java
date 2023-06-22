package net.cheto97.rpgcraftmod.networking.data;

public class ClientLevelData {
    private static int entityData;
    private static int entityId;

    public static void set(int value){
        ClientLevelData.entityData = value;
    }
    public static void setId(int id){
        ClientLevelData.entityId = id;}

    public static int getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
