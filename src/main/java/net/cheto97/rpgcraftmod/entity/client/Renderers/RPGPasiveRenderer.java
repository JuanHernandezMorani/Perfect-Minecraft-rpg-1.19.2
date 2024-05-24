package net.cheto97.rpgcraftmod.entity.client.Renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.entity.client.Models.RPGPasiveModel;
import net.cheto97.rpgcraftmod.entity.custom.prefabs.RPGEntityPasive;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

import static net.cheto97.rpgcraftmod.util.EntityUtils.textureResourceLocation;

public abstract class RPGPasiveRenderer<T extends RPGEntityPasive> extends GeoEntityRenderer<T> {
    protected abstract String getTextureName();
    protected abstract float getShadowRadius();
    protected abstract float getScaleX();
    protected abstract float getScaleY();
    protected abstract float getScaleZ();

    RPGPasiveRenderer(EntityRendererProvider.Context context, RPGPasiveModel<T> modelProvider) {
        super(context, modelProvider);
        this.shadowRadius = getShadowRadius();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T instance) {
        return textureResourceLocation(getTextureName());
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(getScaleX(), getScaleY(), getScaleZ());
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
