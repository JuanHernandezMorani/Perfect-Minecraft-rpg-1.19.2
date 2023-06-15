package net.cheto97.rpgcraftmod.ModHud.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;

import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;

public class Settings {
    private final String CONFIG_VERSION = "1.0.0-1.19.2";
    private Map<String, Setting> settings = new LinkedHashMap<String, Setting>();
    private File file;
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static final String hud_type = "hud_type";
    public static final String enable_button_tooltip = "enable_button_tooltip";
    public static final String show_update_notification = "show_update_notification";
    public static final String show_convert_notification = "show_convert_notification";
    public static final String hotbar_position = "hotbar_position";
    public static final String widget_position = "widget_position";
    public static final String chat_position = "chat_position";
    public static final String armor_position = "armor_position";

    public static final String reduce_size = "reduce_size";
    public static final String show_armor = "show_armor";
    public static final String show_arrow_count = "show_arrow_count";
    public static final String show_item_durability = "show_item_durability";
    public static final String show_block_count = "show_block_count";
    public static final String show_durability_bar = "show_durability_bar";
    public static final String armor_det_position = "armor_det_position";
    public static final String arrow_det_position = "arrow_det_position";
    public static final String item_det_position = "item_det_position";

    public static final String show_numbers_life = "show_numbers_life";
    public static final String life_percentage = "life_percentage";
    public static final String color_life = "color_life";
    public static final String show_numbers_mana = "show_numbers_mana";
    public static final String mana_percentage = "mana_percentage";
    public static final String color_mana = "color_mana";
    public static final String color_absorption = "color_absorption";
    public static final String color_poison = "color_poison";
    public static final String color_wither = "color_wither";
    public static final String life_position = "life_position";
    public static final String mana_position = "mana_position";
    public static final String mount_life_position = "mount_life_position";
    public static final String mount_life_percentage = "mount_life_percentage";

    public static final String show_numbers_food = "show_numbers_food";
    public static final String hunger_percentage = "hunger_percentage";
    public static final String show_hunger_preview = "show_hunger_preview";
    public static final String color_food = "color_food";
    public static final String color_hunger = "color_hunger";
    public static final String hunger_position = "hunger_position";

    public static final String show_numbers_rpgexperience = "show_numbers_rpgexperience";
    public static final String rpgexperience_percentage = "rpgexperience_percentage";
    public static final String color_rpgexperience = "color_rpgexperience";
    public static final String rpgexperience_position = "rpgexperience_position";
    public static final String rpglevel_position = "rpglevel_position";

    public static final String enable_clock = "enable_clock";
    public static final String enable_clock_color = "enable_clock_color";
    public static final String enable_immersive_clock = "enable_immersive_clock";
    public static final String clock_time_format = "clock_time_format";
    public static final String clock_position = "clock_position";

    public static final String enable_compass = "enable_compass";
    public static final String enable_compass_color = "enable_compass_color";
    public static final String enable_immersive_compass = "enable_immersive_compass";
    public static final String enable_compass_coordinates = "enable_compass_coordinates";
    public static final String invert_compass = "invert_compass";
    public static final String compass_position = "compass_position";

    public static final String enable_pickup = "enable_pickup";
    public static final String pickup_duration = "pickup_duration";
    public static final String pickup_position = "pickup_position";

    public static final String render_player_face = "render_player_face";
    public static final String face_position = "face_position";

    public static final String limit_jump_bar = "limit_jump_bar";
    public static final String color_jump_bar = "color_jump_bar";
    public static final String jump_bar_position = "jump_bar_position";

    public static final String enable_view = "enable_view";
    public static final String inspector_position = "inspector_position";
    public static final String show_entity_armor = "show_entity_armor";

    public static final String color_air = "color_air";
    public static final String air_position = "air_position";

    public static final String status_time = "status_time";
    public static final String status_vertical = "status_vertical";
    public static final String status_position = "pickup_position";
    public static final String status_scale = "status_scale";

