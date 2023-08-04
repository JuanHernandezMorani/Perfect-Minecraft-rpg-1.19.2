package net.cheto97.rpgcraftmod.util.SN;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class CodecPacket<T> implements IPacket {
    protected ResourceLocation id;
    protected final T data;

    /**
     * Constructor accepting the data to send.
     *
     * @param data the data to send.
     */
    public CodecPacket(ResourceLocation id, T data) {
        this.id = id;
        this.data = data;
    }

    /**
     * Constructor for deserialization.<br>
     * Subclasses must have this constructor present.
     */
    public CodecPacket(ResourceLocation id, FriendlyByteBuf buf) {
        this.id = id;
        this.data = buf.readWithCodec(this.codec());
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeWithCodec(this.codec(), this.data);
    }

    @Override
    public ResourceLocation id() {
        return this.id;
    }

    /**
     * Implement this method and return the codec to encode and decode the data.
     *
     * @return the codec to encode and decode the data.
     */
    protected abstract Codec<T> codec();
}