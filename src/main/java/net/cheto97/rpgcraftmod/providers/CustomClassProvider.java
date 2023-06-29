package net.cheto97.rpgcraftmod.providers;


import net.cheto97.rpgcraftmod.modsystem.CustomClass;
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

public class CustomClassProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<CustomClass> PLAYER_CLASS = CapabilityManager.get(new CapabilityToken<CustomClass>() {});
    private CustomClass customClass = null;
    private final LazyOptional<CustomClass> optional = LazyOptional.of(this::createCustomClass);
    private CustomClass createCustomClass() {
        if(this.customClass == null){
            this.customClass = new CustomClass();
        }

        return this.customClass;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_CLASS){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCustomClass().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCustomClass().loadNBTData(nbt);
    }
}
