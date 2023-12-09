package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.entity.client.Models.DrakeV5Model;
import net.cheto97.rpgcraftmod.entity.custom.DrakeV5Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DrakeV5Renderer extends RPGRenderer<DrakeV5Entity>{
    public DrakeV5Renderer(EntityRendererProvider.Context context) {
        super(context, new DrakeV5Model("drake_5"));
    }
    @Override
    protected String getTextureName() {
        return "drake_5";
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