package net.cheto97.rpgcraftmod;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.parts.compassWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;


public class RenderOverlay {

    private RenderOverlay() {}

    public static void renderOverlay(RpgcraftMod rpgHud, Minecraft mc, PoseStack ms, float partialTicks) {
        drawElement(mc, HudType.WIDGET, ms, partialTicks);
        //drawElement(mc, HudType.COMPASS, ms, partialTicks);
        drawElement(mc, HudType.LIFE, ms, partialTicks);
        drawElement(mc, HudType.MANA, ms, partialTicks);
        drawElement(mc, HudType.ARMOR, ms, partialTicks);
        drawElement(mc, HudType.FOOD, ms, partialTicks);
        drawElement(mc, HudType.MOUNT_LIFE, ms, partialTicks);
        drawElement(mc, HudType.AIR, ms, partialTicks);
        drawElement(mc, HudType.JUMP_BAR, ms, partialTicks);
        drawElement(mc, HudType.STATUS_EFFECTS, ms, partialTicks);
        drawElement(mc, HudType.RPGEXPERIENCE, ms, partialTicks);
        drawElement(mc, HudType.RPGLEVEL, ms, partialTicks);
        drawElement(mc, HudType.HOTBAR, ms, partialTicks);
        drawElement(mc, HudType.VIEW, ms, partialTicks);

        drawParts(ms);

    }
    private static void drawElement(Minecraft mc, HudType type, PoseStack ms, float partialTicks) {
                //bind(GUI_ICONS_LOCATION);
                ms.pushPose();
                RenderSystem.enableBlend();
                RpgcraftMod.getActiveHud().drawElement(type, mc.gui, ms, partialTicks, partialTicks,
                        mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
                ms.popPose();
    }
    private static void bind(ResourceLocation res) {
        Minecraft.getInstance().getTextureManager().bindForSetup(res);
    }
    private static void drawParts(PoseStack ms){
        ms.pushPose();
        RenderSystem.enableBlend();
        new compassWidget(ms);
        ms.popPose();
    }
}
