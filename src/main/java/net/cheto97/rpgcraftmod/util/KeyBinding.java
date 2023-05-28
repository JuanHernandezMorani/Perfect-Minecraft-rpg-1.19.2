package net.cheto97.rpgcraftmod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_RGPCRAFT = "key.category.rpgcraftmod.rpgcraft";
    public static final String KEY_DRINK_MANA = "key.rpgcraftmod.drink_mana";
    public static final String KEY_SHOW_STATS = "key.rpgcraftmod.show_stats";

    public static final  KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_MANA, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_RGPCRAFT);

    public static final  KeyMapping STATS_KEY = new KeyMapping(KEY_SHOW_STATS, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY_RGPCRAFT);
}
