package net.cheto97.rpgcraftmod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_RGPCRAFT = "key.category.rpgcraftmod.rpgcraft";
    public static final String KEY_DRINK_MANA = "key.rpgcraftmod.drink_mana";
    public static final String KEY_SHOW_STATS = "key.rpgcraftmod.show_stats";
    public static final String KEY_SKILL_1 = "key.rpgcraftmod.skill_1";
    public static final String KEY_SKILL_2 = "key.rpgcraftmod.skill_2";
    public static final String KEY_SKILL_3 = "key.rpgcraftmod.skill_3";

    public static final  KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_MANA, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_RGPCRAFT);
    public static final  KeyMapping STATS_KEY = new KeyMapping(KEY_SHOW_STATS, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY_RGPCRAFT);
    public static final  KeyMapping SKILL_1_STATS_KEY = new KeyMapping(KEY_SKILL_1, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_RGPCRAFT);
    public static final  KeyMapping SKILL_2_STATS_KEY = new KeyMapping(KEY_SKILL_2, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_T, KEY_CATEGORY_RGPCRAFT);
    public static final  KeyMapping SKILL_3_STATS_KEY = new KeyMapping(KEY_SKILL_3, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, KEY_CATEGORY_RGPCRAFT);
}
