package net.cheto97.rpgcraftmod.client;

public class ClientManaData {
    private static double playerMana;

    public static void set(double mana){
        ClientManaData.playerMana = mana;
    }

    public static double getPlayerMana(){
        return playerMana;
    }


}
