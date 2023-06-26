package net.cheto97.rpgcraftmod.networking.data;

import net.minecraft.core.BlockPos;

public class PlayerMountData {
    private static int entityId;
    private static int entityLevel;
    private static int entityRank;
    private static double entityLuck;
    private static BlockPos entityPos;
    private static double entityExperienceReward;
    private static double entityLife;
    private static double entityLifeMax;
    private static double entityLifeRegeneration;
    private static double entityMana;
    private static double entityManaMax;
    private static double entityManaRegeneration;
    private static double entityAgility;
    private static double entityCommand;
    private static double entityDefense;
    private static double entityMagicDefense;
    private static double entityDexterity;
    private static double entityIntelligence;
    private static double entityStrength;
    private static String entityName;

    public PlayerMountData(String name,int id, int rank, int level,BlockPos pos, double experienceReward, double life, double lifeMax,
                      double lifeRegeneration, double mana, double manaMax, double manaRegeneration, double agility, double command, double defense, double magicDefense,
                      double dexterity, double intelligence, double strength,double luck){
        setEntityName(name);
        setEntityId(id);
        setEntityRank(rank);
        setEntityLife(life);
        setEntityLifeMax(lifeMax);
        setEntityLifeRegeneration(lifeRegeneration);
        setEntityMana(mana);
        setEntityManaMax(manaMax);
        setEntityManaRegeneration(manaRegeneration);
        setEntityStrength(strength);
        setEntityIntelligence(intelligence);
        setEntityDexterity(dexterity);
        setEntityMagicDefense(magicDefense);
        setEntityDefense(defense);
        setEntityLevel(level);
        setEntityExperienceReward(experienceReward);
        setEntityCommand(command);
        setEntityAgility(agility);
        setEntityPos(pos);
        setEntityLuck(luck);
    }

    public static int getEntityId() {
        return entityId;
    }

    public static void setEntityId(int entityId) {
        PlayerMountData.entityId = entityId;
    }

    public static int getEntityLevel() {
        return entityLevel;
    }

    public static void setEntityLevel(int entityLevel) {
        PlayerMountData.entityLevel =
                entityLevel;
    }
    public static void setEntityName(String entityName){
        PlayerMountData.entityName = entityName;
    }
    public static String getEntityName(){
        return entityName;
    }
    public static double getEntityLuck(){return entityLuck;}
    public static void setEntityLuck(double entityLuck) {
        PlayerMountData.entityLuck =
                entityLuck;
    }

    public static BlockPos getEntityPos() {
        return entityPos;
    }

    public static void setEntityPos(BlockPos entityPos) {
        PlayerMountData.entityPos =
                entityPos;
    }

    public static double getEntityLife() {
        return entityLife;
    }

    public static void setEntityLife(double entityLife) {
        PlayerMountData.entityLife =
                entityLife;
    }

    public static double getEntityLifeMax() {
        return entityLifeMax;
    }

    public static void setEntityLifeMax(double entityLifeMax) {
        PlayerMountData.entityLifeMax =
                entityLifeMax;
    }

    public static double getEntityLifeRegeneration() {
        return entityLifeRegeneration;
    }

    public static void setEntityLifeRegeneration(double entityLifeRegeneration) {
        PlayerMountData.entityLifeRegeneration =
                entityLifeRegeneration;
    }

    public static double getEntityMana() {
        return entityMana;
    }

    public static void setEntityMana(double entityMana) {
        PlayerMountData.entityMana =
                entityMana;
    }

    public static double getEntityManaMax() {
        return entityManaMax;
    }

    public static void setEntityManaMax(double entityManaMax) {
        PlayerMountData.entityManaMax =
                entityManaMax;
    }

    public static double getEntityManaRegeneration() {
        return entityManaRegeneration;
    }

    public static void setEntityManaRegeneration(double entityManaRegeneration) {
        PlayerMountData.entityManaRegeneration =
                entityManaRegeneration;
    }

    public static double getEntityAgility() {
        return entityAgility;
    }

    public static void setEntityAgility(double entityAgility) {
        PlayerMountData.entityAgility =
                entityAgility;
    }

    public static double getEntityCommand() {
        return entityCommand;
    }

    public static void setEntityCommand(double entityCommand) {
        PlayerMountData.entityCommand =
                entityCommand;
    }

    public static double getEntityDefense() {
        return entityDefense;
    }

    public static void setEntityDefense(double entityDefense) {
        PlayerMountData.entityDefense =
                entityDefense;
    }

    public static double getEntityMagicDefense() {
        return entityMagicDefense;
    }

    public static void setEntityMagicDefense(double entityMagicDefense) {
        PlayerMountData.entityMagicDefense =
                entityMagicDefense;
    }

    public static double getEntityDexterity() {
        return entityDexterity;
    }

    public static void setEntityDexterity(double entityDexterity) {
        PlayerMountData.entityDexterity =
                entityDexterity;
    }

    public static double getEntityIntelligence() {
        return entityIntelligence;
    }

    public static void setEntityIntelligence(double entityIntelligence) {
        PlayerMountData.entityIntelligence =
                entityIntelligence;
    }

    public static double getEntityStrength() {
        return entityStrength;
    }

    public static void setEntityStrength(double entityStrength) {
        PlayerMountData.entityStrength =
                entityStrength;
    }
    public static int getEntityRank(){
        return entityRank;
    }
    public static void setEntityRank(int rank){
        PlayerMountData.entityRank = rank;
    }
    public static double getEntityExperienceReward(){
        return entityExperienceReward;
    }
    public static void setEntityExperienceReward(double experienceReward){
        PlayerMountData.entityExperienceReward = experienceReward;
    }
}
