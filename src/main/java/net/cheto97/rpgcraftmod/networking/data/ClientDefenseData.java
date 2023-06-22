package net.cheto97.rpgcraftmod.networking.data;

public class ClientDefenseData {
    private static double entityData;
    private static int entityId;

    public static void set(double value){
        ClientDefenseData.entityData = value;
    }
    public static void setId(int id){
        ClientDefenseData.entityId = id;}

    public static double getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
