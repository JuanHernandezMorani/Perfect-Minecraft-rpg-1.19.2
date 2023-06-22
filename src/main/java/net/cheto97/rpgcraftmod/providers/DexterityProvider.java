package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Dexterity;
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

public class DexterityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    
    public static Capability<Dexterity> ENTITY_DEXTERITY = CapabilityManager.get(new CapabilityToken<Dexterity>() {});
    private final LivingEntity entity;
    private Dexterity dexterity = null;
    private final LazyOptional<Dexterity> optional = LazyOptional.of(this::createDexterity);
    public DexterityProvider(LivingEntity entity){
        this.entity = entity;
    }

    private Dexterity createDexterity() {
        if(this.dexterity == null && entity != null){
            createDexterity(entity);
        }
        return this.dexterity;
    }

    private Dexterity createDexterity(LivingEntity entity) {
        if(this.dexterity == null){
            this.dexterity = new Dexterity(DoubleGenerator("Dexterity",entity));
        }

        return this.dexterity;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_DEXTERITY){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createDexterity(entity).saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createDexterity(entity).loadNBTData(nbt);
    }
}
