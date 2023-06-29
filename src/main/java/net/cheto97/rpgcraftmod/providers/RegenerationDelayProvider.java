package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.modsystem.RegenerationDelay;
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

public class RegenerationDelayProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<RegenerationDelay> ENTITY_REGENERATION_DELAY = CapabilityManager.get(new CapabilityToken<RegenerationDelay>() {});

    private RegenerationDelay cooldown = null;

    private final LazyOptional<RegenerationDelay> optional = LazyOptional.of(this::createCooldown);

    private RegenerationDelay createCooldown() {
        if(this.cooldown == null){
            this.cooldown = new RegenerationDelay();
        }

        return this.cooldown;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_REGENERATION_DELAY){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCooldown().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCooldown().loadNBTData(nbt);
    }
}
