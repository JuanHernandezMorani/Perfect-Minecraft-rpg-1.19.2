package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Defense;
import net.cheto97.rpgcraftmod.customstats.Intelligence;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.cheto97.rpgcraftmod.util.EntityDataProviderDefine.DoubleGenerator;

public class IntelligenceProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Intelligence> ENTITY_INTELLIGENCE = CapabilityManager.get(new CapabilityToken<Intelligence>() {});

    private Intelligence intelligence = null;
    private final LazyOptional<Intelligence> optional = LazyOptional.of(this::createIntelligence);

    private Intelligence createIntelligence(){
        if(this.intelligence == null){
            this.intelligence = new Intelligence();
        }
        return this.intelligence;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_INTELLIGENCE){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createIntelligence().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createIntelligence().loadNBTData(nbt);
    }
}