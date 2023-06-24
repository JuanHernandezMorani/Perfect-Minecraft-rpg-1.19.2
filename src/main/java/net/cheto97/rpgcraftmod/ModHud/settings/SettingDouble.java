package net.cheto97.rpgcraftmod.ModHud.settings;

import net.cheto97.rpgcraftmod.ModHud.HudType;

public class SettingDouble extends Setting {

    public final double defaultValue;
    public double value;
    public final double minValue;
    public final double maxValue;
    public final double step;

    public SettingDouble(String ID, HudType type, double defaultValue, double minValue, double maxValue, double step) {
        super(ID, type);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.step = step;
    }
    @Override
    public Object getValue() {
        return this.value;
    }
    @Override
    public Setting setValue(Object o) {
        if (o instanceof Double) {
            this.value = (Double) o;
        }
        return this;
    }
}