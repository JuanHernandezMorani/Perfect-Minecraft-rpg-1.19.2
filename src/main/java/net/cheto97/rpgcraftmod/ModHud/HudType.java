package net.cheto97.rpgcraftmod.ModHud;

import net.minecraft.client.resources.language.I18n;

public enum HudType {
    VOID("name.void"),
    HOTBAR("name.hotbar"),
    LIFE("name.life"),
    MANA("name.mana"),
    ARMOR("name.armor"),
    FOOD("name.food"),
    MOUNT_LIFE("name.mount_life"),
    AIR("name.air"),
    JUMP_BAR("name.jump_bar"),
    RPGEXPERIENCE("name.rpgexperience"),
    RPGLEVEL("name.rpglevel"),
    CLOCK("name.clock"),
    DETAILS("name.details"),
    WIDGET("name.widget"),
    COMPASS("name.compass"),
    STATUS_EFFECTS("name.status_effects");

    private final String displayName;

    HudType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return I18n.get(this.displayName);
    }

}
