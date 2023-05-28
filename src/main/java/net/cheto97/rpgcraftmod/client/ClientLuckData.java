package net.cheto97.rpgcraftmod.client;

public class ClientLuckData {
    private static double playerLuck;

    public static void set(double luck){
        ClientLuckData.playerLuck = luck;
    }

    public static double getPlayerLuck(){
        return playerLuck;
    }
}