    public static final String force_render = "force_render";
    public static final String render_vanilla = "render_vanilla";
    public static final String prevent_event = "prevent_event";
    public static final String prevent_element_render = "prevent_element_render";

    private File rpgHudDir() {
        Minecraft mc = Minecraft.getInstance();
        return (new File(mc.gameDirectory.getPath(), "Config" + File.separator + "Rpgcraft Mod Configuration"));
    }

    public Settings() {
        file = rpgHudDir();
        init();
        this.load();
        this.save();

    }

    public void init() {
        addSetting(hud_type, new SettingHudType(hud_type, "rpg"));
        addSetting(enable_button_tooltip, new SettingBoolean(enable_button_tooltip, true));

        addSetting(reduce_size, new SettingBoolean(reduce_size, HudType.DETAILS, false));
        addSetting(show_armor, new SettingBoolean(show_armor, HudType.DETAILS, true));
        addSetting(show_arrow_count, new SettingBoolean(show_arrow_count, HudType.DETAILS, true));
        addSetting(show_item_durability, new SettingBoolean(show_item_durability, HudType.DETAILS, true));
        addSetting(show_block_count, new SettingBoolean(show_block_count, HudType.DETAILS, true));
        addSetting(show_durability_bar, new SettingBoolean(show_durability_bar, HudType.DETAILS, true));
        addSetting(armor_det_position, new SettingPosition(armor_det_position, HudType.DETAILS, 0, 0));
        addSetting(arrow_det_position, new SettingPosition(arrow_det_position, HudType.DETAILS, 0, 0));
        addSetting(item_det_position, new SettingPosition(item_det_position, HudType.DETAILS, 0, 0));

        addSetting(show_numbers_life, new SettingBoolean(show_numbers_life, HudType.LIFE, true));
        addSetting(life_percentage, new SettingBoolean(life_percentage, HudType.LIFE, false));
        addSetting(color_life, new SettingColor(color_life, HudType.LIFE, HudElement.COLOR_RED));
        addSetting(color_absorption, new SettingColor(color_absorption, HudType.LIFE, HudElement.COLOR_ORANGE));
        addSetting(color_poison, new SettingColor(color_poison, HudType.LIFE, HudElement.COLOR_GREEN));
        addSetting(color_wither, new SettingColor(color_wither, HudType.LIFE, HudElement.COLOR_BLACK));
        addSetting(life_position, new SettingPosition(life_position, HudType.LIFE, 0, 0));

        addSetting(show_numbers_mana, new SettingBoolean(show_numbers_mana, HudType.MANA, true));
        addSetting(mana_percentage, new SettingBoolean(mana_percentage, HudType.MANA, false));
        addSetting(color_mana, new SettingColor(color_mana, HudType.MANA, HudElement.COLOR_BLUE));
        addSetting(mana_position, new SettingPosition(mana_position, HudType.MANA, 0, 0));

        addSetting(show_numbers_food, new SettingBoolean(show_numbers_food, HudType.FOOD, true));
        addSetting(hunger_percentage, new SettingBoolean(hunger_percentage, HudType.FOOD, false));
        addSetting(show_hunger_preview, new SettingBoolean(show_hunger_preview, HudType.FOOD, true));
        addSetting(color_food, new SettingColor(color_food, HudType.FOOD, HudElement.COLOR_YELLOW));
        addSetting(color_hunger, new SettingColor(color_hunger, HudType.FOOD, 0x9ba067));
        addSetting(hunger_position, new SettingPosition(hunger_position, HudType.FOOD, 0, 0));

        addSetting(show_numbers_rpgexperience, new SettingBoolean(show_numbers_rpgexperience, HudType.RPGEXPERIENCE, true));
        addSetting(rpgexperience_percentage, new SettingBoolean(rpgexperience_percentage, HudType.RPGEXPERIENCE, false));
        addSetting(color_rpgexperience, new SettingColor(color_rpgexperience, HudType.RPGEXPERIENCE, HudElement.COLOR_BLUE));
        addSetting(rpgexperience_position, new SettingPosition(rpgexperience_position, HudType.RPGEXPERIENCE, 0, 0));

        addSetting(mount_life_percentage, new SettingBoolean(mount_life_percentage, HudType.MOUNT_LIFE, false));

        addSetting(enable_clock, new SettingBoolean(enable_clock, HudType.CLOCK, true));
        addSetting(enable_clock_color, new SettingBoolean(enable_clock_color, HudType.CLOCK, true));
        addSetting(enable_immersive_clock, new SettingBoolean(enable_immersive_clock, HudType.CLOCK, false));
        addSetting(clock_time_format, new SettingString(clock_time_format, HudType.CLOCK, 0, new String[] { "time.24", "time.12" }));
        addSetting(clock_position, new SettingPosition(clock_position, HudType.CLOCK, 0, 0));

        addSetting(enable_compass, new SettingBoolean(enable_compass, HudType.COMPASS, true));
        addSetting(enable_compass_color, new SettingBoolean(enable_compass_color, HudType.COMPASS, true));
        addSetting(enable_immersive_compass, new SettingBoolean(enable_immersive_compass, HudType.COMPASS, false));
        addSetting(enable_compass_coordinates, new SettingBoolean(enable_compass_coordinates, HudType.COMPASS, true));
        addSetting(invert_compass, new SettingBoolean(invert_compass, HudType.COMPASS, false));
        addSetting(compass_position, new SettingPosition(compass_position, HudType.COMPASS, 0, 0));

        addSetting(render_player_face, new SettingBoolean(render_player_face, HudType.WIDGET, true));
        addSetting(widget_position, new SettingPosition(widget_position, HudType.WIDGET, 0, 0));
        addSetting(face_position, new SettingPosition(face_position, HudType.WIDGET, 0, 0));

        addSetting(limit_jump_bar, new SettingBoolean(limit_jump_bar, HudType.JUMP_BAR, true));
        addSetting(color_jump_bar, new SettingColor(color_jump_bar, HudType.JUMP_BAR, HudElement.COLOR_GREY));
        addSetting(jump_bar_position, new SettingPosition(jump_bar_position, HudType.JUMP_BAR, 0, 0));

        addSetting(color_air, new SettingColor(color_air, HudType.AIR, HudElement.COLOR_WHITE));
        addSetting(air_position, new SettingPosition(air_position, HudType.AIR, 0, 0));

        addSetting(status_vertical, new SettingBoolean(status_vertical, HudType.STATUS_EFFECTS, false));
        addSetting(status_time, new SettingBoolean(status_time, HudType.STATUS_EFFECTS, true));
        addSetting(status_position, new SettingPosition(status_position, HudType.STATUS_EFFECTS, 0, 0));
        addSetting(status_scale, new SettingDouble(status_scale, HudType.STATUS_EFFECTS, 1, 0, 0, 0));

        addSetting(mount_life_position, new SettingPosition(mount_life_position, HudType.MOUNT_LIFE, 0, 0));
        addSetting(hotbar_position, new SettingPosition(hotbar_position, HudType.HOTBAR, 0, 0));
        addSetting(rpglevel_position, new SettingPosition(rpglevel_position, HudType.RPGLEVEL, 0, 0));
        addSetting(armor_position, new SettingPosition(armor_position, HudType.ARMOR, 0, 0));

        addDebugSettings(HudType.ARMOR);
        addDebugSettings(HudType.HOTBAR);
        addDebugSettings(HudType.AIR);
        addDebugSettings(HudType.LIFE);
        addDebugSettings(HudType.FOOD);
        addDebugSettings(HudType.RPGEXPERIENCE);
        addDebugSettings(HudType.RPGLEVEL);
        addDebugSettings(HudType.MOUNT_LIFE);
        addDebugSettings(HudType.JUMP_BAR);
        addDebugSettings(HudType.STATUS_EFFECTS);
    }

