package net.cheto97.rpgcraftmod.custom.curios.auras.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.custom.curios.auras.item.Commonaura;
import net.cheto97.rpgcraftmod.custom.curios.auras.model.AuraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import javax.annotation.Nullable;

public class AuraRenderer implements ICurioRenderer {
    private final AuraModel<LivingEntity> auraModel;

    public AuraRenderer(ModelPart auraModel){
        this.auraModel = new AuraModel<>(auraModel);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                                                                          PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer, int light, float limbSwing,
                                                                          float limbSwingAmount, float partialTicks, float ageInTicks,
                                                                          float netHeadYaw, float headPitch){
        if(stack.getItem() instanceof Commonaura aura){
            float[] primaryColour = aura.getPrimaryColour().getTextureDiffuseColors();
            float r1 = primaryColour[0];
            float g1 = primaryColour[1];
            float b1 = primaryColour[2];

            String name = aura.getName(aura.getDefaultInstance()).getString();
            String auraType;

            switch (name){
                case "Boss Aura" -> auraType = "boss_aura";
                case "Brutal Aura" -> auraType = "brutal_aura";
                case "Champion Aura" ->  auraType = "champion_aura";
                case "Demon Aura" -> auraType = "demon_aura";
                case "Elite Aura" -> auraType = "elite_aura";
                case "Hero Aura" -> auraType = "hero_aura";
                case "Legendary Aura" -> auraType = "legendary_aura";
                case "Mythical Aura" -> auraType = "mythical_aura";
                case "Semi Boss Aura" -> auraType = "semi_boss_aura";
                case "Unique Aura" -> auraType = "unique_aura";
                default -> auraType = "entity_aura";
            }

            ResourceLocation layer = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/auras/"+auraType+".png");

            float darkR = r1 * 0.85F;
            float darkG = g1 * 0.85F;
            float darkB = b1 * 0.85F;

            matrixStack.pushPose();
            matrixStack.translate(0.0d,1.5d,0.0d);
            this.auraModel.setupAnim(slotContext.entity(), limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
            this.renderAura(matrixStack, renderTypeBuffer, aura.getDefaultInstance(), RenderType.entityCutoutNoCull(layer), light, darkR, darkG, darkB);
            matrixStack.popPose();
        }
    }
    public void renderAura(PoseStack matrices, MultiBufferSource renderTypeBuffer, @Nullable ItemStack stack, RenderType renderType, int light,float r, float g, float b) {
        float alpha = 200 / 255F;
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, renderType, false, stack != null && stack.isEnchanted());
        this.auraModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, r, g, b, alpha);
    }
}