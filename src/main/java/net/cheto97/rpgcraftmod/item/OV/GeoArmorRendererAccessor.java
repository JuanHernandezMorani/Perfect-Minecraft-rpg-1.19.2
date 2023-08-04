package net.cheto97.rpgcraftmod.item.OV;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mixin(GeoArmorRenderer.class)
public interface GeoArmorRendererAccessor {
    @Accessor
    LivingEntity getEntityLiving();
}