package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.entity.client.Models.DrakeV3Model;
import net.cheto97.rpgcraftmod.entity.custom.DrakeV3Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DrakeV3Renderer extends RPGRenderer<DrakeV3Entity>{
    public DrakeV3Renderer(EntityRendererProvider.Context context) {
        super(context, new DrakeV3Model("drake_3"));
    }
    @Override
    protected String getTextureName() {
        return "drake_3";
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