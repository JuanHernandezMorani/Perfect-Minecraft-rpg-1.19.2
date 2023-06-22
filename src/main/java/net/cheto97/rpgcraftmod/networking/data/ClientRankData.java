package net.cheto97.rpgcraftmod.networking.data;

public class ClientRankData {
    private static int entityData;
    private static int entityId;

    public static void set(int value){
        ClientRankData.entityData = value;
    }
    public static void setId(int id){
        ClientRankData.entityId = id;
    }

    public static int getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
