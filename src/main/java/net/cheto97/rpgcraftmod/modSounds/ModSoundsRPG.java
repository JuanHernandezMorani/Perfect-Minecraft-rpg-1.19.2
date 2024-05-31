package net.cheto97.rpgcraftmod.modSounds;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundsRPG {
   public static final DeferredRegister<SoundEvent> RPG_SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RpgcraftMod.MOD_ID);

    public static final RegistryObject<SoundEvent> LEVEL_UP_SOUND = registerSoundEvent("level_up");

    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_BREAK = registerSoundEvent("animated_block_break");
    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_WALK = registerSoundEvent("animated_block_walk");
    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_PLACE = registerSoundEvent("animated_block_place");
    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_HIT = registerSoundEvent("animated_block_hit");

    public static final RegistryObject<SoundEvent> MANA_DRINK = registerSoundEvent("mana_drink");

    public static final ForgeSoundType ANIMATED_BLOCK_SOUNDS = new ForgeSoundType(1f, 1f,
            ModSoundsRPG.ANIMATED_BLOCK_BREAK, ModSoundsRPG.ANIMATED_BLOCK_WALK, ModSoundsRPG.ANIMATED_BLOCK_PLACE,
            ModSoundsRPG.ANIMATED_BLOCK_HIT, ModSoundsRPG.ANIMATED_BLOCK_WALK);

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation soundEvent = new ResourceLocation(RpgcraftMod.MOD_ID, "sounds/"+name+".ogg");
        return RPG_SOUND_EVENTS.register(name, () -> new SoundEvent(soundEvent));
    }

    public static void register(IEventBus eventBus) {
        RPG_SOUND_EVENTS.register(eventBus);
    }
}
