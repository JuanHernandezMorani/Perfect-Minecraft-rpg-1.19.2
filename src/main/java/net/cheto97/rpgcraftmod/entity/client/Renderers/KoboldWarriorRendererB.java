package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.entity.client.Models.KoboldWarriorModel;
import net.cheto97.rpgcraftmod.entity.custom.KoboldWarriorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class KoboldWarriorRendererB extends RPGRenderer<KoboldWarriorEntity>{

    public KoboldWarriorRendererB(EntityRendererProvider.Context context){super(context, new KoboldWarriorModel("kobold_warrior_2"));}
    @Override
    protected String getTextureName() {
        return "kobold_warrior_2";
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
