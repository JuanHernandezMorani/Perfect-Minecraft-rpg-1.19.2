package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.entity.client.Models.DrakeV7Model;
import net.cheto97.rpgcraftmod.entity.custom.DrakeV7Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DrakeV7Renderer extends RPGRenderer<DrakeV7Entity>{
    public DrakeV7Renderer(EntityRendererProvider.Context context) {
        super(context, new DrakeV7Model("drake_7"));
    }
    @Override
    protected String getTextureName() {
        return "drake_7";
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