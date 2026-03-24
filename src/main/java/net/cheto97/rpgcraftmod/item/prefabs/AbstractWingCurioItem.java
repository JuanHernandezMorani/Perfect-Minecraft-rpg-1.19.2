package net.cheto97.rpgcraftmod.item.prefabs;

import net.cheto97.rpgcraftmod.item.ModCreativeModeTab;
import net.cheto97.rpgcraftmod.item.renderer.GeckoWingItemRenderer;
import net.cheto97.rpgcraftmod.util.WingHelper;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Consumer;

public abstract class AbstractWingCurioItem extends Item implements ICurioItem, IAnimatable {
    public static final String ANIM_STATE_TAG = "WingAnimState";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    protected AbstractWingCurioItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .defaultDurability(1200)
                .durability(1200)
                .rarity(Rarity.RARE)
                .tab(ModCreativeModeTab.RPGCRAFT_WINGS_TAB));
    }

    public abstract WingDefinition getWingDefinition(ItemStack stack);

    public String getWingName(ItemStack stack) {
        return getWingDefinition(stack).id();
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public boolean isUsable(ItemStack stack, LivingEntity entity) {
        return entity instanceof Player && stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    @NotNull
    @Override
    public ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
        return ICurio.DropRule.ALWAYS_DROP;
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return isUsable(stack, entity);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            WingHelper.tickWingFlight(player, stack, getWingDefinition(stack), slotContext);
        }
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

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeckoWingItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new GeckoWingItemRenderer();
                }
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "wing_controller", 0, this::animationPredicate));
    }

    private <T extends IAnimatable> PlayState animationPredicate(AnimationEvent<T> event) {
        if (!(event.getExtraDataOfType(ItemStack.class).stream().findFirst().orElse(ItemStack.EMPTY).getItem() instanceof AbstractWingCurioItem)) {
            return PlayState.STOP;
        }

        ItemStack stack = event.getExtraDataOfType(ItemStack.class).stream().findFirst().orElse(ItemStack.EMPTY);
        String state = stack.getOrCreateTag().getString(ANIM_STATE_TAG);
        String anim = switch (state) {
            case "loop" -> "animation.wing.loop";
            case "boost" -> "animation.wing.boost";
            case "flight" -> "animation.wing.flight";
            default -> "animation.wing.idle";
        };

        event.getController().setAnimation(new AnimationBuilder().addAnimation(anim, true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
