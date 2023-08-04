package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.entity.custom.NatureGuardian;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class NatureGuardianModel extends AMGeckolibHeadModel<NatureGuardian> {
    public NatureGuardianModel() {
        super("nature_guardian");
    }

    @Override
    public void setLivingAnimations(NatureGuardian animatable, Integer instanceId, @Nullable AnimationEvent animationEvent) {
        super.setLivingAnimations(animatable, instanceId, animationEvent);
        IBone scythe = getAnimationProcessor().getBone("scythe");
        scythe.setHidden(!animatable.hasScythe());
    }
}
