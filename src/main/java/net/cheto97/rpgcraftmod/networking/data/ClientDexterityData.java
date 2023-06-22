package net.cheto97.rpgcraftmod.networking.data;

public class ClientDexterityData {
    private static double entityData;
    private static int entityId;

    public static void set(double value){
        ClientDexterityData.entityData = value;
    }
    public static void setId(int id){
        ClientDexterityData.entityId = id;}

    public static double getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
