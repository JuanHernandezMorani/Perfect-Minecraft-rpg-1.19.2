package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.entity.client.Models.DrakeV1Model;
import net.cheto97.rpgcraftmod.entity.custom.DrakeV1Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DrakeV1Renderer extends RPGRenderer<DrakeV1Entity>{

    public DrakeV1Renderer(EntityRendererProvider.Context context) {
        super(context, new DrakeV1Model("drake_1"));
    }

    @Override
    protected String getTextureName() {
        return "drake_1";
    }

    @Override
    protected float getShadowRadius() {
        return 1.0f;
    }
    @Override
    protected float getScaleX() {
        return 1.8f;
    }

    @Override
    protected float getScaleY() {
        return 1.8f;
    }

    @Override
    protected float getScaleZ() {
        return 1.8f;
    }
}