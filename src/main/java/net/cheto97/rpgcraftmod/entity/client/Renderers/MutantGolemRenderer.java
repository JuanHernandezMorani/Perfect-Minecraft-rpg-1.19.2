package net.cheto97.rpgcraftmod.entity.client.Renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.entity.client.Models.MutantGolemModel;
import net.cheto97.rpgcraftmod.entity.custom.MutantGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import static net.cheto97.rpgcraftmod.util.EntityUtils.textureResourceLocation;

public class MutantGolemRenderer extends GeoEntityRenderer<MutantGolemEntity> {
    public MutantGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MutantGolemModel());
        this.shadowRadius = 1.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(MutantGolemEntity instance) {
        return textureResourceLocation("mutant_golem");
    }

    @Override
    public RenderType getRenderType(MutantGolemEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {

        stack.scale(0.8f, 0.8f, 0.8f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);

    }
}