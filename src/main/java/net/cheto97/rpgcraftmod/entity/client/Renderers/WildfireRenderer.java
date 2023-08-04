package net.cheto97.rpgcraftmod.entity.client.Renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.client.Models.WildfireModel;
import net.cheto97.rpgcraftmod.entity.custom.Wildfire;
import net.cheto97.rpgcraftmod.util.OV.Config;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class WildfireRenderer extends GeoEntityRenderer<Wildfire> {
    public WildfireRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new WildfireModel());
    }

    private static final ResourceLocation DEFAULT = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/wildfire/wildfire.png");
    private static final ResourceLocation SOUL = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/wildfire/wildfire_soul.png");

    @Override
    public RenderType getRenderType(Wildfire wildfire, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(wildfire));
    }

    @Override
    protected int getBlockLightLevel(Wildfire arg, BlockPos arg2) {
        return 15;
    }

   @Override
    public @NotNull ResourceLocation getTextureLocation(Wildfire entity) {
        if (entity.getVariant() == 0 || !Config.wildfireVariants.get()) {
            return DEFAULT;
        }
        return SOUL;
    }
}