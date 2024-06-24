package net.cheto97.rpgcraftmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.menu.ToolLevelingTableMenu;
import net.cheto97.rpgcraftmod.screen.widgets.ButtonEntry;
import net.cheto97.rpgcraftmod.screen.widgets.ButtonListWidget;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.ButtonHelper;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.DraggableStackEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ToolLevelingTableScreen extends AbstractContainerScreen<ToolLevelingTableMenu> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(RpgcraftMod.MOD_ID, "textures/gui/screen/tool_leveling_table.png");
    protected ButtonListWidget buttonList;
    private byte ticksSinceUpdate = 0;
    private DraggableStackEntry draggedStackEntry;
    public ToolLevelingTableScreen(ToolLevelingTableMenu container, Inventory inv, Component name) {
        super(container, inv, name);
        this.imageWidth = 248;
        this.imageHeight = 220;
        this.inventoryLabelY += 52;
        this.titleLabelX -= 1;
    }
    @Override
    protected void init() {
        super.init();
        this.buttonList = new ButtonListWidget(this, 136, this.topPos + 23, this.topPos + 118);
        this.buttonList.setLeftPos(this.leftPos + 105);
        this.addRenderableWidget(this.buttonList);
    }
    @Override
    public void containerTick() {
        this.ticksSinceUpdate++;
        if (this.ticksSinceUpdate % 5 == 0) {
            this.ticksSinceUpdate = 0;
            this.buttonList.refreshList();
        }
    }
    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderBackground(matrixStack);

        if (this.draggedStackEntry != null) {
            this.itemRenderer.renderGuiItem(this.draggedStackEntry.getItemStack(), this.draggedStackEntry.getOffsetX(), this.draggedStackEntry.getOffsetY());
        }
        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);
            if (slot.hasItem()) {
                ItemStack itemStack = slot.getItem();
                int slotX = this.leftPos + slot.x + 1;
                int slotY = this.topPos + slot.y + 1;
                this.itemRenderer.renderGuiItem(itemStack, slotX, slotY);
            }
        }
        matrixStack.pushPose();
        matrixStack.translate(0, 0, 200);
        if (this.draggedStackEntry != null) {
            this.itemRenderer.renderGuiItem(this.draggedStackEntry.getItemStack(), this.draggedStackEntry.getOffsetX(), this.draggedStackEntry.getOffsetY());
        }
        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);
            if (slot.hasItem()) {
                ItemStack itemStack = slot.getItem();
                int slotX = this.leftPos + slot.x + 1;
                int slotY = this.topPos + slot.y + 1;
                this.itemRenderer.renderGuiItem(itemStack, slotX, slotY);
            }
        }
        this.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        this.buttonList.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
        this.renderPointsSummary(matrixStack);
        for (int i = 0; i < this.buttonList.children().size(); i++) {
            ButtonEntry entry = this.buttonList.children().get(i);
            if (entry.button.isHoveredOrFocused()) {
                List<Component> tooltip = ButtonHelper.getButtonToolTips(entry);
                this.renderComponentTooltip(matrixStack, tooltip, mouseX, mouseY);
            }
        }

        matrixStack.popPose();
    }
    private void renderPointsSummary(PoseStack stack) {
        String start = "container.rpgcraftmod.tool_leveling_table.worth.";
        Component bonusPoints = Component.translatable(start + "bonus_points", this.menu.getBonusPoints());
        Component invWorth = Component.translatable(start + "inv", this.menu.getContainerWorth());
        float left = this.leftPos + 8;
        this.font.draw(stack, bonusPoints, left, topPos + 45, 4210752);
        this.font.draw(stack, invWorth, left, topPos + 56, 4210752);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);
            if (this.isMouseOverSlot(slot, mouseX, mouseY) && slot.hasItem()) {
                ItemStack itemStack = slot.getItem();
                this.draggedStackEntry = new DraggableStackEntry(itemStack, (int)mouseX, (int)mouseY);
                break;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isMouseOverSlot(Slot slot, double mouseX, double mouseY) {
        return mouseX >= slot.x && mouseX < slot.x + 16 && mouseY >= slot.y && mouseY < slot.y + 16;
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        this.buttonList.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        if (this.draggedStackEntry != null) {
            this.draggedStackEntry.updatePosition((int)mouseX, (int)mouseY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.draggedStackEntry != null) {
            this.draggedStackEntry = null;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        this.buttonList.mouseScrolled(mouseX, mouseY, delta);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }
    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mX, int my) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, imageWidth, imageHeight);
    }
}