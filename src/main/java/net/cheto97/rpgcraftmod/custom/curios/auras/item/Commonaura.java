package net.cheto97.rpgcraftmod.custom.curios.auras.item;

import net.cheto97.rpgcraftmod.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;


public class Commonaura extends Item implements ICurioItem {

    public Commonaura(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {

        return wearer instanceof Player;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();

        if (!player.level.isClientSide()) {
            boolean hasPlayerFireResistance =
                    player.getActiveEffects().equals(MobEffects.FIRE_RESISTANCE);

            if (!hasPlayerFireResistance) {
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200));
            }
        }
    }
}
