package net.cheto97.rpgcraftmod.client;

public class ClientStrengthData {
    private static double playerStrength;

    public static void set(double strength){
        ClientStrengthData.playerStrength = strength;
    }

    public static double getPlayerStrength(){
        return playerStrength;
    }
}
