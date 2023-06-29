package net.cheto97.rpgcraftmod.networking.packet.C2S;

import net.cheto97.rpgcraftmod.block.ModBlocks;
import net.cheto97.rpgcraftmod.modsystem.Customlevel;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.S2C.PlayerSyncPacket;
import net.cheto97.rpgcraftmod.providers.CustomLevelProvider;
import net.cheto97.rpgcraftmod.providers.ManaMaxProvider;
import net.cheto97.rpgcraftmod.providers.ManaProvider;
import net.cheto97.rpgcraftmod.providers.ManaRegenerationProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
                    if(hasManaWell(player,level)){
                        player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> player.getCapability(ManaMaxProvider.ENTITY_MANA_MAX).ifPresent(manaMax -> {
                            if(mana.get() < manaMax.get()){
                                level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                                mana.add(mpRegen.get()*20+(player.getCapability(CustomLevelProvider.ENTITY_CUSTOMLEVEL).map(Customlevel::get).orElse(1)*0.0025));

                                ModMessages.sendToPlayer(new PlayerSyncPacket(player),player);
                                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_MANA_FLUID).withStyle(ChatFormatting.BLUE));
                            }else{
                                player.sendSystemMessage(Component.literal("Your mana is full").withStyle(ChatFormatting.AQUA));
                                ModMessages.sendToPlayer(new PlayerSyncPacket(player),player);
                            }

                        }));
                    }else{
                        player.getCapability(ManaProvider.ENTITY_MANA).ifPresent(mana -> {
                            player.sendSystemMessage(Component.translatable(MESSAGE_NO_MANA_FLUID_NEAR).withStyle(ChatFormatting.RED));
                            ModMessages.sendToPlayer(new PlayerSyncPacket(player),player);
                        });
                    }
                });


            }
        });
        return true;
    }

    private boolean hasManaWell(ServerPlayer player, ServerLevel level) {
        return level.getBlockStates(player.getBoundingBox().inflate(1))
                .filter(state -> state.is(ModBlocks.LIQUID_MANA_BLOCK.get())).toArray().length > 0;
    }

}
