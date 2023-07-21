package net.cheto97.rpgcraftmod.item.wings;

import net.cheto97.rpgcraftmod.custom.ModCreativeModeTab;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.util.WingHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class WingItem extends Item implements ICurioItem {
    private final DyeColor primaryColour;
    private final DyeColor secondaryColour;
    private final WingType wingType;
    private boolean isFlying = false;
    private boolean slowFalling = false;
    private boolean slowFlySpeed = false;
    private int slowCooldown = 0;


    public WingItem(DyeColor primaryColour, DyeColor secondaryColour, WingType wingType){
        super(new Item.Properties().stacksTo(1).durability(0).rarity(wingType == WingType.UNIQUE ? Rarity.EPIC : Rarity.RARE).tab(ModCreativeModeTab.RPGCRAFT_TAB));
        this.primaryColour = primaryColour;
        this.secondaryColour = secondaryColour;
        this.wingType = wingType;
    }

    @NotNull
    @Override
    public ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
        return ICurio.DropRule.ALWAYS_DROP;
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
            slotContext.entity().level.addParticle(ParticleTypes.CRIT, slotContext.entity().getX(), slotContext.entity().getY() + 0.5, slotContext.entity().getZ(), 0, 0, 0);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if(slotContext.entity() instanceof Player player){
            if((player.isOnGround() || player.isUnderWater() || player.isInWater() || player.hasEffect(MobEffects.LEVITATION))){
                WingHelper.stopFlying(player);
                slowFalling = false;
                isFlying = false;
                if(player.hasEffect(MobEffects.SLOW_FALLING)){
                    player.removeEffect(MobEffects.SLOW_FALLING);
                }
            }
            if(player.isFallFlying()){
                if(isFlying && player.flyingSpeed > 0 && !player.isOnGround() && (int)player.getY() < 450 && !slowFlySpeed && !slowFalling) {
                    if(player.hasEffect(MobEffects.SLOW_FALLING)){
                        player.removeEffect(MobEffects.SLOW_FALLING);
                    }
                    WingHelper.applySpeed(player,stack);
                }
                if(player.isCrouching() && !player.isUnderWater() && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION)) {
                    WingHelper.stopFlying(player);
                    slowFalling = true;
                }
                if((int)player.getY() > 450){
                    player.setDeltaMovement(1.0f,-0.4f,1.0f);
                    slowFlySpeed = true;
                    slowCooldown = 40;
                }
                if(slowFlySpeed){
                    if(slowCooldown <= 0){
                        slowFlySpeed = false;
                    }else{
                        slowCooldown--;
                    }
                }
            }
            else{
                if(slowFalling){
                    player.fallDistance = 0F;
                    if(!player.hasEffect(MobEffects.SLOW_FALLING)) {
                        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 1000000, 1));
                    }
                }
                if( !player.isOnGround() && !slowFalling && !isFlying && !player.isCrouching() && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION) && player.fallDistance > 0.06F) {
                    player.startFallFlying();
                    isFlying = true;
                }
            }
        }
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack stack, ItemStack ingredient) {
        return ingredient.is(ModItems.zafiro.get());
    }

    @Override
    public boolean isRepairable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public float getXpRepairRatio(ItemStack stack) {
        return 0.01f;
    }

    @Nullable
    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_ELYTRA;
    }

    public DyeColor getPrimaryColour() {
        return this.primaryColour;
    }

    public DyeColor getSecondaryColour() {
        return this.secondaryColour;
    }

    public WingType getWingType() {
        return this.wingType;
    }
    public enum WingType {
        FEATHERED, DRAGON, MECHANICAL_FEATHERED, MECHANICAL_LEATHER, LIGHT, UNIQUE
    }
}
