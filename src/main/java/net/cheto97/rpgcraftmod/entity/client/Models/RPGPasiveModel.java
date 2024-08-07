package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.entity.custom.prefabs.RPGEntityPasive;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.cheto97.rpgcraftmod.util.EntityUtils.*;
import static net.cheto97.rpgcraftmod.util.EntityUtils.animationResourceLocation;

public abstract class RPGPasiveModel<T extends RPGEntityPasive> extends AnimatedGeoModel<T> {
    private final String entityName;

    public RPGPasiveModel(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public ResourceLocation getModelResource(T entity) {
        return entityName.contains("drake") ? geoResourceLocation("drake") : geoResourceLocation(entityName);
    }

    @Override
    public ResourceLocation getTextureResource(T entity) {
        return textureResourceLocation(entityName);
    }

    @Override
    public ResourceLocation getAnimationResource(T entity) {
        return entityName.contains("drake") ? animationResourceLocation("drake") : animationResourceLocation(entityName);
    }

    public String getEntityName(){ return entityName; }
}
