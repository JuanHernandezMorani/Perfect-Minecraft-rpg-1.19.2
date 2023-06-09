package net.cheto97.rpgcraftmod;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;


public class RenderOverlay {

    private RenderOverlay() {}

    public static void renderOverlay(RpgcraftMod rpgHud, Minecraft mc, PoseStack ms, float partialTicks) {
        drawElement(rpgHud, mc, HudType.WIDGET, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.COMPASS, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.LIFE, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.MANA, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.ARMOR, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.FOOD, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.MOUNT_LIFE, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.AIR, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.JUMP_BAR, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.STATUS_EFFECTS, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.RPGEXPERIENCE, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.RPGLEVEL, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.HOTBAR, ms, partialTicks);
        drawElement(rpgHud, mc, HudType.VIEW, ms, partialTicks);
    }
    private static void drawElement(RpgcraftMod rpgHud, Minecraft mc, HudType type, PoseStack ms, float partialTicks) {
                bind(GUI_ICONS_LOCATION);
                ms.pushPose();
                RenderSystem.enableBlend();
                rpgHud.getActiveHud().drawElement(type, mc.gui, ms, partialTicks, partialTicks,
                        mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
                ms.popPose();
    }
    private static void bind(ResourceLocation res) {
        Minecraft.getInstance().getTextureManager().bindForSetup(res);
    }

}
