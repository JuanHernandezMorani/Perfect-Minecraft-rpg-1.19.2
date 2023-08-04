package net.cheto97.rpgcraftmod.item.OV;

import net.cheto97.rpgcraftmod.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class WildfireHelmetItem extends GeoArmorItem implements IAnimatable {
    private int timer = 0;

    public WildfireHelmetItem() {
        super(ModArmor.WILDFIRE, EquipmentSlot.HEAD, ModItems.ITEM_PROPERTIES.fireResistant());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (slot == EquipmentSlot.HEAD.getIndex()) {
            LivingEntity livingEntity = (LivingEntity) entity;
                if (livingEntity.isOnFire()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1, 0, false, false, true));
                    if (timer % 3 == 0) {
                        stack.hurtAndBreak(1 + (timer / 600), livingEntity, consumer -> consumer.broadcastBreakEvent(EquipmentSlot.HEAD));
                    }
                    timer++;
                } else {
                    timer = 0;
                }
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        if (stack.getItem() instanceof WildfireHelmetItem) {
            return Component.literal("WildFire Helmet");
        }
        return super.getName(stack);
    }

    private final AnimationFactory factory = new AnimationFactory(this);

    @Override
    public void registerControllers(AnimationData animationData) {
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
