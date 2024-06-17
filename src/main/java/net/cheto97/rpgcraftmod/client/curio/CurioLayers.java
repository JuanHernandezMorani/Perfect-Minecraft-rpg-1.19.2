package net.cheto97.rpgcraftmod.client.curio;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class CurioLayers {
    // auras layers
    public static final ModelLayerLocation COMMON_AURA = createModelLayerLocation("/auras/entity_aura");
    public static final ModelLayerLocation BOSS_AURA = createModelLayerLocation("/auras/boss_aura");
    public static final ModelLayerLocation HERO_AURA = createModelLayerLocation("/auras/hero_aura");
    public static final ModelLayerLocation BRUTAL_AURA = createModelLayerLocation("/auras/brutal_aura");
    public static final ModelLayerLocation CHAMPION_AURA = createModelLayerLocation("/auras/champion_aura");
    public static final ModelLayerLocation DEMON_AURA = createModelLayerLocation("/auras/demon_aura");
    public static final ModelLayerLocation ELITE_AURA = createModelLayerLocation("/auras/elite_aura");
    public static final ModelLayerLocation LEGENDARY_AURA = createModelLayerLocation("/auras/legendary_aura");
    public static final ModelLayerLocation MYTHICAL_AURA = createModelLayerLocation("/auras/mythical_aura");
    public static final ModelLayerLocation SEMI_BOSS_AURA = createModelLayerLocation("/auras/semi_boss_aura");
    public static final ModelLayerLocation UNIQUE_AURA = createModelLayerLocation("auras/unique_aura");

    //wings layers
    public static final ModelLayerLocation WINGS_1 = createModelLayerLocation("wings/wings_1");
    public static final ModelLayerLocation WINGS_2 = createWingsLocation(new ResourceLocation(RpgcraftMod.MOD_ID,"models/item/wings_2.json"));

    //armors layers


    private static ModelLayerLocation createModelLayerLocation(String textureLocation){
        return new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/"+textureLocation+".png"),"main");
    }
    private static ModelLayerLocation createWingsLocation(ResourceLocation modelLocation){
        return new ModelLayerLocation(modelLocation,"main");
    }
}
    
    
