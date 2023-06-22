package net.cheto97.rpgcraftmod.networking.data;

public class ClientManaData {
    private static double entityData;
    private static double entityDataMax;
    private static int entityId;

    public static void set(double value){
        ClientManaData.entityData = value;
    }
    public static void setMax(double value){
        ClientManaData.entityDataMax = value;
    }
    public static void setId(int id){
        ClientManaData.entityId = id;}

    public static double getEntityData(){
        return entityData;
    }
    public static double getEntityDataMax(){
        return entityDataMax;
    }
    public static int getEntityId(){return entityId;}
}
