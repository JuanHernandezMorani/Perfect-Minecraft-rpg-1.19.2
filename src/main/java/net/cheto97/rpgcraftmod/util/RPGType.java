package net.cheto97.rpgcraftmod.util;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

import java.util.Locale;

public enum RPGType {
    PLAYER,
    ILLAGER,
    VILLAGER,
    WATER_ANIMAL,
    GOLEM,
    ANIMAL,
    MONSTER,
    UNKNOWN;

    private final ResourceLocation texture;
    RPGType(){
        texture = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/mob_type/"+name().toLowerCase(Locale.ROOT)+".png");
    }

    private static boolean exist(Entity entity){
        return entity instanceof LivingEntity;
    }
    private static RPGType show(Entity entity){
        assert entity instanceof LivingEntity;
        return entity instanceof Villager ? VILLAGER : ((LivingEntity) entity).getMobType() == MobType.WATER && !(entity instanceof Enemy)? WATER_ANIMAL : entity instanceof Mob ? ANIMAL : ((LivingEntity) entity).getMobType() == MobType.ILLAGER ? ILLAGER : MONSTER;
    }
    public static RPGType getEntityType(Entity entity){
        return entity instanceof Player ? PLAYER : exist(entity) ? show(entity) : UNKNOWN;
    }

    public ResourceLocation getTexture(){return texture;}
}
