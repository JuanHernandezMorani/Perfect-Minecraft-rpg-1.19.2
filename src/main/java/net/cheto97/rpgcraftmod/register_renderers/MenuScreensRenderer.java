package net.cheto97.rpgcraftmod.register_renderers;

import net.cheto97.rpgcraftmod.menu.ModMenuTypes;
import net.cheto97.rpgcraftmod.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;


public class MenuScreensRenderer {
    public static void register(){
            MenuScreens.register(ModMenuTypes.WIZARD_TABLE_MENU.get(), WizardTableScreen::new);
            MenuScreens.register(ModMenuTypes.PLAYER_STATS_MENU.get(), PlayerStatsScreen::new);
            MenuScreens.register(ModMenuTypes.PLAYER_CLASS_SELECT_MENU.get(), PlayerClassSelectScreen::new);
            MenuScreens.register(ModMenuTypes.GEM_INFUSING_STATION_MENU.get(), GemInfusingStationScreen::new);
            MenuScreens.register(ModMenuTypes.TLT_CONTAINER.get(), ToolLevelingTableScreen::new);
            MenuScreens.register(ModMenuTypes.CRAFTING_MENU_TYPE.get(), CraftingScreen::new);
    }
}