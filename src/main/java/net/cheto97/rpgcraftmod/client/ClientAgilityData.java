package net.cheto97.rpgcraftmod.client;

public class ClientAgilityData {
    private static double playerAgility;

    public static void set(double agility){
        ClientAgilityData.playerAgility = agility;
    }

    public static double getPlayerAgility(){
        return playerAgility;
    }
}
