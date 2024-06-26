package net.cheto97.rpgcraftmod.networking.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Collection;

public class EntityData {
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
    private static boolean join;
    private static int resetQ;
    private static Collection<MobEffectInstance> entityEffects;

    public static int getEntityId() {
        return entityId;
    }
    public static int getResetQ(){return resetQ;}
    public static void setResetQ(int value){EntityData.resetQ = value;}
    public static void setEntityId(int entityId) {
        EntityData.entityId = entityId;
    }
    public static int getEntityLevel() {
        return entityLevel;
    }
    public static boolean getJoin(){return join;}
    public static void setJoin(boolean value){EntityData.join = value;}
    public static void setEntityLevel(int entityLevel) {
        EntityData.entityLevel =
                entityLevel;
    }
    public static void setEntityName(String entityName){
        EntityData.entityName = entityName;
    }
    public static String getEntityName(){
        return entityName;
    }
    public static double getEntityLuck(){return entityLuck;}
    public static void setEntityLuck(double entityLuck) {
        EntityData.entityLuck =
                entityLuck;
    }
    public static void setEffects(Collection<MobEffectInstance> effects){
            entityEffects = effects.size() > 0 ? effects : null;
    }
    public static Collection<MobEffectInstance> getEffects(){
        return entityEffects;
    }
    public static BlockPos getEntityPos() {
        return entityPos;
    }
    public static void setEntityPos(BlockPos entityPos) {
        EntityData.entityPos =
                entityPos;
    }
    public static double getEntityLife() {
        return entityLife;
    }
    public static void setEntityLife(double entityLife) {
        EntityData.entityLife =
                entityLife;
    }
    public static double getEntityLifeMax() {
        return entityLifeMax;
    }
    public static void setEntityLifeMax(double entityLifeMax) {
        EntityData.entityLifeMax =
                entityLifeMax;
    }
    public static double getEntityLifeRegeneration() {
        return entityLifeRegeneration;
    }
    public static void setEntityLifeRegeneration(double entityLifeRegeneration) {
        EntityData.entityLifeRegeneration =
                entityLifeRegeneration;
    }
    public static double getEntityMana() {
        return entityMana;
    }
    public static void setEntityMana(double entityMana) {
        EntityData.entityMana =
                entityMana;
    }
    public static double getEntityManaMax() {
        return entityManaMax;
    }
    public static void setEntityManaMax(double entityManaMax) {
        EntityData.entityManaMax =
                entityManaMax;
    }
    public static double getEntityManaRegeneration() {
        return entityManaRegeneration;
    }
    public static void setEntityManaRegeneration(double entityManaRegeneration) {
        EntityData.entityManaRegeneration =
                entityManaRegeneration;
    }
    public static double getEntityAgility() {
        return entityAgility;
    }
    public static void setEntityAgility(double entityAgility) {
        EntityData.entityAgility =
                entityAgility;
    }
    public static double getEntityCommand() {
        return entityCommand;
    }
    public static void setEntityCommand(double entityCommand) {
        EntityData.entityCommand =
                entityCommand;
    }
    public static double getEntityDefense() {
        return entityDefense;
    }
    public static void setEntityDefense(double entityDefense) {
        EntityData.entityDefense =
                entityDefense;
    }
    public static double getEntityMagicDefense() {
        return entityMagicDefense;
    }
    public static void setEntityMagicDefense(double entityMagicDefense) {
        EntityData.entityMagicDefense =
                entityMagicDefense;
    }
    public static double getEntityDexterity() {
        return entityDexterity;
    }
    public static void setEntityDexterity(double entityDexterity) {
        EntityData.entityDexterity =
                entityDexterity;
    }
    public static double getEntityIntelligence() {
        return entityIntelligence;
    }
    public static void setEntityIntelligence(double entityIntelligence) {
        EntityData.entityIntelligence =
                entityIntelligence;
    }
    public static double getEntityStrength() {
        return entityStrength;
    }
    public static void setEntityStrength(double entityStrength) {
        EntityData.entityStrength =
                entityStrength;
    }
    public static int getEntityRank(){
        return entityRank;
    }
    public static void setEntityRank(int rank){
        EntityData.entityRank = rank;
    }
    public static double getEntityExperienceReward(){
        return entityExperienceReward;
    }
    public static void setEntityExperienceReward(double experienceReward){
        EntityData.entityExperienceReward = experienceReward;
    }
}
