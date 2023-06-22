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
    private final LivingEntity entity;

    public CommandProvider(LivingEntity entity){
        this.entity = entity;
    }
    
    public static Capability<Command> ENTITY_COMMAND = CapabilityManager.get(new CapabilityToken<Command>() {});

    private Command command = null;
    private final LazyOptional<Command> optional = LazyOptional.of(this::createCommand);

    private Command createCommand() {
        if(this.command == null){
            createCommand(entity);
        }

        return this.command;
    }
    private Command createCommand(LivingEntity entity) {
        if(this.command == null && entity != null){
            this.command = new Command(DoubleGenerator("Command",entity));
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
        createCommand(entity).saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCommand(entity).loadNBTData(nbt);
    }
}
