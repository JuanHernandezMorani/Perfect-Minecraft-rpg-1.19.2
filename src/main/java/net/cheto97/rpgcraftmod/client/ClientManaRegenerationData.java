package net.cheto97.rpgcraftmod.client;

public class ClientManaRegenerationData {
    private static double playerManaRegeneration;

    public static void set(double manaregeneration){
        ClientManaRegenerationData.playerManaRegeneration = manaregeneration;
    }

    public static double getPlayerManaRegeneration(){
        return playerManaRegeneration;
    }
}
