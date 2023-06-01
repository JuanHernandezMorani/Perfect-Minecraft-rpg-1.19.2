package net.cheto97.rpgcraftmod.ModHud.settings;

import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.RpgcraftMod;

import java.util.Set;

public class SettingHudType extends Setting {

    public final String defaultValue;
    public String value;

    public SettingHudType(String ID, String value) {
        super(ID);
        this.defaultValue = value;
        this.value = this.defaultValue;
    }

    public SettingHudType(String ID, HudType type, int defaultValueId) {
        super(ID, type);
        this.defaultValue = this.value;
        this.value = this.defaultValue;
    }

    @Override
    public void increment() {
        Set<String> huds = RpgcraftMod.instance.huds.keySet();
        String[] keys = huds.toArray(new String[huds.size()]);
        int size = keys.length;
        for (int n = 0; n < size; n++) {
            if (keys[n].equals(this.value)) {
                n++;
                if (n == size)
                    n = 0;
                this.value = keys[n];
                return;
            }
        }
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void resetValue() {
        this.value = this.defaultValue;
    }

    @Override
    public Setting setValue(Object o) {
        if (o instanceof String) {
            this.value = (String) o;
        }
        return this;
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }
}
