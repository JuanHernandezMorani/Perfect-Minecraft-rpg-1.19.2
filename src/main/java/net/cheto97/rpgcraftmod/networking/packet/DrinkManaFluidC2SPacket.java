package net.cheto97.rpgcraftmod.networking.packet;

import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.providers.ManaProvider;
import net.cheto97.rpgcraftmod.providers.ManaRegenerationProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DrinkManaFluidC2SPacket {
    private static final String MESSAGE_DRINK_MANA_FLUID = "message.rpgcraftmod.drink_mana";
    private static final String MESSAGE_NO_MANA_FLUID_NEAR = "message.rpgcraftmod.no_mana_fluid_near";

    public DrinkManaFluidC2SPacket(){

    }

    public DrinkManaFluidC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(@NotNull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if(player != null){
                ServerLevel level = player.getLevel();

                player.getCapability(ManaRegenerationProvider.ENTITY_MANAREGENERATION).ifPresent(mpRegen ->{
                    if(hasManaWell(player,level,1)){
                        player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                            if(mana.get() < mana.getMax()){
                                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_MANA_FLUID).withStyle(ChatFormatting.BLUE));

                                level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                                mana.add(mpRegen.get()*7);

                                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.get()), player);
                            }else{
                                player.sendSystemMessage(Component.literal("Your mana is full").withStyle(ChatFormatting.AQUA));
                                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.get()), player);
                            }

                        });
                    }else{
                        player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                            player.sendSystemMessage(Component.translatable(MESSAGE_NO_MANA_FLUID_NEAR).withStyle(ChatFormatting.RED));
                            ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.get()), player);
                        });
                    }
                });


            }
        });
        return true;
    }

    private boolean hasManaWell(ServerPlayer player, ServerLevel level,int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(state -> state.is(ModBlocks.LIQUID_MANA_BLOCK.get())).toArray().length > 0;
    }

}
