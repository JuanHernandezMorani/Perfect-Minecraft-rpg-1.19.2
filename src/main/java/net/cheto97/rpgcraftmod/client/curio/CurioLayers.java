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

    //wings layers
    public static final ModelLayerLocation ASURA_WINGS_LAYER = createModelLayerLocation("wings/asura_wings");
    public static final ModelLayerLocation VOLT_WINGS_LAYER = createModelLayerLocation("wings/volt_wings");
    public static final ModelLayerLocation WINGS_1_LAYER = createModelLayerLocation("wings/wings_1");
    public static final ModelLayerLocation WINGS_2_LAYER = createModelLayerLocation("wings/wings_2");
    public static final ModelLayerLocation WINGS_3_LAYER = createModelLayerLocation("wings/wings_3");
    public static final ModelLayerLocation WINGS_4_LAYER = createModelLayerLocation("wings/wings_4");
    public static final ModelLayerLocation WINGS_5_LAYER = createModelLayerLocation("wings/wings_5");
    public static final ModelLayerLocation WINGS_6_LAYER = createModelLayerLocation("wings/wings_6");
    public static final ModelLayerLocation WINGS_7_LAYER = createModelLayerLocation("wings/wings_7");
    public static final ModelLayerLocation WINGS_8_LAYER = createModelLayerLocation("wings/wings_8");
    public static final ModelLayerLocation WINGS_9_LAYER = createModelLayerLocation("wings/wings_9");
    public static final ModelLayerLocation WINGS_10_LAYER = createModelLayerLocation("wings/wings_10");

    //armors layers


    private static ModelLayerLocation createModelLayerLocation(String textureLocation){
        return new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/"+textureLocation+".png"),"main");
    }
}
    
    
