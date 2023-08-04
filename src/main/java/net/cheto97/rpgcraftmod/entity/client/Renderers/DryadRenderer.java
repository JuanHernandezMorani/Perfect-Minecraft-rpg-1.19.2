package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.client.Models.DryadModel;
import net.cheto97.rpgcraftmod.entity.custom.Dryad;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DryadRenderer extends HumanoidMobRenderer<Dryad, DryadModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/dryad.png");

    public DryadRenderer(final EntityRendererProvider.Context context) {
        super(context, new DryadModel(context.bakeLayer(DryadModel.LAYER_LOCATION_D)), 0.5f);
    }

   @Override
    public @NotNull ResourceLocation getTextureLocation(final Dryad pEntity) {
        return TEXTURE;
    }
}
