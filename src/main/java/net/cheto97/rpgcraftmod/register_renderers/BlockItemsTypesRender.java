package net.cheto97.rpgcraftmod.register_renderers;

import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class BlockItemsTypesRender {
    public static void register(){
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MANA.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MANA.get(),RenderType.translucent());
    }
}
