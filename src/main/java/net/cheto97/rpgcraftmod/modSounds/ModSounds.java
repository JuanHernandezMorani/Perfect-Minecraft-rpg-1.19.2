package net.cheto97.rpgcraftmod.modSounds;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
   /* ** public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RpgcraftMod.MOD_ID);

    public static final RegistryObject<SoundEvent> LEVEL_UP_SOUND = registerSoundEvent("level_up");

    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_BREAK = registerSoundEvent("animated_block_break");
    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_WALK = registerSoundEvent("animated_block_walk");
    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_PLACE = registerSoundEvent("animated_block_place");
    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_HIT = registerSoundEvent("animated_block_hit");

    public static final RegistryObject<SoundEvent> MANA_DRINK = registerSoundEvent("mana_drink");

    public static final ForgeSoundType ANIMATED_BLOCK_SOUNDS = new ForgeSoundType(1f, 1f,
            ModSounds.ANIMATED_BLOCK_BREAK, ModSounds.ANIMATED_BLOCK_WALK, ModSounds.ANIMATED_BLOCK_PLACE,
            ModSounds.ANIMATED_BLOCK_HIT, ModSounds.ANIMATED_BLOCK_WALK);

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(RpgcraftMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    */
}
