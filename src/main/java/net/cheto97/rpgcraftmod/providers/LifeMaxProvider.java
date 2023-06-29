package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.LifeMax;
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

public class LifeMaxProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<LifeMax> ENTITY_LIFE_MAX = CapabilityManager.get(new CapabilityToken<LifeMax>() {});
    private LifeMax lifeMax = null;
    private final LazyOptional<LifeMax> optional = LazyOptional.of(this::createLifeMax);

    private LifeMax createLifeMax() {
        if(this.lifeMax == null){
            this.lifeMax = new LifeMax();
        }

        return this.lifeMax;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_LIFE_MAX){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createLifeMax().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createLifeMax().loadNBTData(nbt);
    }
}
