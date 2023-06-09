package net.cheto97.rpgcraftmod.item.wings.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.wings.WingItem;
import net.cheto97.rpgcraftmod.item.wings.model.*;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
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

import static net.cheto97.rpgcraftmod.event.ClientEvents.*;

public class WingsFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> implements ICurioRenderer {
    private WingEntityModel<T> wingModel;
    private final FeatheredWingsModel<T> featheredWings;
    private final LeatherWingsModel<T> leatherWings;
    private final LightWingsModel<T> lightWings;
    private final FlandresWingsModel<T> flandresWings;
    private final DiscordsWingsModel<T> discordsWings;
    private final ZanzasWingsModel<T> zanzasWings;

    public WingsFeatureRenderer(EntityRendererProvider.Context context) {
        super();
        this.featheredWings = new FeatheredWingsModel<>(context.bakeLayer(FEATHERED));
        this.leatherWings = new LeatherWingsModel<>(context.bakeLayer(LEATHER));
        this.lightWings = new LightWingsModel<>(context.bakeLayer(LIGHT));
        this.flandresWings = new FlandresWingsModel<>(context.bakeLayer(FLANDRE));
        this.discordsWings = new DiscordsWingsModel<>(context.bakeLayer(DISCORD));
        this.zanzasWings = new ZanzasWingsModel<>(context.bakeLayer(ZANZA));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                                                                          PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer, int light, float limbSwing,
                                                                          float limbSwingAmount, float partialTicks, float ageInTicks,
                                                                          float netHeadYaw, float headPitch) {
        if (stack.getItem() instanceof WingItem wingItem) {
            float[] primaryColour = wingItem.getPrimaryColour().getTextureDiffuseColors();
            float[] secondaryColour = wingItem.getSecondaryColour().getTextureDiffuseColors();
            float r1 = primaryColour[0];
            float g1 = primaryColour[1];
            float b1 = primaryColour[2];
            float r2 = secondaryColour[0];
            float g2 = secondaryColour[1];
            float b2 = secondaryColour[2];

            String wingType = wingItem.getWingType() != WingItem.WingType.UNIQUE ? wingItem.getWingType().toString().toLowerCase(Locale.ROOT) : ForgeRegistries.ITEMS.getKey(wingItem.asItem()).getPath().replaceAll("_wings", "");

            if(wingItem.getWingType() == WingItem.WingType.FEATHERED || wingItem.getWingType() == WingItem.WingType.MECHANICAL_FEATHERED)
                wingModel = featheredWings;
            if(wingItem.getWingType() == WingItem.WingType.DRAGON || wingItem.getWingType() == WingItem.WingType.MECHANICAL_LEATHER)
                wingModel = leatherWings;
            if(wingItem.getWingType() == WingItem.WingType.LIGHT)
                wingModel = lightWings;
            if(wingType.equals("flandres"))
                wingModel = flandresWings;
            if(wingType.equals("discords"))
                wingModel = discordsWings;
            if(wingType.equals("zanzas"))
                wingModel = zanzasWings;

            ResourceLocation layer1 = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/" + wingType + "_wings.png");
            ResourceLocation layer2 = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/model/" + wingType + "_wings_2.png");

            matrixStack.pushPose();
            matrixStack.translate(0.0D, 0.0D, 0.125D);
            this.wingModel.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.renderWings(matrixStack, renderTypeBuffer, wingItem.getDefaultInstance(), RenderType.entityTranslucent(layer2), light, r2, g2, b2);
            this.renderWings(matrixStack, renderTypeBuffer, wingItem.getDefaultInstance(), RenderType.entityTranslucent(layer1), light, r1, g1, b1);
            matrixStack.popPose();
        }
        else {
            CuriosApi.getCuriosHelper().getEquippedCurios(slotContext.entity()).ifPresent(wing ->{
                ItemStack item = wing.getStackInSlot(wing.getSlots());
                if(item.getItem() instanceof WingItem wingItem) {
                    float[] primaryColour = wingItem.getPrimaryColour().getTextureDiffuseColors();
                    float[] secondaryColour = wingItem.getSecondaryColour().getTextureDiffuseColors();
                    float r1 = primaryColour[0];
                    float g1 = primaryColour[1];
                    float b1 = primaryColour[2];
                    float r2 = secondaryColour[0];
                    float g2 = secondaryColour[1];
                    float b2 = secondaryColour[2];

                    String wingType = wingItem.getWingType() != WingItem.WingType.UNIQUE ? wingItem.getWingType().toString().toLowerCase(Locale.ROOT) : ForgeRegistries.ITEMS.getKey(wingItem.asItem()).getPath().replaceAll("_wings", "");

                    if(wingItem.getWingType() == WingItem.WingType.FEATHERED || wingItem.getWingType() == WingItem.WingType.MECHANICAL_FEATHERED)
                        wingModel = featheredWings;
                    if(wingItem.getWingType() == WingItem.WingType.DRAGON || wingItem.getWingType() == WingItem.WingType.MECHANICAL_LEATHER)
                        wingModel = leatherWings;
                    if(wingItem.getWingType() == WingItem.WingType.LIGHT)
                        wingModel = lightWings;
                    if(wingType.equals("flandres"))
                        wingModel = flandresWings;
                    if(wingType.equals("discords"))
                        wingModel = discordsWings;
                    if(wingType.equals("zanzas"))
                        wingModel = zanzasWings;

                    ResourceLocation layer1 = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/" + wingType + "_wings.png");
                    ResourceLocation layer2 = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/entity/" + wingType + "_wings_2.png");

                    matrixStack.pushPose();
                    matrixStack.translate(0.0D, 0.0D, 0.125D);
                    this.wingModel.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    this.renderWings(matrixStack, renderTypeBuffer, stack, RenderType.entityTranslucent(layer2), light, r2, g2, b2);
                    this.renderWings(matrixStack, renderTypeBuffer, stack, RenderType.entityTranslucent(layer1), light, r1, g1, b1);
                    matrixStack.popPose();
                }
            });
        }
    }

    public void renderWings(PoseStack matrices, MultiBufferSource renderTypeBuffer, @Nullable ItemStack stack, RenderType renderType, int light, float r, float g, float b) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, renderType, false, stack != null && stack.isEnchanted());
        this.wingModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
    }

}
