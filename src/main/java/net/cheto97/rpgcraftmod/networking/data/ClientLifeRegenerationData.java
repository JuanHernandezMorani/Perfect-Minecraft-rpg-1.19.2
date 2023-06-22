package net.cheto97.rpgcraftmod.networking.data;

public class ClientLifeRegenerationData {
    private static double entityData;
    private static int entityId;

    public static void set(double data){
        ClientLifeRegenerationData.entityData = data;
    }
    public static void setId(int id){ClientLifeRegenerationData.entityId = id;}

    public static double getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
