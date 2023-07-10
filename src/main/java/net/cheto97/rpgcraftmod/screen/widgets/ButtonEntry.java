package net.cheto97.rpgcraftmod.screen.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.ToolLeveling.PacketHandler;
import net.cheto97.rpgcraftmod.networking.ToolLeveling.packets.SetEnchantmentToolLevelingTable;
import net.cheto97.rpgcraftmod.screen.ToolLevelingTableScreen;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.ButtonHelper;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButtonEntry extends ObjectSelectionList.Entry<ButtonEntry> {
    public Button button;
    public Enchantment enchantment;
    public String name;
    public int currentLevel;
    public long upgradeCost;
    private ButtonHelper.ButtonStatus status = ButtonHelper.ButtonStatus.NORMAL;
    private final ToolLevelingTableScreen screen;
    private int x;
    private int y;
    private static Component NARRATION = null;
    public ButtonEntry(ToolLevelingTableScreen screen, Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.currentLevel = level;
        this.name = enchantment.getDescriptionId();
        this.screen = screen;
        this.upgradeCost = Utils.getEnchantmentUpgradeCost(enchantment, level + 1);

        this.button = new Button(this.x,this.y+6,121,20,ButtonHelper.getButtonText(this), (b) -> {
            ModMessages.sendToServer(new SetEnchantmentToolLevelingTable(this.screen.getMenu().getPos(), this.enchantment, this.currentLevel + 1));
        });
    }
    @Override
    public void render(PoseStack mStack, int index, int top, int left, int entryWidth, int entryHeight, int mouseX,
                       int mouseY, boolean isMouseOver, float partialTicks) {
        this.x = left;
        this.y = top;
        this.button.setWidth(entryWidth-15);
        this.button.setHeight(entryHeight);
        this.button.x = left - 3;
        this.button.y = top;
        long worth = this.screen.getMenu().getContainerWorth() + this.screen.getMenu().getBonusPoints();
        boolean normallyActive = (this.upgradeCost <= worth) && ButtonHelper.shouldButtonBeActive(this);
        this.button.active = normallyActive || Utils.freeCreativeUpgrades(Minecraft.getInstance().player);
        this.button.render(mStack, mouseX, mouseY, partialTicks);
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.button.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }
    public void updateButtonText() {
        this.button.setMessage(ButtonHelper.getButtonText(this));
    }
    @Override
    public Component getNarration() {
        if (NARRATION == null) {
            NARRATION = Component.translatable("screen." + RpgcraftMod.MOD_ID + ".tool_leveling_table");
        }
        return NARRATION;
    }
    public void setStatus(ButtonHelper.ButtonStatus status) {
        this.status = status;
        this.updateButtonText();
    }
    public ButtonHelper.ButtonStatus getStatus() {
        return this.status;
    }
}