package net.cheto97.rpgcraftmod.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.prefabs.CustomWingsItem;
import net.cheto97.rpgcraftmod.item.model.WingEntityModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import javax.annotation.Nullable;

public class CustomWingsFeatureRenderer  implements ICurioRenderer {
    private final WingEntityModel<LivingEntity> wingModel;
    public CustomWingsFeatureRenderer(ModelPart wingPart){ this.wingModel = new WingEntityModel<>(wingPart);}

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                                                                          PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer, int light, float limbSwing,
                                                                          float limbSwingAmount, float partialTicks, float ageInTicks,
                                                                          float netHeadYaw, float headPitch){
        if (stack.getItem() instanceof CustomWingsItem wingItem) {
            createRenderer(wingItem,slotContext,matrixStack,renderTypeBuffer,light,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
        }
        else {
            CuriosApi.getCuriosHelper().getEquippedCurios(slotContext.entity()).ifPresent(wing ->{
                ItemStack item = wing.getStackInSlot(wing.getSlots());
                if(item.getItem() instanceof CustomWingsItem wingItem) {
                    createRenderer(wingItem,slotContext,matrixStack,renderTypeBuffer,light,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
                }
            });
        }
    }
    public void renderWings(PoseStack matrices, MultiBufferSource renderTypeBuffer, @Nullable ItemStack stack, RenderType renderType, int light) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, renderType, false, stack != null && stack.isEnchanted());
        this.wingModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
    }
    private void createRenderer(CustomWingsItem wingItem, SlotContext slotContext,
                                PoseStack matrixStack,
                                MultiBufferSource renderTypeBuffer, int light, float limbSwing,
                                float limbSwingAmount, float ageInTicks,
                                float netHeadYaw, float headPitch){

        String wingName = wingItem.getWingName();

        ResourceLocation layer = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/" + wingName + ".png");

        matrixStack.pushPose();
        this.wingModel.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.renderWings(matrixStack, renderTypeBuffer, wingItem.getDefaultInstance(), RenderType.armorCutoutNoCull(layer), light);
        matrixStack.popPose();
    }
}
