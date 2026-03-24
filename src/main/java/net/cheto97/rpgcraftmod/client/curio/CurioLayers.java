package net.cheto97.rpgcraftmod.client.curio;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class CurioLayers {
    // auras layers
    public static final ModelLayerLocation COMMON_AURA_LAYER = createModelLayerLocation("/auras/entity_aura");
    public static final ModelLayerLocation BOSS_AURA_LAYER = createModelLayerLocation("/auras/boss_aura");
    public static final ModelLayerLocation HERO_AURA_LAYER = createModelLayerLocation("/auras/hero_aura");
    public static final ModelLayerLocation BRUTAL_AURA_LAYER = createModelLayerLocation("/auras/brutal_aura");
    public static final ModelLayerLocation CHAMPION_AURA_LAYER = createModelLayerLocation("/auras/champion_aura");
    public static final ModelLayerLocation DEMON_AURA_LAYER = createModelLayerLocation("/auras/demon_aura");
    public static final ModelLayerLocation ELITE_AURA_LAYER = createModelLayerLocation("/auras/elite_aura");
    public static final ModelLayerLocation LEGENDARY_AURA_LAYER = createModelLayerLocation("/auras/legendary_aura");
    public static final ModelLayerLocation MYTHICAL_AURA_LAYER = createModelLayerLocation("/auras/mythical_aura");
    public static final ModelLayerLocation SEMI_BOSS_AURA_LAYER = createModelLayerLocation("/auras/semi_boss_aura");
    public static final ModelLayerLocation UNIQUE_AURA_LAYER = createModelLayerLocation("auras/unique_aura");

    private static ModelLayerLocation createModelLayerLocation(String textureLocation){
        return new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/"+textureLocation+".png"),"main");
    }
}
