package net.cheto97.rpgcraftmod.entity.client.other;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireRain;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;

import java.util.Objects;

public class FireRainGoal extends AbstractBossGoal<FireGuardian> {
    public FireRainGoal(FireGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
    }

    @Override
    public void perform() {
        if (!boss.getLevel().isClientSide()) {
            FireRain fireRain = Objects.requireNonNull(AMEntities.FIRE_RAIN.get().create(boss.getLevel()));
            fireRain.setPos((boss.getTarget() != null ? boss.getTarget().position() : boss.position()).add(0, 1.5, 0));
            fireRain.setDuration(200);
            fireRain.setOwner(boss);
            fireRain.setDamage(2);
            fireRain.setRadius(2);
            boss.getLevel().addFreshEntity(fireRain);
        }
    }
}