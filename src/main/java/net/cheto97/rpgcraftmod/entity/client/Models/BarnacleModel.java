package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.custom.Barnacle;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class BarnacleModel extends AnimatedGeoModel<Barnacle> {
    @Override
    public ResourceLocation getAnimationResource(Barnacle entity) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "animations/barnacle.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Barnacle entity) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "geo/barnacle.geo.json");
    }

   @Override
    public ResourceLocation getTextureResource(Barnacle entity) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/barnacle.png");
    }

    @Override
    public void setLivingAnimations(Barnacle entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone mob = this.getBone("mob");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        mob.setRotationX((extraData.headPitch * ((float) Math.PI / 180F) - (90 * (float) Math.PI / 180)));
        mob.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }

}
