package net.cheto97.rpgcraftmod.networking.packet.S2C;

import net.cheto97.rpgcraftmod.block.entity.GemInfusingStationBlockEntity;
import net.cheto97.rpgcraftmod.block.entity.WizardTableBlockEntity;
import net.cheto97.rpgcraftmod.menu.GemInfusingStationMenu;
import net.cheto97.rpgcraftmod.menu.WizardTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidSyncPacket {
    private final FluidStack fluidStack;
    private final BlockPos pos;

    public FluidSyncPacket(FluidStack fluidStack, BlockPos pos) {
        this.fluidStack = fluidStack;
        this.pos = pos;
    }

    public FluidSyncPacket(FriendlyByteBuf buf) {
        this.fluidStack = buf.readFluidStack();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFluidStack(fluidStack);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof GemInfusingStationBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof GemInfusingStationMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    menu.setFluid(this.fluidStack);
                }
            }
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof WizardTableBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);

                if(Minecraft.getInstance().player.containerMenu instanceof WizardTableMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                        menu.setFluid(this.fluidStack);
                }
            }
        });
        return true;
    }
}
