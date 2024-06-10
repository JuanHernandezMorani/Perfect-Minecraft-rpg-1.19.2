package net.cheto97.rpgcraftmod.util;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.regex.Pattern;

public class ModChatFormatting implements StringRepresentable {

    private final String toString;
    private final int id;
    @Nullable
    private final Integer color;

    ModChatFormatting(int id, @Nullable Integer color) {
        this.id = id;
        this.color = color;
        this.toString = color != null ? String.format("§x§%s§%s§%s§%s§%s§%s",
                Integer.toHexString((color >> 20) & 0xF),
                Integer.toHexString((color >> 16) & 0xF),
                Integer.toHexString((color >> 12) & 0xF),
                Integer.toHexString((color >> 8) & 0xF),
                Integer.toHexString((color >> 4) & 0xF),
                Integer.toHexString(color & 0xF)) : "§r";
    }

    public int getId() {
        return this.id;
    }

    @Nullable
    public Integer getColor() {
        return this.color;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return this.toString;
    }

    private String name(){
        return ColorUtil.getColorName(this.color);
    }
    public static ModChatFormatting fromHexColor(int hexColor) {
        return new ModChatFormatting(-1, hexColor);
    }

    public static boolean isValidHex(int hex){
        String check = (""+hex).substring(2);
        return Pattern.matches("^([A-Fa-f0-9]{6})$", check);
    }
}
