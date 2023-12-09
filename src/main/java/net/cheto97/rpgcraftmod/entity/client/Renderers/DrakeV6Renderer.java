package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.entity.client.Models.DrakeV6Model;
import net.cheto97.rpgcraftmod.entity.custom.DrakeV6Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DrakeV6Renderer extends RPGRenderer<DrakeV6Entity>{
    public DrakeV6Renderer(EntityRendererProvider.Context context) {
        super(context, new DrakeV6Model("drake_6"));
    }
    @Override
    protected String getTextureName() {
        return "drake_6";
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