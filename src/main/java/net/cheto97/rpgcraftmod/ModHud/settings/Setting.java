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

    public abstract Object getValue();
    public Boolean getBoolValue() {
        if(getValue() instanceof Boolean)
            return (Boolean) getValue();
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
    public abstract Setting setValue(Object o);

    public String getName() {
        return I18n.get("name." + this.ID);
    }

}
