package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.custom.Wildfire;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class WildfireModel extends AnimatedGeoModel<Wildfire> {
    @Override
    public ResourceLocation getAnimationResource(Wildfire entity) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "animations/wildfire.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Wildfire entity) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "geo/wildfire.geo.json");
    }

   @Override
    public ResourceLocation getTextureResource(Wildfire entity) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/wildfire/wildfire.png");
    }

    @Override
    public void setLivingAnimations(Wildfire entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
