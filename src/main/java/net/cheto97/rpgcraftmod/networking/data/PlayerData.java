package net.cheto97.rpgcraftmod.networking.data;

import net.minecraft.core.BlockPos;

public class PlayerData {
    private static int playerStatPoints;
    private static int playerId;
    private static  int playerLevel;
    private static  BlockPos playerPos;
    private static double playerExperience;
    private static  double playerLife;
    private static  double playerLifeMax;
    private static  double playerLifeRegeneration;
    private static  double playerMana;
    private static  double playerManaMax;
    private static  double playerManaRegeneration;
    private static  double playerAgility;
    private static  double playerCommand;
    private static  double playerDefense;
    private static  double playerMagicDefense;
    private static  double playerDexterity;
    private static  double playerIntelligence;
    private static  double playerStrength;
    private static double playerLuck;
    private static double expNeed;
    private static boolean join;
    private static int playerClass;
    private static int playerReset;

    public static int getPlayerId() {
        return playerId;
    }
    public static double getExpNeed(){return expNeed;}
    public static void setExpNeed(double value){PlayerData.expNeed = value;}
    public static void setPlayerId(int playerId) {
        PlayerData.playerId =
                playerId;
    }
    public static int getPlayerReset(){
        return playerReset;
    }
    public static void setPlayerReset(int value){
        PlayerData.playerReset = value;
    }
    public static void setJoin(boolean value){
        PlayerData.join = value;
    }
    public static boolean getJoin(){
        return join;
    }
    public static void setPlayerClass(int value){
        PlayerData.playerClass = value;
    }
    public static int getPlayerClass(){
        return playerClass;
    }
    public static double getPlayerLuck(){return playerLuck;}
    public static void setPlayerLuck(double luck){PlayerData.playerLuck = luck;}
    public static int getPlayerStatPoints(){return playerStatPoints;}
    public static void setPlayerStatPoints(int value){PlayerData.playerStatPoints = value;}

    public static int getPlayerLevel() {
        return playerLevel;
    }

    public static void setPlayerLevel(int playerLevel) {
        PlayerData.playerLevel =
                playerLevel;
    }

    public static BlockPos getPlayerPos() {
        return playerPos;
    }

    public static void setPlayerPos(BlockPos playerPos) {
        PlayerData.playerPos =
                playerPos;
    }

    public static double getPlayerLife() {
        return playerLife;
    }

    public static void setPlayerLife(double playerLife) {
        PlayerData.playerLife =
                playerLife;
    }

    public static double getPlayerLifeMax() {
        return playerLifeMax;
    }

    public static void setPlayerLifeMax(double playerLifeMax) {
        PlayerData.playerLifeMax =
                playerLifeMax;
    }

    public static double getPlayerLifeRegeneration() {
        return playerLifeRegeneration;
    }

    public static void setPlayerLifeRegeneration(double playerLifeRegeneration) {
        PlayerData.playerLifeRegeneration =
                playerLifeRegeneration;
    }

    public static double getPlayerMana() {
        return playerMana;
    }

    public static void setPlayerMana(double playerMana) {
        PlayerData.playerMana =
                playerMana;
    }

    public static double getPlayerManaMax() {
        return playerManaMax;
    }

    public static void setPlayerManaMax(double playerManaMax) {
        PlayerData.playerManaMax =
                playerManaMax;
    }

    public static double getPlayerManaRegeneration() {
        return playerManaRegeneration;
    }

    public static void setPlayerManaRegeneration(double playerManaRegeneration) {
        PlayerData.playerManaRegeneration =
                playerManaRegeneration;
    }

    public static double getPlayerAgility() {
        return playerAgility;
    }

    public static void setPlayerAgility(double playerAgility) {
        PlayerData.playerAgility =
                playerAgility;
    }

    public static double getPlayerCommand() {
        return playerCommand;
    }

    public static void setPlayerCommand(double playerCommand) {
        PlayerData.playerCommand =
                playerCommand;
    }

    public static double getPlayerDefense() {
        return playerDefense;
    }

    public static void setPlayerDefense(double playerDefense) {
        PlayerData.playerDefense =
                playerDefense;
    }

    public static double getPlayerMagicDefense() {
        return playerMagicDefense;
    }

    public static void setPlayerMagicDefense(double playerMagicDefense) {
        PlayerData.playerMagicDefense =
                playerMagicDefense;
    }

    public static double getPlayerDexterity() {
        return playerDexterity;
    }

    public static void setPlayerDexterity(double playerDexterity) {
        PlayerData.playerDexterity =
                playerDexterity;
    }

    public static double getPlayerIntelligence() {
        return playerIntelligence;
    }

    public static void setPlayerIntelligence(double playerIntelligence) {
        PlayerData.playerIntelligence =
                playerIntelligence;
    }

    public static double getPlayerStrength() {
        return playerStrength;
    }

    public static void setPlayerStrength(double playerStrength) {
        PlayerData.playerStrength =
                playerStrength;
    }
    
    public static double getPlayerExperience(){
        return playerExperience;
    }
    
    public static void setPlayerExperience(double playerExperience){
        PlayerData.playerExperience = playerExperience;
    }
}
