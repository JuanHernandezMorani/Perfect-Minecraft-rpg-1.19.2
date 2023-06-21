package net.cheto97.rpgcraftmod.client;

public class ClientCustomLevelData {
    private static int playerCustomLevel;

    public static void set(int customlevel){
        ClientCustomLevelData.playerCustomLevel = customlevel;
    }

    public static int getPlayerCustomLevel(){
        return playerCustomLevel;
    }
}
