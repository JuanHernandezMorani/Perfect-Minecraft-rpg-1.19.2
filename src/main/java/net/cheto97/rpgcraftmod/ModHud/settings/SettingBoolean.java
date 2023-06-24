package net.cheto97.rpgcraftmod.ModHud.settings;

import net.cheto97.rpgcraftmod.ModHud.HudType;

public class SettingBoolean extends Setting {

    public final boolean defaultValue;
    public boolean value;

    public SettingBoolean(String ID, boolean defaultValue) {
        super(ID);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public SettingBoolean(String ID, HudType type, boolean defaultValue) {
        super(ID, type);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public Object getValue() {
        return this.value;
    }


    @Override
    public Setting setValue(Object o) {
        if (o instanceof Boolean) {
            this.value = (Boolean) o;
        }
        return this;
    }


}
