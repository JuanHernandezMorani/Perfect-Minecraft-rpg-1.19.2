package net.cheto97.rpgcraftmod.item.model;

import com.google.common.collect.ImmutableList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import net.minecraft.util.Mth;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

public class WingEntityModel<T extends LivingEntity> extends AgeableListModel<T> {
    public final ModelPart rightWing;
    public final ModelPart leftWing;
    public State state = State.IDLE;
    public WingEntityModel(ModelPart root) {
        super();
        this.rightWing = root.getChild("right_wing");
        this.leftWing = root.getChild("left_wing");
    }

    public static LayerDefinition getModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        CubeDeformation deformation = new CubeDeformation(1.0F);


        CubeListBuilder rightWingBuilder = CubeListBuilder.create().texOffs(22, 0)
                .addBox(-16.0F, -11.0F, 15.5F, 24.0F, 37.0F, 2.0F, deformation);

        CubeListBuilder leftWingBuilder = CubeListBuilder.create().texOffs(22, 0)
                .mirror()
                .addBox(-8.0F, -11.0F, 15.5F, 24.0F, 37.0F, 2.0F, deformation);


        part.addOrReplaceChild("right_wing", rightWingBuilder, PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.2617994F, 0.0F, 0.2617994F));
        part.addOrReplaceChild("left_wing", leftWingBuilder, PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.2617994F, 0.0F, -0.2617994F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
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
            n = 0.09F;
        }

        k += Mth.sin(entity.tickCount * a) * b;
        this.leftWing.xScale = 1.0F;
        this.leftWing.yScale = m;

        if(entity instanceof AbstractClientPlayer player) {
            player.elytraRotY = (player.elytraRotY + (k - player.elytraRotY) * 0.1F);
            player.elytraRotX = (player.elytraRotX + (n - player.elytraRotX) * 0.1F);
            player.elytraRotZ = (player.elytraRotZ + (l - player.elytraRotZ) * 0.1F)-0.225f;
            this.leftWing.yRot = player.elytraRotY;
            this.leftWing.xRot = player.elytraRotX;
            this.leftWing.zRot = player.elytraRotZ;
        }
        else {
            this.leftWing.yRot = k;
            this.leftWing.zRot = l;
            this.leftWing.xRot = n;
        }

        this.rightWing.xScale = this.leftWing.xScale;
        this.rightWing.xRot = this.leftWing.xRot;
        this.rightWing.yScale = this.leftWing.yScale;
        this.rightWing.yRot = -this.leftWing.yRot;
        this.rightWing.zRot = -this.leftWing.zRot;
    }

    @Override
    protected @NotNull Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected @NotNull Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.rightWing, this.leftWing);
    }


    public enum State {
        IDLE, CROUCHING, FLYING
    }
}