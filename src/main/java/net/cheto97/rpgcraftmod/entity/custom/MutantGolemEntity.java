package net.cheto97.rpgcraftmod.entity.custom;

import net.cheto97.rpgcraftmod.util.RPGMobType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
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
import java.util.function.Predicate;


public class MutantGolemEntity extends Monster implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = (livingEntity) -> !(livingEntity instanceof Player) && livingEntity.getMobType() != MobType.WATER && livingEntity instanceof Monster && livingEntity.attackable() && !(livingEntity instanceof MutantGolemEntity);
    public MutantGolemEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 160.0D)
                .add(Attributes.ATTACK_DAMAGE, 21.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.ATTACK_KNOCKBACK,2.0D)
                .add(Attributes.ARMOR,5.0D)
                .add(Attributes.ARMOR_TOUGHNESS,3.0D)
                .add(Attributes.LUCK,1.13D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.005D)
                .build();
    }
    @Override
    public @NotNull MobType getMobType() {
        return RPGMobType.GOLEM;
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));

    }
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        this.spawnAtLocation(new ItemStack(Blocks.LARGE_AMETHYST_BUD));
    }
    @Override
    public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.step"))), 0.15f, 1);
    }
    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.hurt"));
    }
    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.death"));
    }
    @Override
    protected float getSoundVolume() {
        return 0.2F;
    }
    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source == DamageSource.FALL) return amount > 10;

        if (source == DamageSource.CACTUS) return false;

        if (source == DamageSource.DROWN) return amount > 3;

        if (source == DamageSource.LIGHTNING_BOLT) return true;

        if (source.isExplosion()) return false;

        if (source == DamageSource.ANVIL) return false;

        if (source == DamageSource.WITHER) return true;

        if(amount < 0.1f) return false;

        return super.hurt(source, amount);
    }
    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public boolean canDisableShield() {
        return true;
    }

    @Override
    public boolean canChangeDimensions() {
        return true;
    }

    // GeckoLib Animation

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mutant_golem.walk", true));
            return PlayState.CONTINUE;
        }
        if(this.isDeadOrDying()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mutant_golem.death", false));
            return PlayState.STOP;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mutant_golem.idle", true));
        return PlayState.CONTINUE;
    }
    private <T extends IAnimatable> PlayState attackPredicate(AnimationEvent<T> event) {
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mutant_golem.attack_right_punch", false));
            this.swinging = false;
        }
       return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this,"controller",
                0,this::predicate));
        data.addAnimationController(new AnimationController<>(this,"attack_controller",
                0,this::attackPredicate));
    }



    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
