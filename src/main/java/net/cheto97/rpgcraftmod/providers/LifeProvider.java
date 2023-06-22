package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Life;
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

public class LifeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Life> ENTITY_LIFE = CapabilityManager.get(new CapabilityToken<Life>() {});
    private final LivingEntity entity;
public LifeProvider(LivingEntity entity){
    this.entity = entity;
}
    private Life life = null;
    private final LazyOptional<Life> optional = LazyOptional.of(this::createLife);

    private Life createLife() {
        if(this.life == null && entity != null){
            createLife(entity);
        }

        return this.life;
    }

    private Life createLife(LivingEntity entity) {
        if(this.life == null){
            this.life = new Life(DoubleGenerator("Life",entity));
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
        createLife(entity).saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createLife(entity).loadNBTData(nbt);
    }
}
