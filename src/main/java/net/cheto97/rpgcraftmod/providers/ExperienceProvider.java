package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.modsystem.Experience;
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

public class ExperienceProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Experience> ENTITY_EXPERIENCE = CapabilityManager.get(new CapabilityToken<Experience>() {});

    private Experience experience = null;
    private final LazyOptional<Experience> optional = LazyOptional.of(this::createExperience);

    private Experience createExperience() {
        if(this.experience == null){
            this.experience = new Experience();
        }

        return this.experience;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_EXPERIENCE){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createExperience().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createExperience().loadNBTData(nbt);
    }
}
