package net.cheto97.rpgcraftmod.networking.data;

public class ClientAgilityData {
    private static double entityData;
    private static int entityId;

    public static void set(double value){
        ClientAgilityData.entityData = value;
    }
    public static void setId(int id){
        ClientAgilityData.entityId = id;}

    public static double getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
