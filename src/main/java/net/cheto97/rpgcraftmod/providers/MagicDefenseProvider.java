package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.MagicDefense;
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

public class MagicDefenseProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final LivingEntity entity;
   public MagicDefenseProvider(LivingEntity entity){
       this.entity = entity;
   }
    
    public static Capability<MagicDefense> ENTITY_MAGIC_DEFENSE = CapabilityManager.get(new CapabilityToken<MagicDefense>() {});
    private  MagicDefense magicDefense = null;
    private final LazyOptional< MagicDefense> optional = LazyOptional.of(this::createMagicDefense);
    private MagicDefense createMagicDefense(){
        if(this.magicDefense == null) {
            createMagicDefense(entity);
        }
        return this.magicDefense;
    }
    private MagicDefense createMagicDefense(LivingEntity entity) {
        if(this.magicDefense == null && entity != null){
            this.magicDefense = new  MagicDefense(DoubleGenerator("MagicDefense",entity));
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
        createMagicDefense(entity).saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createMagicDefense(entity).loadNBTData(nbt);
    }
}