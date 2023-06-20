package net.cheto97.rpgcraftmod.ModHud.Elements.rpg;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.cheto97.rpgcraftmod.client.ClientLifeData;
import net.cheto97.rpgcraftmod.client.ClientMaxLifeData;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;

import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;

public class HudElementLifeRPG extends HudElement {


    public HudElementLifeRPG(){
        super(HudType.LIFE, 0, 0, 0, 0, true);
        parent = HudType.WIDGET;
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.options.hideGui;
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledHeight, int scaledWidth) {
        if(this.mc.player != null){
            bind(INTERFACE);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            double health = ClientLifeData.getPlayerLife();
            int absorption = Mth.ceil(this.mc.player.getAbsorptionAmount());
            double healthMax = ClientMaxLifeData.getPlayerMaxLife();
            int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.life_position)[0];
            int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 9 : 5) + this.settings.getPositionValue(Settings.life_position)[1];
            if (absorption > 1)
                gui.blit(ms, posX, posY, 0, 88, (int) (110.0D * ( (health + absorption) /  (healthMax + absorption))), 12);
            if (this.mc.player.hasEffect(MobEffects.POISON)) {
                gui.blit(ms, posX, posY, 141, 160, (int) (110.0D * ( health / (healthMax + absorption))), 12);
            } else if (this.mc.player.hasEffect(MobEffects.WITHER)) {
                gui.blit(ms, posX, posY, 34, 244, (int) (110.0D * ( health /  (healthMax + absorption))), 12);
            } else {
                gui.blit(ms, posX, posY, 0, 100, (int) (110.0D * ( health /  (healthMax + absorption))), 12);
            }

            String stringHealth =  doubleToString(health+absorption) + "/" + doubleToString(healthMax);
            if (this.settings.getBoolValue(Settings.show_numbers_life))
                Gui.drawCenteredString(ms, this.mc.font, stringHealth, posX + 55, posY + 2, -1);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            bind(Gui.GUI_ICONS_LOCATION);
        }
    }
}