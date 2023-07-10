package net.cheto97.rpgcraftmod.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.screen.widgets.ItemValuesListWidget;
import net.cheto97.rpgcraftmod.util.levelConfig.ItemValueConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemValueScreen extends Screen {

    private static final int SPACING = 30;
    private static final Component TITLE = Component.translatable("block.rpgcraftmod.item_values");
    private ItemValuesListWidget itemValues;

    public ItemValueScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        super.init();
        this.itemValues = new ItemValuesListWidget(this, width - (2 * SPACING), SPACING, height - SPACING);
        this.itemValues.setLeftPos(SPACING);
        this.addWidget(itemValues);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack); // render translucent grey background
        this.itemValues.render(matrixStack, mouseX, mouseY, partialTicks); // render item list widget
        super.render(matrixStack, mouseX, mouseY, partialTicks); // render buttons
        drawCenteredString(matrixStack, font, Component.translatable("screen.rpgcraftmod.default_item_value_worth",
                ItemValueConfig.defaultItemWorth.getValue()), width / 2, 10, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int buttonID) {
        this.itemValues.mouseClicked(mouseX, mouseY, buttonID);
        return super.mouseClicked(mouseX, mouseY, buttonID);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        this.itemValues.mouseScrolled(mouseX, mouseY, delta);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

}
