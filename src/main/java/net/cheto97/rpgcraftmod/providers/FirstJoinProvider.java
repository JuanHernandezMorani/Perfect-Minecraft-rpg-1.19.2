package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.modsystem.FirstJoin;
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

public class FirstJoinProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<FirstJoin> ENTITY_FIRST_JOIN = CapabilityManager.get(new CapabilityToken<FirstJoin>() {});

    private FirstJoin join = null;
    private final LazyOptional<FirstJoin> optional = LazyOptional.of(this::createJoin);

    private FirstJoin createJoin() {
        if(this.join == null){
            this.join = new FirstJoin();
        }

        return this.join;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_FIRST_JOIN){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createJoin().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createJoin().loadNBTData(nbt);
    }
}
