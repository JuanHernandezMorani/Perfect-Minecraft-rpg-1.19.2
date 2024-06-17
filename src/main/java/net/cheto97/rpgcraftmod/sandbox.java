package net.cheto97.rpgcraftmod;

public class sandbox {
    /*
    public class ElytraModel<T extends LivingEntity> extends AgeableListModel<T> {
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public ElytraModel(ModelPart p_170538_) {
        this.leftWing = p_170538_.getChild("left_wing");
        this.rightWing = p_170538_.getChild("right_wing");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        CubeDeformation $$2 = new CubeDeformation(1.0F);
        $$1.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(22, 0).addBox(-10.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, $$2), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.2617994F, 0.0F, -0.2617994F));
        $$1.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(22, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, $$2), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.2617994F, 0.0F, 0.2617994F));
        return LayerDefinition.create($$0, 64, 32);
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.leftWing, this.rightWing);
    }

    public void setupAnim(T p_102544_, float p_102545_, float p_102546_, float p_102547_, float p_102548_, float p_102549_) {
        float $$6 = 0.2617994F;
        float $$7 = -0.2617994F;
        float $$8 = 0.0F;
        float $$9 = 0.0F;
        if (p_102544_.isFallFlying()) {
            float $$10 = 1.0F;
            Vec3 $$11 = p_102544_.getDeltaMovement();
            if ($$11.y < 0.0) {
                Vec3 $$12 = $$11.normalize();
                $$10 = 1.0F - (float)Math.pow(-$$12.y, 1.5);
            }

            $$6 = $$10 * 0.34906584F + (1.0F - $$10) * $$6;
            $$7 = $$10 * -1.5707964F + (1.0F - $$10) * $$7;
        } else if (p_102544_.isCrouching()) {
            $$6 = 0.6981317F;
            $$7 = -0.7853982F;
            $$8 = 3.0F;
            $$9 = 0.08726646F;
        }

        this.leftWing.y = $$8;
        if (p_102544_ instanceof AbstractClientPlayer $$13) {
            $$13.elytraRotX += ($$6 - $$13.elytraRotX) * 0.1F;
            $$13.elytraRotY += ($$9 - $$13.elytraRotY) * 0.1F;
            $$13.elytraRotZ += ($$7 - $$13.elytraRotZ) * 0.1F;
            this.leftWing.xRot = $$13.elytraRotX;
            this.leftWing.yRot = $$13.elytraRotY;
            this.leftWing.zRot = $$13.elytraRotZ;
        } else {
            this.leftWing.xRot = $$6;
            this.leftWing.zRot = $$7;
            this.leftWing.yRot = $$9;
        }

        this.rightWing.yRot = -this.leftWing.yRot;
        this.rightWing.y = this.leftWing.y;
        this.rightWing.xRot = this.leftWing.xRot;
        this.rightWing.zRot = -this.leftWing.zRot;
    }
}
     */
}
