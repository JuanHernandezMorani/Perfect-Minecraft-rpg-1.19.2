package net.cheto97.rpgcraftmod.ModHud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.HashMap;
import java.util.Map;

import static net.cheto97.rpgcraftmod.ModHud.HudType.*;

public abstract class Hud {
    private final String hudKey;
    private final String hudName;

    protected Map<HudType, HudElement> elements = new HashMap<HudType, HudElement>();
    protected Minecraft mc;

    public int chatOffset = 0;
    public Hud(Minecraft mc, String hudKey, String hudName) {
        this.mc = mc;
        this.hudKey = hudKey;
        this.hudName = hudName;
        this.setElements();
    }
    public void setElements() {
        this.elements.put(HOTBAR, setElementHotbar());
        this.elements.put(LIFE, setElementLife());
        this.elements.put(MANA, setElementMana());
        this.elements.put(ARMOR, setElementArmor());
        this.elements.put(FOOD, setElementFood());
        this.elements.put(MOUNT_LIFE, setElementMountLife());
        this.elements.put(AIR, setElementAir());
        this.elements.put(JUMP_BAR, setElementJumpBar());
        this.elements.put(RPGEXPERIENCE, setElementCustomExperience());
        this.elements.put(RPGLEVEL, setElementCustomLevel());
        this.elements.put(CLOCK, setElementClock());
        this.elements.put(DETAILS, setElementDetails());
        this.elements.put(WIDGET, setElementWidget());
        this.elements.put(COMPASS, setElementCompass());
        this.elements.put(STATUS_EFFECTS, setElementMobEffects());

    }
    public String getHudKey() {
        return this.hudKey;
    }
    public String getHudName() {
        return this.hudName;
    }
    protected abstract HudElement setElementHotbar();
    protected abstract HudElement setElementLife();
    protected abstract HudElement setElementMana();
    protected abstract HudElement setElementFood();
    protected abstract HudElement setElementArmor();
    protected abstract HudElement setElementAir();
    protected abstract HudElement setElementCustomExperience();
    protected abstract HudElement setElementCustomLevel();
    protected abstract HudElement setElementJumpBar();
    protected abstract HudElement setElementMountLife();
    protected abstract HudElement setElementClock();
    protected abstract HudElement setElementDetails();
    protected abstract HudElement setElementWidget();
    protected abstract HudElement setElementCompass();
    protected abstract HudElement setElementMobEffects();
    public void drawElement(HudType type, Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        this.elements.get(type).draw(gui, ms, zLevel, partialTicks, scaledWidth, scaledHeight);
    }
    public boolean checkElementConditions(HudType type) {
        return this.elements.get(type).checkConditions();
    }
    public boolean isVanillaElement(HudType type) {
        return this.elements.get(type) == null;
    }
}
