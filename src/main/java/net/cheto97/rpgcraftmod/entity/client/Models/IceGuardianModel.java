package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.entity.custom.IceGuardian;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class IceGuardianModel extends AMGeckolibHeadModel<IceGuardian> {
    public IceGuardianModel() {
        super("ice_guardian");
    }

    @Override
    public void setLivingAnimations(IceGuardian animatable, Integer instanceId, @Nullable AnimationEvent animationEvent) {
        super.setLivingAnimations(animatable, instanceId, animationEvent);
        IBone rightArm = getAnimationProcessor().getBone("right_arm");
        rightArm.setHidden(animatable.getArmCount() <= 1);
        IBone leftArm = getAnimationProcessor().getBone("left_arm");
        leftArm.setHidden(!animatable.canLaunchArm());
    }
}
