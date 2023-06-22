package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Agility;
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

public class AgilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Agility> ENTITY_AGILITY = CapabilityManager.get(new CapabilityToken<Agility>() {});
    private final LivingEntity entity;

    public AgilityProvider(LivingEntity entity){
        this.entity = entity;
    }

    private Agility agility = null;
    private final LazyOptional<Agility> optional = LazyOptional.of(this::createAgility);

    private Agility createAgility() {
        if(this.agility == null && entity != null){
            createAgility(entity);
        }

        return this.agility;
    }

    private Agility createAgility(LivingEntity entity) {
        if(this.agility == null){
            this.agility = new Agility(DoubleGenerator("Agility",entity));
        }

        return this.agility;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_AGILITY){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createAgility(entity).saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createAgility(entity).loadNBTData(nbt);
    }
}
