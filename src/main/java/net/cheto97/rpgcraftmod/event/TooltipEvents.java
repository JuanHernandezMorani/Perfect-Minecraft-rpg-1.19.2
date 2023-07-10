package net.cheto97.rpgcraftmod.event;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RpgcraftMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class TooltipEvents {

    @SubscribeEvent
    public static void onTooltip(final ItemTooltipEvent event) {
        if (event.getFlags().isAdvanced() && Screen.hasShiftDown()) {
            final Item item = event.getItemStack().getItem();
            final long worth = Utils.getItemWorth(item);
            event.getToolTip().add(Component.literal(""));
            event.getToolTip().add(Component.translatable("tooltip.rpgcraftmod.itemworth", worth).withStyle(ChatFormatting.AQUA));
        }
    }
}
