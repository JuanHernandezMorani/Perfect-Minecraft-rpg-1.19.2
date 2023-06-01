package net.cheto97.rpgcraftmod.ModHud.Elements.vanilla;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.ModHud.HudElement;
import net.cheto97.rpgcraftmod.ModHud.HudType;
import net.cheto97.rpgcraftmod.ModHud.settings.Settings;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HudElementClockVanilla  extends HudElement {

    public HudElementClockVanilla() {
        super(HudType.CLOCK, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return super.checkConditions()
                && this.settings.getBoolValue(Settings.enable_clock)
                && !this.mc.options.renderDebug
                && (this.settings.getBoolValue(Settings.enable_immersive_clock) ? this.mc.player.getInventory().contains(new ItemStack(Items.CLOCK)) : true);
    }

    @Override
    public void drawElement(Gui gui, PoseStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        int clockColor = 0xFFFFFF;
        if (this.settings.getBoolValue(Settings.enable_clock_color)) {
            clockColor = getClockColor();
        }
        if (this.settings.getBoolValue(Settings.reduce_size))
            ms.scale(0.5f, 0.5f, 0.5f);
        Gui.drawString(ms, this.mc.font, getTime(), (this.settings.getBoolValue(Settings.reduce_size) ? 8 : 4) + this.settings.getPositionValue(Settings.clock_position)[0], (this.settings.getBoolValue(Settings.reduce_size) ? 104 : 52) + this.settings.getPositionValue(Settings.clock_position)[1], clockColor);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.settings.getBoolValue(Settings.reduce_size))
            ms.scale(2f, 2f, 2f);
    }

    /** Returns the time of the minecraft world as a String */
    public String getTime() {
        long time = this.mc.player.getLevel().getDayTime();
        long day = (long) (this.mc.player.getLevel().getDayTime() / 24000L);
        long currentTime = time - (24000L * day);
        long currentHour = (currentTime / 1000L) + 6L;
        double currentTimeMin = currentTime - ((currentHour - 6L) * 1000L);
        currentTimeMin = currentTimeMin / (1000.0D / 60.0D);
        int currentMin = (int) currentTimeMin;
        if (currentHour > 24)
            currentHour -= 24L;
        if (this.settings.getStringValue(Settings.clock_time_format) == "time.24") {
            return get24HourTimeForString(currentHour, currentMin);
        }
        return get12HourTimeForString(currentHour, currentMin);
    }
    public static String get24HourTimeForString(long currentHour, long currentMin) {
        StringBuilder sb = new StringBuilder();
        if (currentHour == 24)
            currentHour = 0;
        if (currentHour < 10)
            sb.append("0");
        sb.append(currentHour);
        return sb.toString() + ":" + getMinuteForString(currentMin);
    }
    public static String get12HourTimeForString(long currentHour, long currentMin) {
        StringBuilder sb = new StringBuilder();
        String period = "am";
        if (currentHour == 12) {
            period = "pm";
        }
        if (currentHour == 24) {
            currentHour = 12;
            period = "am";
        }
        if (currentHour > 12) {
            currentHour -= 12;
            period = "pm";
        }
        if (currentHour < 10)
            sb.append(0);
        sb.append(currentHour);
        return sb.toString() + ":" + getMinuteForString(currentMin) + " " + period;
    }

    public static String getMinuteForString(long currentMin) {
        StringBuilder sb = new StringBuilder();
        if (currentMin < 10)
            sb.append("0");
        sb.append(currentMin);
        return sb.toString();
    }

    public int getClockColor() {
        long time = this.mc.player.level.getDayTime();
        long day = (this.mc.player.level.getDayTime() / 24000L);
        long currentTime = time - (24000L * day);
        if (currentTime < 1000)
            return 0xFFAF00;
        else if (currentTime < 6000)
            return 0xFFAF00;
        else if (currentTime < 11000)
            return 0xFFCF00;
        else if (currentTime < 12000)
            return 0xFFAF00;
        else if (currentTime < 13000)
            return 0xFFA200;
        else if (currentTime < 13500)
            return 0xE36E21;
        else if (currentTime < 18000)
            return 0x345D74;
        else if (currentTime < 21000)
            return 0x1F3847;
        else if (currentTime < 22250)
            return 0x345D74;
        else if (currentTime < 22500)
            return 0x775D74;
        else if (currentTime < 23000)
            return 0xE36E21;
        else
            return 0xFFA200;
    }
}