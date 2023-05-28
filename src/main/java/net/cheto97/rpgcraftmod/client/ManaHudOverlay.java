package net.cheto97.rpgcraftmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {
    private static final ResourceLocation FILLED_MANA = new ResourceLocation(RpgcraftMod.MOD_ID,
            "textures/mana/filled_mana_vanilla.png");

    private static final ResourceLocation EMPTY_MANA = new ResourceLocation(RpgcraftMod.MOD_ID,
            "textures/mana/empty_mana_vanilla.png");

    public static final IGuiOverlay HUD_MANA = ((gui,poseStack,partialTick,width,height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0,EMPTY_MANA);
        for(int i = 0;i < 10;i++){
            GuiComponent.blit(poseStack,x-94+(i*9),y-54,0,0,12,
                    12,12,12);
        }


        RenderSystem.setShaderTexture(0,FILLED_MANA);
        for(int i = 0; i<10;i++){
            if(ClientManaData.getPlayerMana() > i){
                GuiComponent.blit(poseStack,x-94+(i*9),y-54,0,0,12,
                       12,12,12);
            }else{
               break;
            }
        }
    });
}
