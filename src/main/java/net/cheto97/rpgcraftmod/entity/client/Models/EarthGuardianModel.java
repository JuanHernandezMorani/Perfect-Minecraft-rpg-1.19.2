package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.entity.custom.EarthGuardian;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class EarthGuardianModel extends AMGeckolibHeadModel<EarthGuardian> {
    public EarthGuardianModel() {
        super("earth_guardian");
    }

    @Override
    public void setLivingAnimations(EarthGuardian animatable, Integer instanceId, @Nullable AnimationEvent animationEvent) {
        super.setLivingAnimations(animatable, instanceId, animationEvent);
        IBone rock = getAnimationProcessor().getBone("rock");
        rock.setHidden(!animatable.shouldRenderRock);
    }
}
