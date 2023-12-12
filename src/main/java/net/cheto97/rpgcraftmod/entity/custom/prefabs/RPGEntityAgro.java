package net.cheto97.rpgcraftmod.entity.custom.prefabs;

import net.cheto97.rpgcraftmod.entity.custom.MutantGolemEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;

public abstract class RPGEntityAgro extends Monster implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public RPGEntityAgro(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class,true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MutantGolemEntity.class,true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this,Player.class,true));
    }
    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    // GeckoLib Animation

    protected abstract String getWalkAnimation();

    protected abstract String getIdleAnimation();

    protected abstract String getDeathAnimation();
    protected abstract String getAttackAnimation();

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !Objects.equals(getWalkAnimation(),null) && !Objects.equals(getWalkAnimation(), "")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(getWalkAnimation(), true));
            return PlayState.CONTINUE;
        }
        if (this.isDeadOrDying() && !Objects.equals(getDeathAnimation(),null) && !Objects.equals(getDeathAnimation(), "")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(getDeathAnimation(), false));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(getIdleAnimation(), true));
        return PlayState.CONTINUE;
    }
    private <T extends IAnimatable> PlayState attackPredicate(AnimationEvent<T> event) {
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation(getAttackAnimation(), false));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController<>(this,"attack_controller",
                0,this::attackPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}