package net.cheto97.rpgcraftmod.register_renderers;

import net.cheto97.rpgcraftmod.entity.ModEntityTypes;
import net.cheto97.rpgcraftmod.entity.client.Renderers.*;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class MobsRenderer {
    public static void register(){
        EntityRenderers.register(ModEntityTypes.MUTANT_GOLEM.get(), MutantGolemRenderer::new);
        EntityRenderers.register(ModEntityTypes.DRAKE.get(), DrakeV1Renderer::new);
        EntityRenderers.register(ModEntityTypes.DRAKE_2.get(), DrakeV2Renderer::new);
        EntityRenderers.register(ModEntityTypes.DRAKE_3.get(), DrakeV3Renderer::new);
        EntityRenderers.register(ModEntityTypes.DRAKE_4.get(), DrakeV4Renderer::new);
        EntityRenderers.register(ModEntityTypes.DRAKE_5.get(), DrakeV5Renderer::new);
        EntityRenderers.register(ModEntityTypes.DRAKE_6.get(), DrakeV6Renderer::new);
        EntityRenderers.register(ModEntityTypes.DRAKE_7.get(), DrakeV7Renderer::new);
        EntityRenderers.register(ModEntityTypes.KOBOLD_WARRIOR.get(), KoboldWarriorRenderer::new);
        EntityRenderers.register(ModEntityTypes.KOBOLD_WARRIOR.get(), KoboldWarriorRendererB::new);
        EntityRenderers.register(ModEntityTypes.KOBOLD_WARRIOR.get(), KoboldWarriorRendererC::new);
    }
}
