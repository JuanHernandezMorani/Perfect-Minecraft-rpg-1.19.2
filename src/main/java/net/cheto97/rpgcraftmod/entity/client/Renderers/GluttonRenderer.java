package net.cheto97.rpgcraftmod.entity.client.Renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.client.Models.GluttonModel;
import net.cheto97.rpgcraftmod.entity.custom.Glutton;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class GluttonRenderer extends GeoMobRenderer<Glutton> {
    public GluttonRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GluttonModel());
    }

    private static final ResourceLocation SAND = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/glutton.png");
    private static final ResourceLocation RED_SAND = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/glutton_red.png");
    private static final ResourceLocation SWAMP = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/glutton_swamp.png");

    @Override
    public RenderType getRenderType(Glutton glutton, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(glutton));
    }

   @Override
    public @NotNull ResourceLocation getTextureLocation(Glutton entity) {
        return switch (entity.getVariant()) {
            case 1 -> RED_SAND;
            case 2 -> SWAMP;
            default -> SAND;
        };
    }
}
