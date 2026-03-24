package net.cheto97.rpgcraftmod.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class GeckoWingCurioRenderer implements ICurioRenderer {
    private final GeckoWingItemRenderer renderer = new GeckoWingItemRenderer();

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                                                                          PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource buffer, int light, float limbSwing,
                                                                          float limbSwingAmount, float partialTicks, float ageInTicks,
                                                                          float netHeadYaw, float headPitch) {
        poseStack.pushPose();

        if (renderLayerParent.getModel() instanceof HumanoidModel<?> humanoidModel) {
            humanoidModel.body.translateAndRotate(poseStack);
        }

        poseStack.translate(0.0D, 0.1D, 0.3D);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(1.0F, 1.0F, 1.0F);

        renderer.renderByItem(stack, ItemDisplayContext.NONE, poseStack, buffer, light, 0);

        poseStack.popPose();
    }
}
