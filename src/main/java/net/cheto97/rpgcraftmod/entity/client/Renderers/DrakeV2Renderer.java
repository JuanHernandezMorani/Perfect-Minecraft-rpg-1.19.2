package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.entity.client.Models.DrakeV2Model;
import net.cheto97.rpgcraftmod.entity.custom.DrakeV2Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DrakeV2Renderer extends RPGRenderer<DrakeV2Entity>{

    public DrakeV2Renderer(EntityRendererProvider.Context context) {
        super(context, new DrakeV2Model("drake_2"));
    }
    @Override
    protected String getTextureName() {
        return "drake_2";
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