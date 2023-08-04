package net.cheto97.rpgcraftmod.util.SN;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

@SuppressWarnings("unused")
public interface IPacket {
    /**
     * @return The id of the packet.
     */
    ResourceLocation id();

    /**
     * @param buf the {@link FriendlyByteBuf} to put the information into
     */
    void serialize(FriendlyByteBuf buf);

    /**
     * @param buf the {@link FriendlyByteBuf} containing the information
     */
    default void deserialize(FriendlyByteBuf buf) {throw new NotImplementedException();}

    /**
     * @param id  the {@link ResourceLocation id} of the packet
     * @param buf the {@link FriendlyByteBuf} containing the information
     */
    default void deserialize(ResourceLocation id, FriendlyByteBuf buf) {this.deserialize(buf);}

    /**
     * Handle the received message in this method
     * the data should already be deserialized when this is called
     *
     * @param ctx the {@link NetworkEvent.Context} of the received message
     */
    void handle(NetworkEvent.Context ctx);

    /**
     * Internal version of {@link IPacket#handle} Override when you want to return something else than true
     *
     * @param ctx the {@link NetworkEvent.Context} of the received message
     * @return whether the packet was handled or not
     */
    default boolean handle_(NetworkEvent.Context ctx) {
        this.handle(ctx);
        return true;
    }

    /**
     * Exception thrown when a method is not implemented.
     */
    class NotImplementedException extends RuntimeException {}
}