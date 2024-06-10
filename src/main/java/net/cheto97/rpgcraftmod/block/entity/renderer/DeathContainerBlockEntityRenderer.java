package net.cheto97.rpgcraftmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.cheto97.rpgcraftmod.block.custom.stations.WizardTableBlock;
import net.cheto97.rpgcraftmod.block.entity.DeathContainerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class DeathContainerBlockEntityRenderer implements BlockEntityRenderer<DeathContainerBlockEntity> {
    public DeathContainerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }
    @Override
    public void render(DeathContainerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay){
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 1.0f, 0.5f);
        pPoseStack.scale(3F, 3F, 3F);
        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90));

        switch (pBlockEntity.getBlockState().getValue(WizardTableBlock.FACING)) {
            case NORTH -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
            case EAST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(90));
            case SOUTH -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
            case WEST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(270));
        }

        pPoseStack.popPose();
    }
}
