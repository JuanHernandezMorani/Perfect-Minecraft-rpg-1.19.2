package net.cheto97.rpgcraftmod.client.curio;


import net.cheto97.rpgcraftmod.custom.curios.auras.model.AuraModel;
import net.cheto97.rpgcraftmod.custom.curios.auras.renderer.AuraRenderer;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.item.wings.renderer.WingsFeatureRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.*;

public class CurioRenderer {

    public static void register(){
        CuriosRendererRegistry.register(ModItems.WHITE_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.ORANGE_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.MAGENTA_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.LIGHT_BLUE_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.YELLOW_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.LIME_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.PINK_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.GREY_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.LIGHT_GREY_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.CYAN_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.PURPLE_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.BLUE_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.BROWN_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.GREEN_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.RED_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.BLACK_LIGHT_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));
        CuriosRendererRegistry.register(ModItems.ZANZAS_WINGS.get(),() -> new WingsFeatureRenderer(bakeLayer(LIGHT),bakeLayer(ZANZA)));

        CuriosRendererRegistry.register(ModItems.COMMON_AURA.get(),() -> new AuraRenderer(bakeLayer(COMMON_AURA)));
        CuriosRendererRegistry.register(ModItems.CHAMPION_AURA.get(),() -> new AuraRenderer(bakeLayer(CHAMPION_AURA)));
        CuriosRendererRegistry.register(ModItems.DEMON_AURA.get(),() -> new AuraRenderer(bakeLayer(DEMON_AURA)));
        CuriosRendererRegistry.register(ModItems.BOSS_AURA.get(),() -> new AuraRenderer(bakeLayer(BOSS_AURA)));
        CuriosRendererRegistry.register(ModItems.BRUTAL_AURA.get(),() -> new AuraRenderer(bakeLayer(BRUTAL_AURA)));
        CuriosRendererRegistry.register(ModItems.ELITE_AURA.get(),() -> new AuraRenderer(bakeLayer(ELITE_AURA)));
        CuriosRendererRegistry.register(ModItems.HERO_AURA.get(),() -> new AuraRenderer(bakeLayer(HERO_AURA)));
        CuriosRendererRegistry.register(ModItems.LEGENDARY_AURA.get(),() -> new AuraRenderer(bakeLayer(LEGENDARY_AURA)));
        CuriosRendererRegistry.register(ModItems.MYTHICAL_AURA.get(),() -> new AuraRenderer(bakeLayer(MYTHICAL_AURA)));
        CuriosRendererRegistry.register(ModItems.SEMI_BOSS_AURA.get(),() -> new AuraRenderer(bakeLayer(SEMI_BOSS_AURA)));
        CuriosRendererRegistry.register(ModItems.UNIQUE_AURA.get(),() -> new AuraRenderer(bakeLayer(UNIQUE_AURA)));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
}
