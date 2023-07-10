package net.cheto97.rpgcraftmod.screen.widgets;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.screen.ToolLevelingTableScreen;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.ButtonHelper;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButtonListWidget extends ObjectSelectionList<ButtonEntry> {

	private final ToolLevelingTableScreen screen;
	private final int listWidth;

	public ButtonListWidget(ToolLevelingTableScreen screen, int listWidth, int top, int bottom) {
		super(screen.getMinecraft(), listWidth, screen.height, top, bottom, 24);
		this.screen = screen;
		this.listWidth = listWidth;
		// disable rendering of the dirt background
		this.setRenderBackground(false);
		this.setRenderTopAndBottom(false);
		this.setRenderHeader(false, 0);
	}

	public void refreshList() {
		this.clearEntries();
		ItemStack stack = this.screen.getMenu().getSlot(0).getItem();
		if (!stack.getItem().equals(Items.AIR)) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
			for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
				ButtonEntry buttonEntry = ButtonHelper.getButtonEntry(this.screen, entry.getKey(), entry.getValue());
				this.addEntry(buttonEntry);
			}
		}
	}

	@Override
	protected int getScrollbarPosition() {
		return this.x1 - 10;
	}

	@Override
	public int getRowWidth() {
		return this.listWidth;
	}

	@Override
	protected void renderBackground(PoseStack matrixStack) {
		// background of the scroll view
		this.fillGradient(matrixStack, x0 - 1, y0 - 1, x1, y1 + 2, -10066330, -10066330);
	}
}