package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.MagicDefense;
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

public class MagicDefenseProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<MagicDefense> ENTITY_MAGIC_DEFENSE = CapabilityManager.get(new CapabilityToken<MagicDefense>() {});
    private  MagicDefense magicDefense = null;
    private final LazyOptional< MagicDefense> optional = LazyOptional.of(this::createMagicDefense);
    private MagicDefense createMagicDefense() {
        if(this.magicDefense == null){
            this.magicDefense = new  MagicDefense();
        }
        return this.magicDefense;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_MAGIC_DEFENSE){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createMagicDefense().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createMagicDefense().loadNBTData(nbt);
    }
}