    public void addDebugSettings(HudType type) {
        addSetting(render_vanilla + "_" + type.name().toLowerCase(), new SettingBooleanDebug(render_vanilla + "_" + type.name().toLowerCase(), type, false));
        addSetting(prevent_event + "_" + type.name().toLowerCase(), new SettingBooleanDebug(prevent_event + "_" + type.name().toLowerCase(), type, false));
        addSetting(prevent_element_render + "_" + type.name().toLowerCase(),
                new SettingBooleanDebug(prevent_element_render + "_" + type.name().toLowerCase(), type, false));
    }

    public Setting getSetting(String id) {
        return this.settings.get(id);
    }

    public int[] getPositionValue(String i) {
        String[] postions = this.settings.get(i).getValue().toString().split("_");
        int[] values = { Integer.valueOf(postions[0]), Integer.valueOf(postions[1]) };
        return values;
    }

    public Object getValue(String i) {
        return this.settings.get(i).getValue();
    }

    public double getDoubleValue(String i) {
        return this.settings.get(i).getDoubleValue();
    }

    public Integer getIntValue(String i) {
        return this.settings.get(i).getIntValue();
    }

    public Boolean getBoolValue(String i) {
        return this.settings.get(i).getBoolValue();
    }

    public Float getFloatValue(String i) {
        return this.settings.get(i).getFloatValue();
    }

