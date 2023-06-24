package net.cheto97.rpgcraftmod.providers;

import net.cheto97.rpgcraftmod.customstats.Command;
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

public class CommandProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    
    public static Capability<Command> ENTITY_COMMAND = CapabilityManager.get(new CapabilityToken<Command>() {});

    private Command command = null;
    private final LazyOptional<Command> optional = LazyOptional.of(this::createCommand);
    private Command createCommand() {
        if(this.command == null){
            this.command = new Command();
        }

        return this.command;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_COMMAND){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCommand().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCommand().loadNBTData(nbt);
    }
}
