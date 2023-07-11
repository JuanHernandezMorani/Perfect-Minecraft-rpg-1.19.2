package net.cheto97.rpgcraftmod.item.wings.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class WingEntityModel<T extends LivingEntity> extends EntityModel<T> {

    public final ModelPart rightWing;
    public final ModelPart leftWing;

    public State state = State.IDLE;

    public WingEntityModel(ModelPart root) {
        this.rightWing = root.getChild("rightWing");
        this.leftWing = root.getChild("leftWing");
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData =  modelData.getRoot();

        modelPartData.addOrReplaceChild("rightWing", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));
        modelPartData.addOrReplaceChild("leftWing", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));

        return modelData;
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        state = State.IDLE;
        float a = 0.125F;
        float b = 0.1F;
        float k = 0.4F;
        float l = -0.5F;
        float m = -1.0F;
        float n = 0.0F;

        if(entity.isFallFlying()) {
            state = State.FLYING;
            float o = 1.0F;
            Vec3 Vec3 = entity.getDeltaMovement();

            if(Vec3.y < 0.0D) {
                Vec3 Vec32 = Vec3.normalize();
                o = 1.0F - (float) Math.pow(-Vec32.y, 1.5D);
            }

            k = o * 0.35F + (1.0F - o) * k;
            l = o * -1.6F + (0.3F - o) * l;

            if(entity.flyingSpeed > 0) {
                a = 0.4F;
                b = 1.0F;
            }
        }
        else if(entity.isCrouching()) {
            state = State.CROUCHING;
            k = 0.7F;
            m = 0.0F;
            n = 0.09F;
        }

        k += Mth.sin(entity.tickCount * a) * b;
        this.leftWing.xScale = 7.0F;
        this.leftWing.yScale = m;

        if(entity instanceof AbstractClientPlayer) {
            AbstractClientPlayer player = (AbstractClientPlayer) entity;
            player.elytraRotY = (player.elytraRotY + (k - player.elytraRotY) * 0.1F);
            player.elytraRotX = (player.elytraRotX + (n - player.elytraRotX) * 0.1F);
            player.elytraRotZ = (player.elytraRotZ + (l - player.elytraRotZ) * 0.1F);
            this.leftWing.yRot = player.elytraRotY;
            this.leftWing.xRot = player.elytraRotX;
            this.leftWing.zRot = player.elytraRotZ;
        }
        else {
            this.leftWing.yRot = k;
            this.leftWing.zRot = l;
            this.leftWing.xRot = n;
        }

        this.rightWing.xScale = -this.leftWing.xScale;
        this.rightWing.xRot = -this.leftWing.xRot;
        this.rightWing.yScale = this.leftWing.yScale;
        this.rightWing.yRot = this.leftWing.yRot;
        this.rightWing.zRot = -this.leftWing.zRot;
    }

    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.rightWing, this.leftWing);
    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight ,int packedOverlay, float red, float green, float blue, float alpha) {

    }

    public enum State {
        IDLE, CROUCHING, FLYING
    }
}