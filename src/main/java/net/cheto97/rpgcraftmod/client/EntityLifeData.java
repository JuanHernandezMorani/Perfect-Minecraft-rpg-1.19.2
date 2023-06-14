package net.cheto97.rpgcraftmod.client;

public class EntityLifeData {
    private static double entityLife;
    private static int entityId;

    public static void set(double life,int id){

        EntityLifeData.entityLife = life;
        EntityLifeData.entityId = id;
    }

    public static double getEntityLife(){
        return entityLife;
    }

    public static int getEntityId(){
        return entityId;
    }

}
