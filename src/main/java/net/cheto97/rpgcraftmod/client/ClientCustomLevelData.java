package net.cheto97.rpgcraftmod.client;

public class ClientCustomLevelData {
    private static double playerCustomLevel;

    public static void set(double customlevel){
        ClientCustomLevelData.playerCustomLevel = customlevel;
    }

    public static double getPlayerCustomLevel(){
        return playerCustomLevel;
    }
}
