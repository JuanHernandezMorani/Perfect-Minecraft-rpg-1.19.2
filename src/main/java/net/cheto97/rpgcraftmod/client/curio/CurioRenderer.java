package net.cheto97.rpgcraftmod.client.curio;

import net.cheto97.rpgcraftmod.item.renderer.AuraRenderer;
import net.cheto97.rpgcraftmod.item.renderer.WingsRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Supplier;

import static net.cheto97.rpgcraftmod.client.curio.CurioLayers.*;
import static net.cheto97.rpgcraftmod.item.ModItems.*;

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
        aura(COMMON_AURA, COMMON_AURA_LAYER);
        aura(CHAMPION_AURA, CHAMPION_AURA_LAYER);
        aura(DEMON_AURA, DEMON_AURA_LAYER);
        aura(BOSS_AURA, BOSS_AURA_LAYER);
        aura(BRUTAL_AURA, BRUTAL_AURA_LAYER);
        aura(ELITE_AURA, ELITE_AURA_LAYER);
        aura(HERO_AURA, HERO_AURA_LAYER);
        aura(LEGENDARY_AURA, LEGENDARY_AURA_LAYER);
        aura(MYTHICAL_AURA, MYTHICAL_AURA_LAYER);
        aura(SEMI_BOSS_AURA, SEMI_BOSS_AURA_LAYER);
        aura(UNIQUE_AURA, UNIQUE_AURA_LAYER);
    }
    private static void wingsRegister(){
        wing(WING_1, WINGS_1_LAYER,"default");
        wing(WING_2,WINGS_2_LAYER,"default");
        wing(ASURA_WINGS, ASURA_WINGS_LAYER,"asura_wings");
        wing(VOLT_WINGS, VOLT_WINGS_LAYER,"volt_wings");
      }
    private static void wing(RegistryObject<Item> item, ModelLayerLocation modelLayerLocation, String name){
          create(item.get(), () -> new WingsRenderer(bakeLayer(modelLayerLocation),name));
      }
    private static void aura(RegistryObject<Item> item, ModelLayerLocation modelLayerLocation){
        create(item.get(), () -> new AuraRenderer(bakeLayer(modelLayerLocation)));
    }
}
