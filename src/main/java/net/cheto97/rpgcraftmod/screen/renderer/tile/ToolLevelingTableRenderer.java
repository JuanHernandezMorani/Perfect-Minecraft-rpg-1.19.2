package net.cheto97.rpgcraftmod.screen.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.cheto97.rpgcraftmod.block.custom.stations.ToolLevelingTableBlock;
import net.cheto97.rpgcraftmod.block.entity.ToolLevelingTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ToolLevelingTableRenderer implements BlockEntityRenderer<ToolLevelingTableBlockEntity> {

    public ToolLevelingTableRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {}

    @Override
    public void render(ToolLevelingTableBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn,
                       MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        ItemStack stack = tileEntityIn.getStackToEnchant();
        if (!stack.isEmpty()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5D, 0.83D, 0.5D);
            matrixStackIn.scale(0.6F, 0.6F, 0.6F);
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90));

            switch (tileEntityIn.getBlockState().getValue(ToolLevelingTableBlock.FACING)) {
                case NORTH -> matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(0));
                case EAST -> matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90));
                case SOUTH -> matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180));
                case WEST -> matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(270));
            }
            renderItem(stack, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            matrixStackIn.popPose();

        }
    }

    private void renderItem(ItemStack stack, float partialTicks, PoseStack pstack, MultiBufferSource bufferIn,
                            int combinedLightIn, int combinedOverlayIn) {
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, combinedLightIn,
                combinedOverlayIn, pstack, bufferIn, 0);
    }

}
