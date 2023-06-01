package net.cheto97.rpgcraftmod.ModHud.settings;

import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.minecraft.client.resources.language.I18n;

public abstract class Setting {
    public final HudType associatedType;
    public final String ID;

    public Setting(String ID) {
        this.ID = ID;
        this.associatedType = null;
    }

    public Setting(String ID, HudType type) {
        this.ID = ID;
        this.associatedType = type;
    }

    public abstract void increment();

    public abstract Object getValue();

    public Integer getIntValue() {
        if(getValue() instanceof Integer)
            return (Integer) getValue();
        return null;
    }

    public Boolean getBoolValue() {
        if(getValue() instanceof Boolean)
            return (Boolean) getValue();
        return null;
    }

    public Float getFloatValue() {
        if(getValue() instanceof Float)
            return (Float) getValue();
        return null;
    }

    public Double getDoubleValue() {
        if(getValue() instanceof Double)
            return (Double) getValue();
        return null;
    }

    public String getStringValue() {
        if(getValue() instanceof String)
            return (String) getValue();
        return null;
    }

    public abstract Object getDefaultValue();

    public abstract void resetValue();

    public abstract Setting setValue(Object o);

    public String getName() {
        return I18n.get("name." + this.ID, new Object[0]);
    }

    public String getTooltip() {
        return I18n.get("tooltip." + this.ID, new Object[0]);
    }

    public String getFormatedTooltip() {
        return I18n.get("tooltip." + this.ID, new Object[0]).replaceAll("/n", " ");
    }

}
