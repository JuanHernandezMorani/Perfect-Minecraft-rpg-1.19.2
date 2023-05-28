package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Life;
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

public class LifeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Life> ENTITY_LIFE = CapabilityManager.get(new CapabilityToken<Life>() {});

    private Life life = null;
    private final LazyOptional<Life> optional = LazyOptional.of(this::createLife);

    private Life createLife() {
        if(this.life == null){
            this.life = new Life();
        }

        return this.life;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_LIFE){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createLife().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createLife().loadNBTData(nbt);
    }
}
