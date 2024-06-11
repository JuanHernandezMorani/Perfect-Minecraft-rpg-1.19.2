package net.cheto97.rpgcraftmod.item.wings.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.wings.WingItem;
import net.cheto97.rpgcraftmod.item.wings.model.*;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Objects;

public class WingsFeatureRenderer implements ICurioRenderer {
    private WingEntityModel<LivingEntity> wingModel;
    private final LightWingsModel lightWings;
    private final ZanzasWingsModel zanzasWings;
    public  WingsFeatureRenderer(ModelPart modelLight, ModelPart modelZanzas) {
        this.lightWings = new LightWingsModel(modelLight);
        this.zanzasWings = new ZanzasWingsModel(modelZanzas);
    }
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                                                                          PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer, int light, float limbSwing,
                                                                          float limbSwingAmount, float partialTicks, float ageInTicks,
                                                                          float netHeadYaw, float headPitch) {
        if (stack.getItem() instanceof WingItem wingItem) {

            createRenderer(wingItem,slotContext,matrixStack,renderTypeBuffer,light,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
        }
        else {
            CuriosApi.getCuriosHelper().getEquippedCurios(slotContext.entity()).ifPresent(wing ->{
                ItemStack item = wing.getStackInSlot(wing.getSlots());
                if(item.getItem() instanceof WingItem wingItem) {
                    createRenderer(wingItem,slotContext,matrixStack,renderTypeBuffer,light,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
                }
            });
        }
    }
    public void renderWings(PoseStack matrices, MultiBufferSource renderTypeBuffer, @Nullable ItemStack stack, RenderType renderType, int light, float r, float g, float b) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, renderType, false, stack != null && stack.isEnchanted());
        this.wingModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
    }
    private String getWingItemType(WingItem wingItem){
       return wingItem.getWingType() != WingItem.WingType.UNIQUE ? wingItem.getWingType().toString().toLowerCase(Locale.ROOT) : Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(wingItem.asItem())).getPath().replaceAll("_wings", "");
    }
    private float[] getWingItemPrimaryColor(WingItem item){
        return item.getPrimaryColour().getTextureDiffuseColors();
    }
    private float[] getWingItemSecondaryColor(WingItem item){
        return item.getSecondaryColour().getTextureDiffuseColors();
    }
    private void createRenderer(WingItem wingItem, SlotContext slotContext,
                                PoseStack matrixStack,
                                MultiBufferSource renderTypeBuffer, int light, float limbSwing,
                                float limbSwingAmount, float ageInTicks,
                                float netHeadYaw, float headPitch){

        float[] primaryColour = getWingItemPrimaryColor(wingItem);
        float[] secondaryColour = getWingItemSecondaryColor(wingItem);
        String wingType = getWingItemType(wingItem);

        float r1 = primaryColour[0];
        float g1 = primaryColour[1];
        float b1 = primaryColour[2];
        float r2 = secondaryColour[0];
        float g2 = secondaryColour[1];
        float b2 = secondaryColour[2];

        wingModel = wingItem.getWingType() == WingItem.WingType.LIGHT ? lightWings : zanzasWings;

            ResourceLocation layer1 = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/" + wingType + "_wings.png");
            ResourceLocation layer2 = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/" + wingType + "_wings_2.png");

            matrixStack.pushPose();
            matrixStack.translate(0.0D, 0.0D, 0.125D);
            this.wingModel.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.renderWings(matrixStack, renderTypeBuffer, wingItem.getDefaultInstance(), RenderType.entityTranslucent(layer2), light, r2, g2, b2);
            this.renderWings(matrixStack, renderTypeBuffer, wingItem.getDefaultInstance(), RenderType.entityTranslucent(layer1), light, r1, g1, b1);
            matrixStack.popPose();
    }
}