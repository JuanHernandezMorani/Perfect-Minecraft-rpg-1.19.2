package net.cheto97.rpgcraftmod;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
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
    }

    private static void drawElement(RpgcraftMod rpgHud, Minecraft mc, HudType type, PoseStack ms, float partialTicks) {
                bind(GUI_ICONS_LOCATION);
                ms.pushPose();
                RenderSystem.enableBlend();
                rpgHud.getActiveHud().drawElement(type, mc.gui, ms, partialTicks, partialTicks,
                        mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
                ms.popPose();
    }

    private static boolean preventElementRenderType(RpgcraftMod rpgHud, HudType type) {
        String id = Settings.prevent_element_render + "_" + type.name().toLowerCase();
        if (rpgHud.settings.doesSettingExist(id)) {
            return rpgHud.settings.getBoolValue(id);
        }
        return false;
    }

    public static boolean shouldRenderVanilla(RpgcraftMod rpgHud, HudType type) {
        return isVanillaElement(rpgHud, type) || forceRenderTypeVanilla(rpgHud, type);
    }

    public static boolean forceRenderTypeVanilla(RpgcraftMod rpgHud, HudType type) {
        return false;
    }

    public static boolean preventEventType(RpgcraftMod rpgHud, HudType type) {
        if (!shouldRenderVanilla(rpgHud, type)) return true;
        return false;
    }

    private static void bind(ResourceLocation res) {
        Minecraft.getInstance().getTextureManager().bindForSetup(res);
    }

    public static boolean isVanillaElement(RpgcraftMod rpgHud, HudType type) {
        return rpgHud.getActiveHud().isVanillaElement(type);
    }
}
