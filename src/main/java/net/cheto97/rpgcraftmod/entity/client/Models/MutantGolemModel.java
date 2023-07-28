package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.entity.custom.MutantGolemEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.cheto97.rpgcraftmod.util.EntityUtils.*;

public class MutantGolemModel extends AnimatedGeoModel<MutantGolemEntity> {
    private static final String entityName = "mutant_golem";
    @Override
    public ResourceLocation getModelResource(MutantGolemEntity object) {
        return geoResourceLocation(entityName);
    }

    @Override
    public ResourceLocation getTextureResource(MutantGolemEntity object) {
        return textureResourceLocation(entityName);
    }

    @Override
    public ResourceLocation getAnimationResource(MutantGolemEntity animatable) {
        return animationResourceLocation(entityName);
    }
}
