package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

public class WildfireShield {
    public static final Material base = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(RpgcraftMod.MOD_ID, "entity/wildfire_shield_base"));
    public static final Material base_nop = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(RpgcraftMod.MOD_ID, "entity/wildfire_shield_base_nopattern"));
}