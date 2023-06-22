package net.cheto97.rpgcraftmod.networking.data;

public class ClientLifeData {
    private static double entityData;
    private static double entityDataMax;
    private static int entityId;

    public static void set(double life){
        ClientLifeData.entityData = life;
    }
    public static void setMax(double value){
        ClientLifeData.entityDataMax = value;
    }
    public static void setId(int id){ClientLifeData.entityId = id;}

    public static double getEntityData(){
        return entityData;
    }
    public static double getEntityDataMax(){
        return entityDataMax;
    }
    public static int getEntityId(){return entityId;}
}
