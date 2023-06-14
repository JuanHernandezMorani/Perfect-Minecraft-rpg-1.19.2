package net.cheto97.rpgcraftmod.client;

public class ClientMaxLifeData {
    private static double playerMaxLife;

    public static void setMaxLife(double maxLife){
        ClientMaxLifeData.playerMaxLife = maxLife;
    }

    public static double getPlayerMaxLife(){
        return playerMaxLife;
    }


}
