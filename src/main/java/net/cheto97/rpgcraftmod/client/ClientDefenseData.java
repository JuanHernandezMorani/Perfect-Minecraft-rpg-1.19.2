package net.cheto97.rpgcraftmod.client;

public class ClientDefenseData {
    private static double playerDefense;

    public static void set(double defense){
        ClientDefenseData.playerDefense = defense;
    }

    public static double getPlayerDefense(){
        return playerDefense;
    }
}