    public String getStringValue(String i) {
        return this.settings.get(i).getStringValue();
    }

    public void resetSetting(String i) {
        Setting setting = this.settings.get(i);
        setting.resetValue();
        this.settings.put(i, setting);
    }

    public void increment(String i) {
        Setting setting = this.settings.get(i);
        setting.increment();
        this.settings.put(i, setting);
    }

    public void setSetting(String i, Object o) {
        Setting setting = this.settings.get(i);
        setting.setValue(o);
        this.settings.put(i, setting);
    }

    public void addSetting(String id, Setting setting) {
        this.settings.put(id, setting);
    }

    public boolean doesSettingExist(String i) {
        return this.settings.containsKey(i);
    }

    public String getButtonString(String id) {
        Setting setting = this.settings.get(id);
        String s = I18n.get(setting.getName(), new Object[0]) + ": ";
        if(setting instanceof SettingBoolean) {
            return s + (setting.getBoolValue() ? I18n.get("options.on", new Object[0]) : I18n.get("options.off", new Object[0]));
        } else if(setting instanceof SettingString || setting instanceof SettingHudType) {
            return s + I18n.get(setting.getStringValue(), new Object[0]);
        } else if(setting instanceof SettingColor) {
            return s + intToHexString(setting.getIntValue());
        } else if(setting instanceof SettingInteger) {
            return s + setting.getIntValue();
        } else if(setting instanceof SettingFloat) {
            SettingFloat sf = (SettingFloat) setting;
            return s + (id == pickup_duration ? Math.ceil(SettingFloat.snapToStepClamp(sf, sf.getFloatValue())) + " " + I18n.get("gui.rpg.sec", new Object[0])
                    : String.valueOf(SettingFloat.snapToStepClamp(sf, sf.getFloatValue())));
        } else if(setting instanceof SettingPosition || setting instanceof SettingDouble) {
            return s;
        } else {
            return s + "error";
        }
    }

    public static String intToHexString(int hex) {
        String s = Integer.toHexString(hex).toUpperCase();
        if(hex <= 0xFFFFF) {
            s = "0" + s;
            if(hex <= 0xFFFF) {
                s = "0" + s;
                if(hex <= 0xFFF) {
                    s = "0" + s;
                    if(hex <= 0xFF) {
                        s = "0" + s;
                        if(hex <= 0xF) {
                            s = "0" + s;
                        }
                    }
                }
            }
        }
        return "#" + s;
    }

    public void saveSettings() {
        this.save();
    }

