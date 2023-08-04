package net.cheto97.rpgcraftmod.entity.client.Renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.client.Models.WildfireHelmetModel;
import net.cheto97.rpgcraftmod.item.OV.GeoArmorRendererAccessor;
import net.cheto97.rpgcraftmod.item.OV.WildfireHelmetItem;
import net.cheto97.rpgcraftmod.util.OV.Config;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class WildfireHelmetRenderer extends GeoArmorRenderer<WildfireHelmetItem> {
    private static final ResourceLocation HELMET_TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/wildfire/wildfire.png");
    private static final ResourceLocation HELMET_TEXTURE_SOUL = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/wildfire/wildfire_soul.png");

    public WildfireHelmetRenderer() {
        super(new WildfireHelmetModel());

        this.headBone = "helmet";
        this.bodyBone = "chestplate";
        this.rightArmBone = "rightArm";
        this.leftArmBone = "leftArm";
        this.rightLegBone = "rightLeg";
        this.leftLegBone = "leftLeg";
        this.rightBootBone = "rightBoot";
        this.leftBootBone = "leftBoot";
    }

    @Override
    public void renderEarly(WildfireHelmetItem item, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(item, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (((GeoArmorRendererAccessor) this).getEntityLiving().isBaby()) {
            stackIn.scale(0.7F, 0.7F, 0.7F);
            stackIn.translate(0F, -0.4F, 0F);
        }
    }

   @Override
    public @NotNull ResourceLocation getTextureLocation(WildfireHelmetItem instance) {
        ItemStack stack = ((GeoArmorRendererAccessor) this).getEntityLiving().getItemBySlot(EquipmentSlot.HEAD);
        if (Config.wildfireVariants.get()) {
            if (stack.getTag() != null && stack.getTag().getFloat("SoulTexture") == 1.0F) {
                return HELMET_TEXTURE_SOUL;
            }
        }
        return HELMET_TEXTURE;
    }
}
