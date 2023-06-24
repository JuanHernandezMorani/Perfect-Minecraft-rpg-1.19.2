package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.ManaRegeneration;
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

public class ManaRegenerationProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ManaRegeneration> ENTITY_MANAREGENERATION = CapabilityManager.get(new CapabilityToken<ManaRegeneration>() {});
    private ManaRegeneration manaregeneration = null;

    private final LazyOptional<ManaRegeneration> optional = LazyOptional.of(this::createManaRegeneration);

    private ManaRegeneration createManaRegeneration(){
        if(this.manaregeneration == null){
            this.manaregeneration = new ManaRegeneration();
        }

        return this.manaregeneration;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side){
        if(cap == ENTITY_MANAREGENERATION){
            return optional.cast();
        }
        return LazyOptional.empty();
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createManaRegeneration().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createManaRegeneration().loadNBTData(nbt);
    }



}
