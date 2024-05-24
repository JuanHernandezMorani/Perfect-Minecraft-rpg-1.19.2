package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.entity.custom.prefabs.RPGEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.cheto97.rpgcraftmod.util.EntityUtils.*;

public abstract class RPGModel<T extends RPGEntity> extends AnimatedGeoModel<T> {
    private final String entityName;

    public RPGModel(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public ResourceLocation getModelResource(T entity) {
        return entityName.contains("drake") ? geoResourceLocation("drake") : entityName.contains("kobold_warrior") ? geoResourceLocation("kobold_warrior") : geoResourceLocation(entityName);
    }

    @Override
    public ResourceLocation getTextureResource(T entity) {
        return textureResourceLocation(entityName);
    }

    @Override
    public ResourceLocation getAnimationResource(T entity) {
        return entityName.contains("drake") ? animationResourceLocation("drake") : entityName.contains("kobold_warrior") ? animationResourceLocation("kobold_warrior") : animationResourceLocation(entityName);
    }

    public String getEntityName(){ return entityName; }
}
