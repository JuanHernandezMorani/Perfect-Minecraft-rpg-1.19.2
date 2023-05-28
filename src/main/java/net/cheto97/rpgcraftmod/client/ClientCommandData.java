package net.cheto97.rpgcraftmod.client;

public class ClientCommandData {
    private static double playerCommand;

    public static void set(double command){
        ClientCommandData.playerCommand = command;
    }

    public static double getPlayerCommand(){
        return playerCommand;
    }
}
