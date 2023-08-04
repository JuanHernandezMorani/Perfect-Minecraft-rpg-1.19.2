package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.OV.WildfireHelmetItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class WildfireHelmetModel extends AnimatedGeoModel<WildfireHelmetItem> {
    @Override
    public ResourceLocation getAnimationResource(WildfireHelmetItem item) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "animations/empty.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(WildfireHelmetItem item) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "geo/wildfirehelmet.geo.json");
    }

   @Override
    public ResourceLocation getTextureResource(WildfireHelmetItem item) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/wildfire/wildfire.png");
    }
}