package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Mana;
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

public class ManaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Mana> ENTITY_MANA = CapabilityManager.get(new CapabilityToken<Mana>() {});

    private LivingEntity entity;

    public ManaProvider(LivingEntity entity){
        this.entity = entity;
    }
    private Mana mana = null;
    private final LazyOptional<Mana> optional = LazyOptional.of(this::createMana);

    private Mana createMana() {
        if(this.mana == null && entity != null){
            createMana(entity);
        }

        return this.mana;
    }

    private Mana createMana(LivingEntity entity) {
        if(this.mana == null){
            this.mana = new Mana(DoubleGenerator("Mana",entity));
        }

        return this.mana;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_MANA){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createMana(entity).saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createMana(entity).loadNBTData(nbt);
    }
}