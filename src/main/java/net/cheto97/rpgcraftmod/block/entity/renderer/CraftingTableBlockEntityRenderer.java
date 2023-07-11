package net.cheto97.rpgcraftmod.block.entity.renderer;


import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.block.entity.CraftingTableBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import com.mojang.math.Vector3f;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CraftingTableBlockEntityRenderer implements BlockEntityRenderer<CraftingTableBlockEntity> {
    private final ItemRenderer itemRenderer;

    public CraftingTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(CraftingTableBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        combinedLightIn = blockEntity.getLevel() != null ? LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above()) : 15728880;
        for (int i = 0; i < blockEntity.getContainerSize(); ++i) {
            ItemStack itemStack = blockEntity.getItem(i);
            if (!itemStack.isEmpty()) {
                this.renderIngredientItem(blockEntity, partialTicks, poseStack, bufferIn, combinedLightIn, combinedOverlayIn, i, itemStack);
            }
        }
        if (!blockEntity.getLastResult().isEmpty()) {
            this.renderResultItem(blockEntity.getLastResult(), blockEntity.getLevel(), blockEntity.ticks + partialTicks, poseStack, bufferIn, combinedLightIn);
        }
    }

    private void renderIngredientItem(CraftingTableBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, int i, ItemStack itemStack) {
        poseStack.pushPose();

        this.setupLayingRenderer(blockEntity, partialTicks, poseStack, itemStack, i);

        this.itemRenderer.renderStatic(itemStack, TransformType.FIXED, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, (int) blockEntity.getBlockPos().asLong() + i);
        poseStack.popPose();
    }

    private void setupFloatingRenderer(CraftingTableBlockEntity blockEntity, float partialTicks, PoseStack poseStack, ItemStack itemStack, int index) {
        float shift = (float) Math.abs(((blockEntity.ticks + partialTicks) * 50.0 + (index * 1000L)) % 5000L - 2500L) / 200000.0F;
        BakedModel model = this.itemRenderer.getModel(itemStack, null, null, 0);
        boolean blockItem = model.isGui3d();
        poseStack.translate(0.5, shift, 0.5);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, blockEntity.currentAngle, blockEntity.nextAngle)));
        poseStack.translate((double) (index % 3) * 3.0 / 16.0 + 0.3125 - 0.5, 1.09375, (double) (index / 3) * 3.0 / 16.0 + 0.3125 - 0.5);
        float scale = blockItem ? 0.24F : 0.18F;
        poseStack.scale(scale, scale, scale);
    }

    private void setupLayingRenderer(CraftingTableBlockEntity blockEntity, float partialTicks, PoseStack poseStack, ItemStack itemStack, int index) {
        BakedModel model = this.itemRenderer.getModel(itemStack, null, null, 0);
        boolean blockItem = model.isGui3d();
        poseStack.translate(0.5, 0.0, 0.5);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, blockEntity.currentAngle, blockEntity.nextAngle)));
        poseStack.translate((double) (index % 3) * 3.0 / 16.0 + 0.3125 - 0.5, blockItem ? 1.0625 : 1.005, (double) (index / 3) * 3.0 / 16.0 + 0.3125 - 0.5);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
        float scale = blockItem ? 0.25F : 0.175F;
        poseStack.scale(scale, scale, scale);
    }

    private void renderResultItem(ItemStack stack, @Nullable Level worldIn, float time, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.15F, 0.5F);
        BakedModel model = this.itemRenderer.getModel(stack, worldIn, null, 0);
        float hoverOffset = Mth.sin(time / 10.0F) * 0.04F + 0.1F;
        float modelYScale =  model.getTransforms().getTransform(TransformType.FIXED).scale.y();
        poseStack.translate(0.0, hoverOffset + 0.25F * modelYScale, 0.0);
        poseStack.mulPose(Vector3f.YP.rotation(time / 20.0F));
        if (!model.isGui3d()) {
            poseStack.scale(0.75F, 0.75F, 0.75F);
        }
        this.itemRenderer.render(stack, TransformType.FIXED, false, poseStack, bufferIn, combinedLightIn, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
    }
}
