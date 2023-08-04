package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.client.Models.WintersGraspModel;
import net.cheto97.rpgcraftmod.entity.custom.WintersGrasp;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WintersGraspRenderer extends NonLiving3DModelRenderer<WintersGrasp, WintersGraspModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/ice_guardian.png");

    public WintersGraspRenderer(EntityRendererProvider.Context context) {
        super(context, new WintersGraspModel(context.bakeLayer(WintersGraspModel.LAYER_LOCATION_WG)));
    }

   @Override
    public @NotNull ResourceLocation getTextureLocation(WintersGrasp pEntity) {
        return TEXTURE;
    }
}
