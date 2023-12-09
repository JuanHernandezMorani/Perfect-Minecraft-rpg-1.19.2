package net.cheto97.rpgcraftmod.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(Screen.class)
public interface ScreenAccessor {

    @Invoker("renderTooltipInternal")
    void invokeRenderTooltipFromComponents(PoseStack matrices, List<ClientTooltipComponent> components, int x, int y);
}
