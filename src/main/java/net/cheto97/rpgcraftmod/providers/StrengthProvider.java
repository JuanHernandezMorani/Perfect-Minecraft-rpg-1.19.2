package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Strength;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StrengthProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Strength> ENTITY_STRENGTH = CapabilityManager.get(new CapabilityToken<Strength>() {});

    private Strength strength = null;
    private final LazyOptional<Strength> optional = LazyOptional.of(this::createStrength);

    private Strength createStrength() {
        if(this.strength == null){
            this.strength = new Strength();
        }

        return this.strength;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_STRENGTH){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createStrength().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createStrength().loadNBTData(nbt);
    }
}
