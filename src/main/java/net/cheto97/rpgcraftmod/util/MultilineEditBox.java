package net.cheto97.rpgcraftmod.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultilineEditBox extends AbstractWidget implements Widget, GuiEventListener {
    private final Font font;
    private final List<Component> components;
    private final int maxLines;
    private final int lineHeight;
    private int frame;
    private int displayStart;

    public MultilineEditBox(Font font, int x, int y, int width, int height, List<Component> components, int maxLines, int lineHeight) {
        super(x, y, width, height, Component.literal(""));
        this.font = font;
        this.components = components;
        this.maxLines = maxLines;
        this.lineHeight = lineHeight;
    }

    @Override
    public void renderButton(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);

        int startY = this.y + this.lineHeight;
        int endY = Math.min(startY + this.maxLines * this.lineHeight, this.y + this.height);

        for (int i = 0; i < maxLines; i++) {
            int lineIndex = displayStart + i;
            if (lineIndex < components.size()) {
                int lineY = this.y + i * this.lineHeight;
                renderLine(matrixStack, components.get(lineIndex), lineY);
            }
        }

        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    private void renderLine(PoseStack matrixStack, Component component, int lineY) {
        if (component != null) {
            int color = this.active ? 0xFFFFFF : 0xA0A0A0;
            int textX = this.x + 4;
            int textY = lineY + (this.lineHeight - 8) / 2;
            this.font.draw(matrixStack, component, (float) textX, (float) textY, color);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            int clickedLine = (int) ((mouseY - y) / lineHeight);
            int clickedIndex = displayStart + clickedLine;

            if (clickedIndex >= 0 && clickedIndex < components.size()) {
                Component clickedComponent = components.get(clickedIndex);
                Minecraft.getInstance().keyboardHandler.setClipboard(clickedComponent.getString());
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (isMouseOver(mouseX, mouseY)) {
            displayStart = Mth.clamp(displayStart - (int) delta, 0, Math.max(0, components.size() - maxLines));
            return true;
        }
        return false;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput elementOutput) {
        elementOutput.add(NarratedElementType.TITLE, Component.translatable("narration.edit_box", this.components));
    }
}
