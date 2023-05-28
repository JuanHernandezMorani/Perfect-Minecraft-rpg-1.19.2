package net.cheto97.rpgcraftmod.client;

public class ClientExperienceData {
    private static double playerExperience;

    public static void set(double experience){
        ClientExperienceData.playerExperience = experience;
    }

    public static double getPlayerExperience(){
        return playerExperience;
    }
}
