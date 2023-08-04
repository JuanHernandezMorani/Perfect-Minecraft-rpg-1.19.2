package net.cheto97.rpgcraftmod.entity.client.Renderers;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.NotNull;

public class ManaCreeperRenderer extends CreeperRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/mana_creeper.png");

    public ManaCreeperRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

   @Override
    public @NotNull ResourceLocation getTextureLocation(Creeper pEntity) {
        return TEXTURE;
    }
}
