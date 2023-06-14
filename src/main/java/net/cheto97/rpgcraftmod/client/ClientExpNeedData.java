package net.cheto97.rpgcraftmod.client;

public class ClientExpNeedData {
    private static double playerExpNeed;

    public static void set(double expNeed){
        ClientExpNeedData.playerExpNeed = expNeed;
    }

    public static double getPlayerExpNeed(){
        return playerExpNeed;
    }
}
