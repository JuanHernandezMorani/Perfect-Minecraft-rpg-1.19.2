package net.cheto97.rpgcraftmod.networking.data;

public class ClientManaRegenerationData {
    private static double entityData;
    private static int entityId;

    public static void set(double value){
        ClientManaRegenerationData.entityData = value;
    }
    public static void setId(int id){
        ClientManaRegenerationData.entityId = id;}

    public static double getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
