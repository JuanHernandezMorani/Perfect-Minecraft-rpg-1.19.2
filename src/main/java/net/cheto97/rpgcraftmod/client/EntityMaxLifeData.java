package net.cheto97.rpgcraftmod.client;

public class EntityMaxLifeData {
    private static double entityMaxLife;
    private static int entityId;

    public static void set(double life,int id){
        EntityMaxLifeData.entityMaxLife = life;
        EntityMaxLifeData.entityId = id;
    }

    public static double getEntityMaxLife(){
        return entityMaxLife;
    }

    public static int getEntityId(){
        return entityId;
    }
}
