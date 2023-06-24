package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.util.ExperienceReward;
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

public class ExperienceRewardProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ExperienceReward> ENTITY_EXPERIENCE_REWARD = CapabilityManager.get(new CapabilityToken<ExperienceReward>(){});

    private ExperienceReward experiencereward = null;
    private final LazyOptional<ExperienceReward> optional = LazyOptional.of(this::createExperienceReward);

    private ExperienceReward createExperienceReward(){
        if(this.experiencereward == null){
            this.experiencereward = new ExperienceReward();
        }
        return this.experiencereward;
    }

    @Override
    public @NotNull <T> LazyOptional <T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side){
        if(cap == ENTITY_EXPERIENCE_REWARD){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT(){
        CompoundTag nbt = new CompoundTag();
        createExperienceReward().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt){
        createExperienceReward().loadNBTData(nbt);
    }
}
