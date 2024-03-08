package net.cheto97.rpgcraftmod.item;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTier {
    public static Tier SAPPHIRE;

    static {
        SAPPHIRE = TierSortingRegistry.registerTier(
                new ForgeTier(5,4062,14.0F,6.0F,24,
                        ModTags.Blocks.NEEDS_SAPPHIRE_TOOL, () -> Ingredient.of(ModItems.zafiro.get())),
                new ResourceLocation(RpgcraftMod.MOD_ID, "sapphire"),
                List.of(Tiers.NETHERITE), List.of());
    }

}
