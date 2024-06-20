package net.cheto97.rpgcraftmod.item.model;

import com.google.gson.JsonArray;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.model.geom.builders.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class WingsModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart modelItem;
    public WingsModel(ModelPart part){
        this.modelItem = part;
    }
    public static LayerDefinition createLayer() {
        ResourceLocation modelLocation = new ResourceLocation(RpgcraftMod.MOD_ID,"models/item/wings_1.json");
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();
        PartDefinition leftPart = part.getChild("left_wings");
        PartDefinition rightPart = part.getChild("right_wings");

        try (Reader reader = new InputStreamReader(Objects.requireNonNull(WingsModel.class.getResourceAsStream("/assets/" + modelLocation.getNamespace() + "/" + modelLocation.getPath())))) {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            JsonArray elements = json.getAsJsonArray("elements");

            if (elements != null) {
                int index = 0;
                for (JsonElement element : elements) {
                    JsonObject obj = element.getAsJsonObject();

                    float[] from = parseFloatArray(obj.getAsJsonArray("from"));
                    float[] to = parseFloatArray(obj.getAsJsonArray("to"));
                    JsonObject rotationObj = obj.getAsJsonObject("rotation");

                    float angle = rotationObj.get("angle").getAsFloat();
                    String axis = rotationObj.get("axis").getAsString();
                    float[] origin = parseFloatArray(rotationObj.getAsJsonArray("origin"));

                    CubeDeformation deformation = CubeDeformation.NONE;
                    CubeListBuilder cube = CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(from[0], from[1], from[2], to[0] - from[0], to[1] - from[1], to[2] - from[2], deformation);

                    PartPose pose = PartPose.offsetAndRotation(origin[0], origin[1], origin[2],
                            axis.equals("x") ? (float)Math.toRadians(angle) : 0,
                            axis.equals("y") ? (float)Math.toRadians(angle) : 0,
                            axis.equals("z") ? (float)Math.toRadians(angle) : 0);

                    part.addOrReplaceChild("part" + index++, cube, pose);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return LayerDefinition.create(mesh, 64, 64);
    }

    private static float[] parseFloatArray(JsonArray jsonArray) {
        float[] array = new float[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            array[i] = jsonArray.get(i).getAsFloat();
        }
        return array;
    }

    @Override
    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        modelItem.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
