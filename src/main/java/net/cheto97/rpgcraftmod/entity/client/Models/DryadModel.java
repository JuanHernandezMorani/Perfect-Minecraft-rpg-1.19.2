package net.cheto97.rpgcraftmod.entity.client.Models;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.entity.custom.Dryad;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

public class DryadModel extends HumanoidModel<Dryad> {
    public static final ModelLayerLocation LAYER_LOCATION_D = new ModelLayerLocation(new ResourceLocation(RpgcraftMod.MOD_ID, "dryad"), "main");

    public DryadModel(ModelPart pRoot) {
        super(pRoot);
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0), 64, 32);
    }
}
