package net.cheto97.rpgcraftmod.ModHud.settings;

import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.minecraft.client.resources.language.I18n;

public class SettingBooleanDebug extends SettingBoolean{

    public static final String force_render = "force_render";
    public static final String render_vanilla = "render_vanilla";
    public static final String prevent_event = "prevent_event";
    public static final String prevent_element_render = "prevent_element_render";

    public SettingBooleanDebug(String ID, HudType type, boolean defaultValue) {
        super(ID, type, defaultValue);
    }

    public String getName() {
        if(this.ID.contains(prevent_event))
            return I18n.get("name." + prevent_event);
        else if(this.ID.contains(prevent_element_render))
            return I18n.get("name." + prevent_element_render);
        else if(this.ID.contains(render_vanilla))
            return I18n.get("name." + render_vanilla);
        else if(this.ID.contains(force_render))
            return I18n.get("name." + force_render);
        else return I18n.get("name." + this.ID + "error");
    }
}