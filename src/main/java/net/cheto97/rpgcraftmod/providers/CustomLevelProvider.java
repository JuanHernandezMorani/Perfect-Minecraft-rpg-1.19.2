package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.modsystem.Customlevel;
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

public class CustomLevelProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Customlevel> ENTITY_CUSTOMLEVEL = CapabilityManager.get(new CapabilityToken<Customlevel>() {});

    private Customlevel customlevel = null;
    private final LazyOptional<Customlevel> optional = LazyOptional.of(this::createCustomlevel);

    private Customlevel createCustomlevel() {
        if(this.customlevel == null){
            this.customlevel = new Customlevel();
        }

        return this.customlevel;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_CUSTOMLEVEL){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCustomlevel().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCustomlevel().loadNBTData(nbt);
    }
}
