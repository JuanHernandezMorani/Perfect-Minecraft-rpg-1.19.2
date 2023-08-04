package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EmptyRenderer extends EntityRenderer<Entity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("missingno");

    public EmptyRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

   @Override
    public @NotNull ResourceLocation getTextureLocation(Entity pEntity) {
        return TEXTURE;
    }
}
