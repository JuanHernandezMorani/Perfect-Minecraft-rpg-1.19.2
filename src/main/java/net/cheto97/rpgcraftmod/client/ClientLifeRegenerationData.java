package net.cheto97.rpgcraftmod.client;

public class ClientLifeRegenerationData {
    private static double playerLifeRegeneration;

    public static void set(double liferegeneration){
        ClientLifeRegenerationData.playerLifeRegeneration = liferegeneration;
    }

    public static double getPlayerLifeRegeneration(){
        return playerLifeRegeneration;
    }
}
