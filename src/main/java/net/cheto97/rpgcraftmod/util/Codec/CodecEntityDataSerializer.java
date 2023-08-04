package net.cheto97.rpgcraftmod.util.Codec;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.function.Consumer;

public record CodecEntityDataSerializer<T>(Codec<T> codec, Consumer<String> errorConsumer) implements EntityDataSerializer<T> {

    public CodecEntityDataSerializer(Codec<T> codec) {
        this(codec, s -> {});
    }

    @Override
    public void write(final FriendlyByteBuf buffer, final T value) {
        buffer.writeNbt((CompoundTag) codec.encodeStart(NbtOps.INSTANCE, value).getOrThrow(false, errorConsumer));
    }

    @Override
    public T read(final FriendlyByteBuf buffer) {
        return codec.decode(NbtOps.INSTANCE, buffer.readNbt()).getOrThrow(false, errorConsumer).getFirst();
    }

    @Override
    public T copy(final T value) {
        return codec.encodeStart(NbtOps.INSTANCE, value).flatMap(tag -> codec.decode(NbtOps.INSTANCE, tag)).getOrThrow(false, errorConsumer).getFirst();
    }
}