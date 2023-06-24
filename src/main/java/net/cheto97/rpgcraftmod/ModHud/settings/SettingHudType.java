package net.cheto97.rpgcraftmod.ModHud.settings;


public class SettingHudType extends Setting {

    public final String defaultValue;
    public String value;

    public SettingHudType(String ID, String value) {
        super(ID);
        this.defaultValue = value;
        this.value = this.defaultValue;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Setting setValue(Object o) {
        if (o instanceof String) {
            this.value = (String) o;
        }
        return this;
    }
}
