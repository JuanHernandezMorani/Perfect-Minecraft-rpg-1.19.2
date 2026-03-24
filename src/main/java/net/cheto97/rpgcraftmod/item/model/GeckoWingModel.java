package net.cheto97.rpgcraftmod.item.model;

import net.cheto97.rpgcraftmod.item.prefabs.AbstractWingCurioItem;
import net.cheto97.rpgcraftmod.item.prefabs.WingDefinition;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GeckoWingModel extends AnimatedGeoModel<AbstractWingCurioItem> {
    @Override
    public ResourceLocation getModelLocation(AbstractWingCurioItem object) {
        return object.getWingDefinition(object.getDefaultInstance()).geoModel();
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractWingCurioItem object) {
        WingDefinition definition = object.getWingDefinition(object.getDefaultInstance());
        return definition.texture();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AbstractWingCurioItem animatable) {
        return animatable.getWingDefinition(animatable.getDefaultInstance()).animationFile();
    }
}
