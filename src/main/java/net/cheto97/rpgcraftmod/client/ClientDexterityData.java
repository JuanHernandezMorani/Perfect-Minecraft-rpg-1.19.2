package net.cheto97.rpgcraftmod.client;

public class ClientDexterityData {
    private static double playerDexterity;

    public static void set(double dexterity){
        ClientDexterityData.playerDexterity = dexterity;
    }

    public static double getPlayerDexterity(){
        return playerDexterity;
    }
}
