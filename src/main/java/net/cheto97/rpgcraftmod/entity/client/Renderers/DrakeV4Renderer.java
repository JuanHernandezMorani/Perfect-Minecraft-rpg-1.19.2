package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.entity.client.Models.DrakeV4Model;
import net.cheto97.rpgcraftmod.entity.custom.DrakeV4Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DrakeV4Renderer extends RPGRenderer<DrakeV4Entity>{
    public DrakeV4Renderer(EntityRendererProvider.Context context) {
        super(context, new DrakeV4Model("drake_4"));
    }
    @Override
    protected String getTextureName() {
        return "drake_4";
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