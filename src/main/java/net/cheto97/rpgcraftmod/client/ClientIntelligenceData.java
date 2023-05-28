package net.cheto97.rpgcraftmod.client;

public class ClientIntelligenceData {
    private static double playerIntelligence;

    public static void set(double intelligence){
        ClientIntelligenceData.playerIntelligence = intelligence;
    }

    public static double getPlayerIntelligence(){
        return playerIntelligence;
    }
}
