package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Luck;
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

public class LuckProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Luck> ENTITY_LUCK = CapabilityManager.get(new CapabilityToken<Luck>() {});

    private Luck luck = null;
    private final LazyOptional<Luck> optional = LazyOptional.of(this::createLuck);

    private Luck createLuck() {
        if(this.luck == null){
            this.luck = new Luck();
        }

        return this.luck;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_LUCK){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createLuck().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createLuck().loadNBTData(nbt);
    }
}
