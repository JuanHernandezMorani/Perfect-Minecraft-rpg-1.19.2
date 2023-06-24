package net.cheto97.rpgcraftmod.ModHud.settings;

import net.cheto97.rpgcraftmod.ModHud.HudType;

public class SettingColor extends Setting{

    public final int defaultColor;
    public int color;


    public SettingColor(String ID, HudType type, int color) {
        super(ID, type);
        this.defaultColor = color;
        this.color = color;
    }

    public void setColor(int color){
        this.color = color;
    }

    @Override
    public Object getValue() {
        return this.color;
    }

    @Override
    public Setting setValue(Object o) {
        if (o instanceof String) {
            this.color = Integer.parseInt((String) o, 16);
        } else if(o instanceof Integer) {
            this.color = (Integer) o;
        }
        return this;
    }
}