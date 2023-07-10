package net.cheto97.rpgcraftmod.menu;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.util.ToolLevelingUp.Names;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RpgcraftMod.MOD_ID);

    public static final RegistryObject<MenuType<WizardTableMenu>> WIZARD_TABLE_MENU =
            registerMenuType(WizardTableMenu::new, "wizard_table_menu");

    public static final RegistryObject<MenuType<PlayerStatsMenu>> PLAYER_STATS_MENU =
            registerMenuType(PlayerStatsMenu::new, "player_stats_menu");

    public static final RegistryObject<MenuType<PlayerClassSelectMenu>> PLAYER_CLASS_SELECT_MENU =
            registerMenuType(PlayerClassSelectMenu::new,"player_class_select_menu");

    public static final RegistryObject<MenuType<GemInfusingStationMenu>> GEM_INFUSING_STATION_MENU =
            registerMenuType(GemInfusingStationMenu::new, "gem_infusing_station_menu");

    public static final RegistryObject<MenuType<ToolLevelingTableMenu>> TLT_CONTAINER =
            registerMenuType(ToolLevelingTableMenu::new,"tool_leveling_table_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }


    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
