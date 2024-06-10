package net.cheto97.rpgcraftmod.block.custom.lamps;

import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public class MagicLampBlock extends LampBlocks {
    public MagicLampBlock(Properties p) {
        super(p);
    }

    @Override
    protected String getSystemMessage() {
        return "Healing spell activated";
    }

    @Override
    protected String getEffectDescription() {
        return "You feel renewed";
    }

    @Override
    protected ChatFormatting getSystemMessageStyle() {
        return ChatFormatting.DARK_GREEN;
    }

    @Override
    protected String getHexString() {
        return "0xEE82EE";
    }

    @Override
    protected MobEffect getEffect() {
        return MobEffects.REGENERATION;
    }

    @Override
    protected int getLevel() {
        return 2;
    }

    @Override
    protected int getDuration() {
        return 10;
    }

}