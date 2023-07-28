package net.cheto97.rpgcraftmod.entity.client.other;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureScythe;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;

import java.util.Objects;

public class ThrowScytheGoal extends AbstractBossGoal<NatureGuardian> {
    public ThrowScytheGoal(NatureGuardian boss) {
        super(boss, AbstractBoss.Action.THROW, 10, 10);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 4 && boss.hasScythe();
    }

    @Override
    public void perform() {
        if (!boss.getLevel().isClientSide()) {
            NatureScythe entity = Objects.requireNonNull(AMEntities.NATURE_SCYTHE.get().create(boss.getLevel()));
            entity.moveTo(boss.position().add(0, 3, 0).add(boss.getLookAngle()));
            entity.setDeltaMovement(boss.getLookAngle());
            entity.setXRot(boss.getXRot());
            entity.setYRot(boss.getYRot());
            entity.setOwner(boss);
            boss.getLevel().addFreshEntity(entity);
            boss.setHasScythe(false);
        }
    }
}
