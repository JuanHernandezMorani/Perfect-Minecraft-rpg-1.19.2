package net.cheto97.rpgcraftmod.networking.data;

public class ClientExperienceData {
    private static Double entityData;
    private static int entityId;

    public static void set(Double value){
        ClientExperienceData.entityData = value;
    }
    public static void setId(int id){
        ClientExperienceData.entityId = id;}

    public static Double getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
