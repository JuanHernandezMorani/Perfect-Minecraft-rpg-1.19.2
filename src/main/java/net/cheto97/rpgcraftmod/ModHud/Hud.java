package net.cheto97.rpgcraftmod.ModHud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.HashMap;
import java.util.Map;

import static net.cheto97.rpgcraftmod.ModHud.HudType.*;

public abstract class Hud {
    private final String hudKey;

    protected Map<HudType, HudElement> elements = new HashMap<>();
    protected Minecraft mc;

    public int chatOffset = 0;
    public Hud(Minecraft mc, String hudKey, String hudName) {
        this.mc = mc;
        this.hudKey = hudKey;
        this.setElements();
    }

    public boolean checkConditions() {
        return  this.mc.player != null && !this.mc.player.isSpectator();
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
        this.elements.put(VIEW, setElementView());
        this.elements.put(WIDGET, setElementWidget());
        this.elements.put(COMPASS, setElementCompass());
        this.elements.put(STATUS_EFFECTS, setElementMobEffects());

    }
    public String getHudKey() {
        return this.hudKey;
    }
    protected abstract HudElement setElementHotbar();
    protected abstract HudElement setElementLife();
    protected abstract HudElement setElementMana();
    protected abstract HudElement setElementFood();
    protected abstract HudElement setElementArmor();
    protected abstract HudElement setElementAir();
    protected abstract HudElement setElementCustomExperience();
    protected abstract HudElement setElementCustomLevel();
    protected abstract HudElement setElementView();
    protected abstract HudElement setElementJumpBar();
    protected abstract HudElement setElementMountLife();
    protected abstract HudElement setElementWidget();
    protected abstract HudElement setElementCompass();
    protected abstract HudElement setElementMobEffects();
    public void drawElement(HudType type, Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        if(checkConditions()){
            this.elements.get(type).draw(gui, ms, zLevel, partialTicks, scaledWidth, scaledHeight);
        }
    }
}
