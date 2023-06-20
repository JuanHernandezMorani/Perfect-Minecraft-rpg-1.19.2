package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Rank;
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

public class RankProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Rank> ENTITY_RANK = CapabilityManager.get(new CapabilityToken<Rank>() {});

    private Rank rank = null;
    private final LazyOptional<Rank> optional = LazyOptional.of(this::createLuck);

    private Rank createLuck() {
        if(this.rank == null){
            this.rank = new Rank();
        }

        return this.rank;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_RANK){
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
