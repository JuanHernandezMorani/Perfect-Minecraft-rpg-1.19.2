package net.cheto97.rpgcraftmod.util;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.resources.ResourceLocation;

public class EntityUtils {
    public static ResourceLocation geoResourceLocation(String name){
        return new ResourceLocation(RpgcraftMod.MOD_ID,"geo/"+name+".geo.json");
    }
    public static ResourceLocation textureResourceLocation(String name){
        return new ResourceLocation(RpgcraftMod.MOD_ID,"textures/entity/custom/"+name+".png");
    }
    public static ResourceLocation animationResourceLocation(String name){
        return new ResourceLocation(RpgcraftMod.MOD_ID,"animations/"+name+".animation.json");
    }
}