    public List<String> getSettingsOf(HudType type) {
        List<String> settings = new ArrayList<String>();
        for(String key : this.settings.keySet()) {
            if(this.settings.get(key).associatedType == type)
                settings.add(key);
        }
        return settings;
    }

    public List<String> getSettingsOf(String type) {
        List<String> settings = new ArrayList<String>();
        for(String key : this.settings.keySet()) {
            if(this.settings.get(key).associatedType != null && this.settings.get(key).associatedType.name() == type)
                settings.add(key);
            else if(type == "general" && this.settings.get(key).associatedType == null)
                settings.add(key);
        }
        return settings;
    }

    public void save() {
        try {
            if(file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            if(!file.exists() && !file.createNewFile()) {
                return;
            }

            if(file.canWrite()) {
                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

                buffer.write("Version=" + CONFIG_VERSION + NEW_LINE);

                save(buffer);

                buffer.close();
                fos.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        BufferedReader buffer = null;
        try {
            if(file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            if(!file.exists()) {
                if(!file.createNewFile())
                    return;
            }

            if(file.canRead()) {
                buffer = new BufferedReader(new FileReader(file));

                String line;

                while(true) {
                    line = buffer.readLine();
                    if(line == null || line.isEmpty())
                        break;
                    if(line.contains(":") && line.contains("=")) {
                        String[] type = line.split(":");
                        String[] setting = type[1].split("=");
                        if(this.getSetting(setting[0]) != null) {
                            if(type[0].matches("B")) {
                                this.setSetting(setting[0], this.getSetting(setting[0]).setValue(Boolean.valueOf(setting[1])));
                            } else if(type[0].matches("S")) {
                                this.setSetting(setting[0], this.getSetting(setting[0]).setValue(setting[1]));
                            } else if(type[0].matches("C")) {
                                this.setSetting(setting[0], this.getSetting(setting[0]).setValue(Integer.valueOf(setting[1])));
                            } else if(type[0].matches("H")) {
                                this.setSetting(setting[0], this.getSetting(setting[0]).setValue(setting[1]));
                            } else if(type[0].matches("I")) {
                                this.setSetting(setting[0], this.getSetting(setting[0]).setValue(Integer.valueOf(setting[1])));
                            } else if(type[0].matches("F")) {
                                this.setSetting(setting[0], this.getSetting(setting[0]).setValue(Float.valueOf(setting[1])));
                            } else if(type[0].matches("D")) {
                                this.setSetting(setting[0], this.getSetting(setting[0]).setValue(Double.valueOf(setting[1])));
                            } else if(type[0].matches("P")) {
                                this.setSetting(setting[0], setting[1]);
                            }
                        }

                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void save(BufferedWriter out) throws IOException {
        for(Setting setting : settings.values()) {
            if(setting instanceof SettingBoolean) {
                out.write("B:" + setting.ID + "=" + setting.getValue() + NEW_LINE);
            } else if(setting instanceof SettingString) {
                out.write("S:" + setting.ID + "=" + setting.getValue() + NEW_LINE);
            } else if(setting instanceof SettingHudType) {
                out.write("H:" + setting.ID + "=" + setting.getValue() + NEW_LINE);
            } else if(setting instanceof SettingColor) {
                out.write("C:" + setting.ID + "=" + setting.getValue() + NEW_LINE);
            } else if(setting instanceof SettingInteger) {
                out.write("I:" + setting.ID + "=" + setting.getValue() + NEW_LINE);
            } else if(setting instanceof SettingFloat) {
                out.write("F:" + setting.ID + "=" + setting.getValue() + NEW_LINE);
            } else if(setting instanceof SettingDouble) {
                out.write("D:" + setting.ID + "=" + setting.getValue() + NEW_LINE);
            } else if(setting instanceof SettingPosition) {
                out.write("P:" + setting.ID + "=" + setting.getValue() + NEW_LINE);
            } else {
                out.write("E:" + setting.ID + "=" + "ERROR" + NEW_LINE);
            }
        }
    }
}
