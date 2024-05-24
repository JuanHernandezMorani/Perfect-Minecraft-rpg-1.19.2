package net.cheto97.rpgcraftmod.entity.custom.prefabs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class RPGEntityMount extends RPGEntityTameable implements IAnimatable,ContainerListener, HasCustomInventoryScreen, PlayerRideableJumping, Saddleable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public static final int EQUIPMENT_SLOT_OFFSET = 400;
    public static final int CHEST_SLOT_OFFSET = 499;
    public static final int INVENTORY_SLOT_OFFSET = 500;
    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT, Items.SUGAR, Blocks.HAY_BLOCK.asItem(), Items.APPLE, Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
    private final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(this.getClass(), EntityDataSerializers.BYTE);
    private final EntityDataAccessor<Optional<UUID>> DATA_ID_OWNER_UUID = SynchedEntityData.defineId(this.getClass(), EntityDataSerializers.OPTIONAL_UUID);
    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("293X1371-8Y1H-17K8-8P4D-SF0G1667F295");
    private int eatingCounter;
    private static final Predicate<LivingEntity> PARENT_SELECTOR = (p_30636_) -> p_30636_ instanceof RPGEntityMount && ((RPGEntityMount)p_30636_).isBred();
    private static final TargetingConditions MOM_TARGETING = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().selector(PARENT_SELECTOR);
    public int sprintCounter;
    protected boolean isJumping;
    protected SimpleContainer inventory;
    protected int temper;
    protected float playerJumpPendingScale;
    private boolean allowStandSliding;
    protected boolean canGallop = true;
    protected int gallopSoundCounter;

    protected RPGEntityMount(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.createInventory();
    }
    protected int getInventorySize() {
        return 2;
    }
    protected void doPlayerRide(Player p_30634_) {
        this.setEating(false);
        this.setStanding(false);
        if (!this.level.isClientSide) {
            p_30634_.setYRot(this.getYRot());
            p_30634_.setXRot(this.getXRot());
            p_30634_.startRiding(this);
        }
    }
    public boolean isEating() {
        return this.getFlag(16);
    }
    public void setTamed(boolean p_30652_) {
        this.setFlag(2, p_30652_);
    }
    public void setIsJumping(boolean p_30656_) {
        this.isJumping = p_30656_;
    }
    public boolean isJumping() {
        return this.isJumping;
    }
    public double getCustomJump() {
        return this.getAttributeValue(Attributes.JUMP_STRENGTH);
    }
    public void travel(@NotNull Vec3 p_30633_) {
        if (this.isAlive()) {
            LivingEntity livingentity = this.getControllingPassenger();
            if (this.isVehicle() && livingentity != null) {
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                    this.gallopSoundCounter = 0;
                }

                if (this.onGround && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
                    f = 0.0F;
                    f1 = 0.0F;
                }

                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
                    double d0 = this.getCustomJump() * (double)this.playerJumpPendingScale * (double)this.getBlockJumpFactor();
                    double d1 = d0 + this.getJumpBoostPower();
                    Vec3 vec3 = this.getDeltaMovement();
                    this.setDeltaMovement(vec3.x, d1, vec3.z);
                    this.setIsJumping(true);
                    this.hasImpulse = true;
                    net.minecraftforge.common.ForgeHooks.onLivingJump(this);
                    if (f1 > 0.0F) {
                        float f2 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
                        float f3 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
                        this.setDeltaMovement(this.getDeltaMovement().add((double)(-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double)(0.4F * f3 * this.playerJumpPendingScale)));
                    }

                    this.playerJumpPendingScale = 0.0F;
                }

                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3((double)f, p_30633_.y, (double)f1));
                } else if (livingentity instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                if (this.onGround) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }

                this.calculateEntityAnimation(this, false);
                this.tryCheckInsideBlocks();
            } else {
                this.flyingSpeed = 0.02F;
                super.travel(p_30633_);
            }
        }
    }
    public boolean isStanding() {
        return this.getFlag(32);
    }
    public void readAdditionalSaveData(@NotNull CompoundTag p_30565_) {
        super.readAdditionalSaveData(p_30565_);
        this.setEating(p_30565_.getBoolean("EatingHaystack"));
        this.setBred(p_30565_.getBoolean("Bred"));
        this.setTemper(p_30565_.getInt("Temper"));
        this.setTamed(p_30565_.getBoolean("Tame"));
        UUID uuid;
        if (p_30565_.hasUUID("Owner")) {
            uuid = p_30565_.getUUID("Owner");
        } else {
            String s = p_30565_.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(this.getServer()), s);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }

        if (p_30565_.contains("SaddleItem", 10)) {
            ItemStack itemstack = ItemStack.of(p_30565_.getCompound("SaddleItem"));
            if (itemstack.is(Items.SADDLE)) {
                this.inventory.setItem(0, itemstack);
            }
        }

        this.updateContainerEquipment();
    }
    public void addAdditionalSaveData(@NotNull CompoundTag p_30589_) {
        super.addAdditionalSaveData(p_30589_);
        p_30589_.putBoolean("EatingHaystack", this.isEating());
        p_30589_.putBoolean("Bred", this.isBred());
        p_30589_.putInt("Temper", this.getTemper());
        p_30589_.putBoolean("Tame", this.isTamed());
        if (this.getOwnerUUID() != null) {
            p_30589_.putUUID("Owner", this.getOwnerUUID());
        }

        if (!this.inventory.getItem(0).isEmpty()) {
            p_30589_.put("SaddleItem", this.inventory.getItem(0).save(new CompoundTag()));
        }

    }
    public boolean isBred() {
        return this.getFlag(8);
    }
    public void setBred(boolean p_30658_) {
        this.setFlag(8, p_30658_);
    }
    protected boolean isImmobile() {
        return super.isImmobile() && this.isVehicle() && this.isSaddled() || this.isEating() || this.isStanding();
    }
    protected void followMommy() {
        if (this.isBred() && this.isBaby() && !this.isEating()) {
            LivingEntity livingentity = this.level.getNearestEntity(this.getClass(), MOM_TARGETING, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().inflate(16.0D));
            if (livingentity != null && this.distanceToSqr(livingentity) > 4.0D) {
                this.navigation.createPath(livingentity, 0);
            }
        }

    }
    public boolean canEatGrass() {
        return true;
    }
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide && this.isAlive()) {
            if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }

            if (this.canEatGrass()) {
                if (!this.isEating() && !this.isVehicle() && this.random.nextInt(300) == 0 && this.level.getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
                    this.setEating(true);
                }

                if (this.isEating() && ++this.eatingCounter > 50) {
                    this.eatingCounter = 0;
                    this.setEating(false);
                }
            }

            this.followMommy();
        }
    }
    public boolean causeFallDamage(float p_149499_, float p_149500_, @NotNull DamageSource p_149501_) {
        if (p_149499_ > 1.0F) {
            this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
        }

        int i = this.calculateFallDamage(p_149499_, p_149500_);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(p_149501_, (float)i);
            if (this.isVehicle()) {
                for(Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(p_149501_, (float)i);
                }
            }

            this.playBlockFallSound();
            return true;
        }
    }
    protected int calculateFallDamage(float p_30606_, float p_30607_) {
        return Mth.ceil((p_30606_ * 0.5F - 3.0F) * p_30607_);
    }
    protected void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        this.updateContainerEquipment();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
    }
    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            this.setFlag(4, !this.inventory.getItem(0).isEmpty());
            this.setArmorEquipment(this.inventory.getItem(1));
            this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }
    public void containerChanged(@NotNull Container p_30548_) {
        boolean flag = this.isSaddled();
        this.updateContainerEquipment();
        if (this.tickCount > 20 && !flag && this.isSaddled()) {
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
        }

    }
    @Override
    public void openCustomInventoryScreen(@NotNull Player player) {

    }
    protected void setFlag(int p_30598_, boolean p_30599_) {
        byte b0 = this.entityData.get(DATA_ID_FLAGS);
        if (p_30599_) {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | p_30598_));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~p_30598_));
        }

    }
    public void setEating(boolean p_30662_) {
        this.setFlag(16, p_30662_);
    }
    public void setStanding(boolean p_30666_) {
        if (p_30666_) {
            this.setEating(false);
        }

        this.setFlag(32, p_30666_);
    }
    private void stand() {
        if (this.isControlledByLocalInstance() || this.isEffectiveAi()) {
            this.setStanding(true);
        }
    }
    public void onPlayerJump(int p_30591_) {
        if (this.isSaddled()) {
            if (p_30591_ < 0) {
                p_30591_ = 0;
            } else {
                this.allowStandSliding = true;
                this.stand();
            }

            if (p_30591_ >= 90) {
                this.playerJumpPendingScale = 1.0F;
            } else {
                this.playerJumpPendingScale = 0.4F + 0.4F * (float)p_30591_ / 90.0F;
            }

        }
    }
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable net.minecraft.core.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (itemHandler != null) {
            net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
            itemHandler = null;
            oldHandler.invalidate();
        }
    }
    @Nullable
    private Vec3 getDismountLocationInDirection(Vec3 p_30562_, LivingEntity p_30563_) {
        double d0 = this.getX() + p_30562_.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + p_30562_.z;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Pose pose : p_30563_.getDismountPoses()) {
            blockpos$mutableblockpos.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level.getBlockFloorHeight(blockpos$mutableblockpos);
                if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
                    break;
                }

                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB aabb = p_30563_.getLocalBoundsForPose(pose);
                    Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level, p_30563_, aabb.move(vec3))) {
                        p_30563_.setPose(pose);
                        return vec3;
                    }
                }

                blockpos$mutableblockpos.move(Direction.UP);
                if (!((double)blockpos$mutableblockpos.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }
    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(LivingEntity p_30576_) {
        Vec3 vec3 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)p_30576_.getBbWidth(), this.getYRot() + (p_30576_.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 vec31 = this.getDismountLocationInDirection(vec3, p_30576_);
        if (vec31 != null) {
            return vec31;
        } else {
            Vec3 vec32 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)p_30576_.getBbWidth(), this.getYRot() + (p_30576_.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
            Vec3 vec33 = this.getDismountLocationInDirection(vec32, p_30576_);
            return vec33 != null ? vec33 : this.position();
        }
    }
    protected float generateRandomMaxHealth(RandomSource p_218806_) {
        return 15.0F + (float)p_218806_.nextInt(8) + (float)p_218806_.nextInt(9);
    }
    protected double generateRandomJumpStrength(RandomSource p_218810_) {
        return (double)0.4F + p_218810_.nextDouble() * 0.2D + p_218810_.nextDouble() * 0.2D + p_218810_.nextDouble() * 0.2D;
    }
    protected double generateRandomSpeed(RandomSource p_218812_) {
        return ((double)0.45F + p_218812_.nextDouble() * 0.3D + p_218812_.nextDouble() * 0.3D + p_218812_.nextDouble() * 0.3D) * 0.25D;
    }
    public ItemStack getArmor() {
        return this.getItemBySlot(EquipmentSlot.CHEST);
    }
    private void setArmor(ItemStack p_30733_) {
        this.setItemSlot(EquipmentSlot.CHEST, p_30733_);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
    }
    private void setArmorEquipment(ItemStack p_30735_) {
        this.setArmor(p_30735_);
        if (!this.level.isClientSide) {
            Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
            if (this.isArmor(p_30735_)) {
                int i = ((HorseArmorItem)p_30735_.getItem()).getProtection();
                if (i != 0) {
                    Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
                }
            }
        }

    }
    protected void randomizeAttributes(RandomSource p_218815_) {
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(this.generateRandomMaxHealth(p_218815_));
        Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(this.generateRandomSpeed(p_218815_));
        Objects.requireNonNull(this.getAttribute(Attributes.JUMP_STRENGTH)).setBaseValue(this.generateRandomJumpStrength(p_218815_));
    }
    public boolean canWearArmor() {
        return true;
    }
    public boolean isArmor(ItemStack p_30731_) {
        return p_30731_.getItem() instanceof HorseArmorItem;
    }
    private SlotAccess createEquipmentSlotAccess(final int p_149503_, final Predicate<ItemStack> p_149504_) {
        return new SlotAccess() {
            public @NotNull ItemStack get() {
                return RPGEntityMount.this.inventory.getItem(p_149503_);
            }

            public boolean set(@NotNull ItemStack p_149528_) {
                if (!p_149504_.test(p_149528_)) {
                    return false;
                } else {
                    RPGEntityMount.this.inventory.setItem(p_149503_, p_149528_);
                    RPGEntityMount.this.updateContainerEquipment();
                    return true;
                }
            }
        };
    }
    public @NotNull SlotAccess getSlot(int p_149514_) {
        int i = p_149514_ - 400;
        if (i >= 0 && i < 2 && i < this.inventory.getContainerSize()) {
            if (i == 0) {
                return this.createEquipmentSlotAccess(i, (p_149518_) -> p_149518_.isEmpty() || p_149518_.is(Items.SADDLE));
            }

            if (i == 1) {
                if (!this.canWearArmor()) {
                    return SlotAccess.NULL;
                }

                return this.createEquipmentSlotAccess(i, (p_149516_) -> p_149516_.isEmpty() || this.isArmor(p_149516_));
            }
        }

        int j = p_149514_ - 500 + 2;
        return j >= 2 && j < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, j) : super.getSlot(p_149514_);
    }
    @Nullable
    public LivingEntity getControllingPassenger() {
        if (this.isSaddled()) {
            Entity entity = this.getFirstPassenger();
            if (entity instanceof LivingEntity) {
                return (LivingEntity)entity;
            }
        }

        return null;
    }
    @Nullable
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor p_30555_, @NotNull DifficultyInstance p_30556_, @NotNull MobSpawnType p_30557_, @Nullable SpawnGroupData p_30558_, @Nullable CompoundTag p_30559_) {
        if (p_30558_ == null) {
            p_30558_ = new AgeableMob.AgeableMobGroupData(0.2F);
        }

        this.randomizeAttributes(p_30555_.getRandom());
        return super.finalizeSpawn(p_30555_, p_30556_, p_30557_, p_30558_, p_30559_);
    }
    public boolean hasInventoryChanged(Container p_149512_) {
        return this.inventory != p_149512_;
    }
    @Override
    public boolean canJump() {
        return this.isSaddled();
    }
    @Override
    public void handleStartJump(int p_21695_) {
        this.allowStandSliding = true;
        this.stand();
    }
    @Override
    public void handleStopJump() {
    }
    protected boolean getFlag(int p_30648_) {
        return (this.entityData.get(DATA_ID_FLAGS) & p_30648_) != 0;
    }
    public boolean isTamed() {
        return this.getFlag(2);
    }
    @Override
    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby() && this.isTamed();
    }
    @Override
    public void equipSaddle(@Nullable SoundSource p_30546_) {
        this.inventory.setItem(0, new ItemStack(Items.SADDLE));
        if (p_30546_ != null) {
            this.level.playSound((Player)null, this, SoundEvents.HORSE_SADDLE, p_30546_, 0.5F, 1.0F);
        }
    }
    private void openMouth() {
        if (!this.level.isClientSide) {
            this.setFlag(64, true);
        }

    }
    protected boolean canParent() {
        return !this.isVehicle() && !this.isPassenger() && this.isTamed() && !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }
    public @NotNull InteractionResult mobInteract(Player p_30713_, @NotNull InteractionHand p_30714_) {
        ItemStack itemstack = p_30713_.getItemInHand(p_30714_);
        if (!this.isBaby()) {
            if (this.isTamed() && p_30713_.isSecondaryUseActive()) {
                this.openCustomInventoryScreen(p_30713_);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }

            if (this.isVehicle()) {
                return super.mobInteract(p_30713_, p_30714_);
            }
        }

        if (!itemstack.isEmpty()) {
            if (this.isFood(itemstack)) {
                return this.fedFood(p_30713_, itemstack);
            }

            InteractionResult interactionresult = itemstack.interactLivingEntity(p_30713_, this, p_30714_);
            if (interactionresult.consumesAction()) {
                return interactionresult;
            }

            if (!this.isTamed()) {
                this.makeMad();
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }

            boolean flag = !this.isBaby() && !this.isSaddled() && itemstack.is(Items.SADDLE);
            if (this.isArmor(itemstack) || flag) {
                this.openCustomInventoryScreen(p_30713_);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }

        if (this.isBaby()) {
            return super.mobInteract(p_30713_, p_30714_);
        } else {
            this.doPlayerRide(p_30713_);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
    }
    @Nullable
    protected SoundEvent getAngrySound() {
        this.stand();
        return null;
    }
    public void makeMad() {
        if (!this.isStanding()) {
            this.stand();
            SoundEvent soundevent = this.getAngrySound();
            if (soundevent != null) {
                this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
            }
        }

    }
    public boolean canMate(@NotNull Animal p_30698_) {
        if (p_30698_ == this) {
            return false;
        }
        else {
            return this.canParent() && ((RPGEntityMount)p_30698_).canParent();
        }
    }
    public InteractionResult fedFood(Player p_30581_, ItemStack p_30582_) {
        boolean flag = this.handleEating(p_30581_, p_30582_);
        if (!p_30581_.getAbilities().instabuild) {
            p_30582_.shrink(1);
        }

        if (this.level.isClientSide) {
            return InteractionResult.CONSUME;
        } else {
            return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
    }
    private void eating() {
        this.openMouth();
        if (!this.isSilent()) {
            SoundEvent soundevent = this.getEatingSound(Items.APPLE.getDefaultInstance());
            if (soundevent != null) {
                this.level.playSound((Player)null, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }
        }

    }
    protected boolean handleEating(Player p_30593_, ItemStack p_30594_) {
        boolean flag = false;
        float f = 0.0F;
        int i = 0;
        int j = 0;
        if (p_30594_.is(Items.WHEAT)) {
            f = 2.0F;
            i = 20;
            j = 3;
        } else if (p_30594_.is(Items.SUGAR)) {
            f = 1.0F;
            i = 30;
            j = 3;
        } else if (p_30594_.is(Blocks.HAY_BLOCK.asItem())) {
            f = 20.0F;
            i = 180;
        } else if (p_30594_.is(Items.APPLE)) {
            f = 3.0F;
            i = 60;
            j = 3;
        } else if (p_30594_.is(Items.GOLDEN_CARROT)) {
            f = 4.0F;
            i = 60;
            j = 5;
            if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
                flag = true;
                this.setInLove(p_30593_);
            }
        } else if (p_30594_.is(Items.GOLDEN_APPLE) || p_30594_.is(Items.ENCHANTED_GOLDEN_APPLE)) {
            f = 10.0F;
            i = 240;
            j = 10;
            if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
                flag = true;
                this.setInLove(p_30593_);
            }
        }

        if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
            this.heal(f);
            flag = true;
        }

        if (this.isBaby() && i > 0) {
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
            if (!this.level.isClientSide) {
                this.ageUp(i);
            }

            flag = true;
        }

        if (j > 0 && (flag || !this.isTamed()) && this.getTemper() < 100) {
            flag = true;
            if (!this.level.isClientSide) {
                this.modifyTemper(j);
            }
        }

        if (flag) {
            this.eating();
            this.gameEvent(GameEvent.EAT);
        }

        return flag;
    }
    @Override
    public boolean isSaddled() {
        return this.getFlag(4);
    }
    public int getTemper() {
        return this.temper;
    }
    public void setTemper(int p_30650_) {
        this.temper = p_30650_;
    }
    public int modifyTemper(int p_30654_) {
        int i = Mth.clamp(this.getTemper() + p_30654_, 0, 100);
        this.setTemper(i);
        return i;
    }

    public static AttributeSupplier setDefaultAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 0.3f)
                .add(Attributes.ATTACK_KNOCKBACK,1.0D)
                .add(Attributes.ARMOR,1.0D)
                .add(Attributes.ARMOR_TOUGHNESS,1.0D)
                .add(Attributes.LUCK,1.13D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.08D)
                .build();
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
