package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.ManaMax;
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

public class ManaMaxProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ManaMax> ENTITY_MANA_MAX = CapabilityManager.get(new CapabilityToken<ManaMax>() {});
    private ManaMax manaMax = null;
    private final LazyOptional<ManaMax> optional = LazyOptional.of(this::createManaMax);

    private ManaMax createManaMax() {
        if(this.manaMax == null){
            this.manaMax = new ManaMax();
        }

        return this.manaMax;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_MANA_MAX){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createManaMax().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createManaMax().loadNBTData(nbt);
    }
}
