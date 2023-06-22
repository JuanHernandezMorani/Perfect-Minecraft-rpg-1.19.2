package net.cheto97.rpgcraftmod.networking.data;

public class ClientMagicDefenseData {
    private static double entityData;
    private static int entityId;

    public static void set(double value){
        ClientMagicDefenseData.entityData = value;
    }
    public static void setId(int id){
        ClientMagicDefenseData.entityId = id;}

    public static double getEntityData(){
        return entityData;
    }
    public static int getEntityId(){return entityId;}
}
