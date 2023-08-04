package net.cheto97.rpgcraftmod.entity.client.Models;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AMGeckolibHeadModel<T extends IAnimatable> extends AMGeckolibModel<T> {
    public AMGeckolibHeadModel(String name) {
        super(name);
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent animationEvent) {
        super.setLivingAnimations(entity, uniqueID, animationEvent);
        if (animationEvent != null) {
            IBone head = getAnimationProcessor().getBone("head");
            EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX((float) (extraData.headPitch * Math.PI / 180D));
            head.setRotationY((float) (extraData.netHeadYaw * Math.PI / 180D));
        }
    }
}
