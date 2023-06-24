package net.cheto97.rpgcraftmod.ModHud.settings;

import net.cheto97.rpgcraftmod.ModHud.HudType;

public class SettingPosition extends Setting{

    public final int defaultX, defaultY;
    public int x, y;

    public SettingPosition(String ID, HudType type, int x, int y) {
        super(ID, type);
        this.defaultX = x;
        this.x = x;
        this.defaultY = y;
        this.y = y;
    }

    @Override
    public Object getValue() {
        return x + "_" + y;
    }

    @Override
    public Setting setValue(Object o) {
        String[] positions = ((String) o).split("_");
        if(positions.length > 1 && !positions[0].equals("") && !positions[1].equals("")) {
            try {
                this.x = Integer.parseInt(positions[0]);
                this.y = Integer.parseInt(positions[1]);
            }
            catch(NumberFormatException e)
            {
                this.x = 0;
                this.y = 0;
            }
        }
        return this;
    }
}