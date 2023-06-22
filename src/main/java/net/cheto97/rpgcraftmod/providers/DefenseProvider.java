package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Defense;
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

public class DefenseProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    
    public static Capability<Defense> ENTITY_DEFENSE = CapabilityManager.get(new CapabilityToken<Defense>() {});
    private final LivingEntity entity;
    private Defense defense = null;
    public DefenseProvider(LivingEntity entity){
        this.entity = entity;
    }
    private final LazyOptional<Defense> optional = LazyOptional.of(this::createDefense);

    private Defense createDefense() {
        if(this.defense == null && entity != null){
            createDefense(entity);
        }

        return this.defense;
    }

    private Defense createDefense(LivingEntity entity) {
        if(this.defense == null){
            this.defense = new Defense(DoubleGenerator("Defense",entity));
        }

        return this.defense;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_DEFENSE){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createDefense(entity).saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createDefense(entity).loadNBTData(nbt);
    }
}