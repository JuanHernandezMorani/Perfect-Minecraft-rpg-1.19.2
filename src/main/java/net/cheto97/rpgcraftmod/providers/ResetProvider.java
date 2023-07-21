package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.modsystem.Reset;
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

public class ResetProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Reset> ENTITY_RESET = CapabilityManager.get(new CapabilityToken<Reset>() {});
    
    private Reset reset = null;
    
    private final LazyOptional<Reset> optional = LazyOptional.of(this::createReset);
    
    private Reset createReset(){
        if(this.reset == null){
            this.reset = new Reset();
        }
        return this.reset;
    }
    
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side){
        if(cap == ENTITY_RESET){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createReset().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createReset().loadNBTData(nbt);
    }
    
}
