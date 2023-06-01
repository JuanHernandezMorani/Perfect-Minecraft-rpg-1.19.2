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
            return I18n.get("name." + prevent_event, new Object[0]);
        else if(this.ID.contains(prevent_element_render))
            return I18n.get("name." + prevent_element_render, new Object[0]);
        else if(this.ID.contains(render_vanilla))
            return I18n.get("name." + render_vanilla, new Object[0]);
        else if(this.ID.contains(force_render))
            return I18n.get("name." + force_render, new Object[0]);
        else return I18n.get("name." + this.ID + "error", new Object[0]);
    }

    public String getTooltip() {
        if(this.ID.contains(prevent_event))
            return I18n.get("tooltip." + prevent_event, new Object[0]);
        else if(this.ID.contains(prevent_element_render))
            return I18n.get("tooltip." + prevent_element_render, new Object[0]);
        else if(this.ID.contains(render_vanilla))
            return I18n.get("tooltip." + render_vanilla, new Object[0]);
        else if(this.ID.contains(force_render))
            return I18n.get("tooltip." + force_render, new Object[0]);
        else return I18n.get("tooltip." + this.ID + "error", new Object[0]);
    }

    public String getFormatedTooltip() {
        if(this.ID.contains(prevent_event))
            return I18n.get("tooltip." + prevent_event, new Object[0]).replaceAll("/n", " ");
        else if(this.ID.contains(prevent_element_render))
            return I18n.get("tooltip." + prevent_element_render, new Object[0]).replaceAll("/n", " ");
        else if(this.ID.contains(render_vanilla))
            return I18n.get("tooltip." + render_vanilla, new Object[0]).replaceAll("/n", " ");
        else if(this.ID.contains(force_render))
            return I18n.get("tooltip." + force_render, new Object[0]).replaceAll("/n", " ");
        else return I18n.get("tooltip." + this.ID + "error", new Object[0]).replaceAll("/n", " ");
    }
}