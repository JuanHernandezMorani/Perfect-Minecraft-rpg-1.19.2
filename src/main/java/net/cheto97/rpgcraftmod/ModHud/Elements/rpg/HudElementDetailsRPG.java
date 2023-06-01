package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.vertex.PoseStack;

import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.cheto97.rpgcraftmod.ModHud.Elements.vanilla.HudElementDetailsVanilla;

public class HudElementDetailsRPG extends HudElementDetailsVanilla {

    public HudElementDetailsRPG() {
        super();
        this.typeOffset = 10;
    }
}