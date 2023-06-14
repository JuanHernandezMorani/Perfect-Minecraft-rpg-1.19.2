package net.cheto97.rpgcraftmod.client;

public class ClientMaxManaData {
    private static double playerMaxMana;

    public static void setMaxMana(double maxMana){
        ClientMaxManaData.playerMaxMana = maxMana;
    }

    public static double getPlayerMaxMana(){
        return playerMaxMana;
    }


}
