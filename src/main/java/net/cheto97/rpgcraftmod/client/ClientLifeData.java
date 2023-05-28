package net.cheto97.rpgcraftmod.client;

public class ClientLifeData {
    private static double playerLife;

    public static void set(double life){
        ClientLifeData.playerLife = life;
    }

    public static double getPlayerLife(){
        return playerLife;
    }
}
