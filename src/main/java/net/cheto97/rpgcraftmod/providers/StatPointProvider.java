package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.StatPoint;
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

public class StatPointProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<StatPoint> ENTITY_STATPOINT = CapabilityManager.get(new CapabilityToken<StatPoint>() {});

    private StatPoint stat = null;

    private final LazyOptional<StatPoint> optional = LazyOptional.of(this::createStat);

    private StatPoint createStat(){
        if(this.stat == null){
            this.stat = new StatPoint();
        }

        return this.stat;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_STATPOINT){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createStat().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createStat().loadNBTData(nbt);
    }

}
