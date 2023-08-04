package net.cheto97.rpgcraftmod.entity.client.Renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.client.Models.BarnacleModel;
import net.cheto97.rpgcraftmod.entity.custom.Barnacle;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class BarnacleRenderer extends GeoEntityRenderer<Barnacle> {
    public BarnacleRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BarnacleModel());
    }

    @Override
    public RenderType getRenderType(Barnacle barnacle, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(barnacle));
    }

    @Override
    public ResourceLocation getTextureLocation(Barnacle entity) {
        return new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/barnacle.png");
    }
}