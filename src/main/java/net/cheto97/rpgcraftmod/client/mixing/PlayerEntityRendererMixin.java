package net.cheto97.rpgcraftmod.client.mixing;

import net.cheto97.rpgcraftmod.item.wings.model.WingEntityModel;
import net.cheto97.rpgcraftmod.item.wings.renderer.WingsFeatureRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@Mixin(PlayerRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> {
    public PlayerEntityRendererMixin(EntityRendererProvider.Context context, EntityModel<LivingEntity> slim, CallbackInfo info) {
        super(context, slim,0);
        this.addLayer(new RenderLayer<>(this, new WingsFeatureRenderer<>(context)));
    }
}
