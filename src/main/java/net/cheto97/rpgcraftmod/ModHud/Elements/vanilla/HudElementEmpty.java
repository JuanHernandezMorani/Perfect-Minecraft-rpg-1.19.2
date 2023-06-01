package net.cheto97.rpgcraftmod.ModHud.Elements.vanilla;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.minecraft.client.gui.Gui;

public class HudElementEmpty extends HudElement {

    public HudElementEmpty() {
        super(HudType.VOID, 0, 0, 0, 0, false);
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
    }

}