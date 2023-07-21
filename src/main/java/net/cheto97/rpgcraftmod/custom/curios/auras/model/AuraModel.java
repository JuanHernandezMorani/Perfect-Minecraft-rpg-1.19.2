package net.cheto97.rpgcraftmod.custom.curios.auras.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class AuraModel<T extends LivingEntity> extends EntityModel<T> {
    public final ModelPart aura;

    public AuraModel(ModelPart root){
        super();
        this.aura = root.getChild("aura");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild("aura", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-16, 0, -16, 32, 0, 32, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float rotationSpeed = 0.0075F;
        this.aura.yRot = entity.tickCount * rotationSpeed;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight ,int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        aura.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
