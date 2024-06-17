package net.cheto97.rpgcraftmod.client.curio;

import net.cheto97.rpgcraftmod.custom.curios.auras.renderer.AuraRenderer;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.item.wings.renderer.CustomWingsFeatureRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Supplier;

import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.*;

public class CurioRenderer {

    public static void register(){
        aurasRegister();
        wingsRegister();
    }
    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
    public static void create(Item item, Supplier<ICurioRenderer> renderer){
        CuriosRendererRegistry.register(item,renderer);
    }
    private static void aurasRegister(){
        create(ModItems.COMMON_AURA.get(),() -> new AuraRenderer(bakeLayer(COMMON_AURA)));
        create(ModItems.CHAMPION_AURA.get(),() -> new AuraRenderer(bakeLayer(CHAMPION_AURA)));
        create(ModItems.DEMON_AURA.get(),() -> new AuraRenderer(bakeLayer(DEMON_AURA)));
        create(ModItems.BOSS_AURA.get(),() -> new AuraRenderer(bakeLayer(BOSS_AURA)));
        create(ModItems.BRUTAL_AURA.get(),() -> new AuraRenderer(bakeLayer(BRUTAL_AURA)));
        create(ModItems.ELITE_AURA.get(),() -> new AuraRenderer(bakeLayer(ELITE_AURA)));
        create(ModItems.HERO_AURA.get(),() -> new AuraRenderer(bakeLayer(HERO_AURA)));
        create(ModItems.LEGENDARY_AURA.get(),() -> new AuraRenderer(bakeLayer(LEGENDARY_AURA)));
        create(ModItems.MYTHICAL_AURA.get(),() -> new AuraRenderer(bakeLayer(MYTHICAL_AURA)));
        create(ModItems.SEMI_BOSS_AURA.get(),() -> new AuraRenderer(bakeLayer(SEMI_BOSS_AURA)));
        create(ModItems.UNIQUE_AURA.get(),() -> new AuraRenderer(bakeLayer(UNIQUE_AURA)));
    }
    private static void wingsRegister(){
        create(ModItems.WING_1.get(), () -> new CustomWingsFeatureRenderer(bakeLayer(WINGS_1)));
        create(ModItems.WING_2.get(), () -> new CustomWingsFeatureRenderer(bakeLayer(WINGS_2)));
    }
}
