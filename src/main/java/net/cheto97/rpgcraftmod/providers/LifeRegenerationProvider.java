package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.LifeRegeneration;
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

public class LifeRegenerationProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<LifeRegeneration> ENTITY_LIFEREGENERATION = CapabilityManager.get(new CapabilityToken<LifeRegeneration>() {});

    private LifeRegeneration liferegeneration = null;

    private final LazyOptional<LifeRegeneration> optional = LazyOptional.of(this::createLifeRegeneration);

    private LifeRegeneration createLifeRegeneration(){
        if(this.liferegeneration == null){
            this.liferegeneration = new LifeRegeneration();
        }

        return this.liferegeneration;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side){
        if(cap == ENTITY_LIFEREGENERATION){
            return optional.cast();
        }
        return LazyOptional.empty();
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createLifeRegeneration().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createLifeRegeneration().loadNBTData(nbt);
    }
}
