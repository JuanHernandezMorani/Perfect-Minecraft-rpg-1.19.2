package net.cheto97.rpgcraftmod.util.ToolLevelingUp;

import net.cheto97.rpgcraftmod.screen.ToolLevelingTableScreen;
import net.cheto97.rpgcraftmod.screen.widgets.ButtonEntry;
import net.cheto97.rpgcraftmod.util.levelConfig.ToolLevelingConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public final class ButtonHelper {

    public static boolean shouldButtonBeActive(ButtonEntry entry) {
        ButtonStatus status = entry.getStatus();
        if (status == ButtonStatus.NORMAL) {
            return true;
        } else if (status == ButtonStatus.USELESS) {
            return ToolLevelingConfig.allowLevelingUselessEnchantments.getValue();
        } else if (status == ButtonStatus.BREAK) {
            return ToolLevelingConfig.allowLevelingBreakingEnchantments.getValue();
        } else if (status == ButtonStatus.NOT_WHITELISTED ||status == ButtonStatus.BLACKLISTED || status == ButtonStatus.CAPPED
                || status == ButtonStatus.MAX_LEVEL || status == ButtonStatus.MIN_LEVEL) {
            return false;
        }
        return false;
    }

    public static ButtonEntry getButtonEntry(ToolLevelingTableScreen parent, Enchantment enchantment, int level) {
        List<Enchantment> whitelist = ToolLevelingConfig.whitelist.getValue();
        List<Enchantment> blacklist = ToolLevelingConfig.blacklist.getValue();
        ButtonEntry buttonEntry = new ButtonEntry(parent, enchantment, level);

        if (!whitelist.isEmpty() && !whitelist.contains(enchantment)) {
            buttonEntry.setStatus(ButtonStatus.NOT_WHITELISTED);
            return buttonEntry;
        }
        if (whitelist.isEmpty() && blacklist.contains(enchantment)) {
            buttonEntry.setStatus(ButtonStatus.BLACKLISTED);
            return buttonEntry;
        }
        if (level >= 32767) {
            buttonEntry.setStatus(ButtonStatus.MAX_LEVEL);
            return buttonEntry;
        }
        if (Utils.isEnchantmentAtCap(enchantment, level)) {
            buttonEntry.setStatus(ButtonStatus.CAPPED);
            return buttonEntry;
        }
        if (!Utils.isEnchantmentOverMinimum(enchantment, level)) {
            buttonEntry.setStatus(ButtonStatus.MIN_LEVEL);
            return buttonEntry;
        }
        if (enchantment.getMaxLevel() == 1) {
            buttonEntry.setStatus(ButtonStatus.USELESS);
            return buttonEntry;
        }
        if (Utils.willEnchantmentBreak(enchantment, level)) {
            buttonEntry.setStatus(ButtonStatus.BREAK);
            return buttonEntry;
        }
        return buttonEntry;
    }

    public static Component getButtonText(ButtonEntry entry) {
        return Component.translatable(entry.name).withStyle(getButtonTextFormatting(entry));
    }

    public static List<Component> getButtonToolTips(ButtonEntry data) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(Component.translatable(data.name).withStyle(ChatFormatting.AQUA));
        final String start = "container.rpgcraftmod.tool_leveling_table";
        if (ButtonHelper.shouldButtonBeActive(data) || Utils.freeCreativeUpgrades(Minecraft.getInstance().player)) {
            tooltip.add(Component.translatable(start + ".current_level", data.currentLevel).withStyle(ChatFormatting.DARK_GRAY));
            if(data.currentLevel < Short.MAX_VALUE){
                tooltip.add(Component.translatable(start + ".next_level", (data.currentLevel + 1)).withStyle(ChatFormatting.DARK_GRAY));
                tooltip.add(Component.translatable(start + ".cost", data.upgradeCost).withStyle(ChatFormatting.GOLD));
            }else{
                tooltip.add(Component.literal("Max level reached").withStyle(ChatFormatting.DARK_GREEN));
                tooltip.add(Component.literal("-").withStyle(ChatFormatting.DARK_GRAY));
            }
        }
        if (Utils.freeCreativeUpgrades(Minecraft.getInstance().player)) {
            tooltip.add(Component.translatable(start + ".free_creative").withStyle(ChatFormatting.GREEN));
        } else if (data.getStatus() != ButtonStatus.NORMAL) {
            tooltip.add(Component.translatable(start + ".error." + data.getStatus().toString().toLowerCase()).withStyle(ButtonHelper.getButtonTextFormatting(data)));
        }
        return tooltip;
    }

    public static ChatFormatting getButtonTextFormatting(ButtonEntry entry) {
        ChatFormatting format = ChatFormatting.RESET;
        if (Utils.freeCreativeUpgrades(Minecraft.getInstance().player)) {
            return ChatFormatting.RESET;
        }
        if (entry.getStatus() != ButtonStatus.NORMAL) {
            format = ChatFormatting.DARK_RED;
        }
        if (entry.getStatus() == ButtonStatus.USELESS) {
            format = ChatFormatting.YELLOW;
        }
        return format;
    }

    public enum ButtonStatus {
        NORMAL,
        NOT_WHITELISTED,
        BLACKLISTED,
        USELESS,
        BREAK,
        MAX_LEVEL,
        CAPPED,
        MIN_LEVEL
    }
}
