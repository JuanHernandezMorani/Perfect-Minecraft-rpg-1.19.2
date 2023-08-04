package net.cheto97.rpgcraftmod.util.OV;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static top.theillusivec4.curios.api.CuriosApi.MODID;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RpgcraftMod.MOD_ID);

    public static final RegistryObject<SoundEvent> WILDFIRE_AMBIENT = SOUNDS.register("wildfire_ambient",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "wildfire_ambient")));
    public static final RegistryObject<SoundEvent> WILDFIRE_HURT = SOUNDS.register("wildfire_hurt",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "wildfire_hurt")));
    public static final RegistryObject<SoundEvent> WILDFIRE_DEATH = SOUNDS.register("wildfire_death",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "wildfire_death")));
    public static final RegistryObject<SoundEvent> WILDFIRE_BURN = SOUNDS.register("wildfire_burn",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "wildfire_burn")));
    public static final RegistryObject<SoundEvent> WILDFIRE_SHOOT = SOUNDS.register("wildfire_shoot",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "wildfire_shoot")));

    public static final RegistryObject<SoundEvent> GLUTTON_AMBIENT = SOUNDS.register("glutton_ambient",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "glutton_ambient")));
    public static final RegistryObject<SoundEvent> GLUTTON_HURT = SOUNDS.register("glutton_hurt",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "glutton_hurt")));
    public static final RegistryObject<SoundEvent> GLUTTON_DEATH = SOUNDS.register("glutton_death",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "glutton_death")));
    public static final RegistryObject<SoundEvent> GLUTTON_BITE = SOUNDS.register("glutton_bite",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "glutton_bite")));
    public static final RegistryObject<SoundEvent> GLUTTON_SPIT = SOUNDS.register("glutton_spit",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "glutton_spit")));
    public static final RegistryObject<SoundEvent> GLUTTON_EAT = SOUNDS.register("glutton_eat",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "glutton_eat")));
    public static final RegistryObject<SoundEvent> GLUTTON_DIG = SOUNDS.register("glutton_dig",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "glutton_dig")));
    public static final RegistryObject<SoundEvent> GLUTTON_DIG_SAND = SOUNDS.register("glutton_dig_sand",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "glutton_dig_sand")));

    public static final RegistryObject<SoundEvent> BARNACLE_AMBIENT = SOUNDS.register("barnacle_ambient",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "barnacle_ambient")));
    public static final RegistryObject<SoundEvent> BARNACLE_HURT = SOUNDS.register("barnacle_hurt",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "barnacle_hurt")));
    public static final RegistryObject<SoundEvent> BARNACLE_DEATH = SOUNDS.register("barnacle_death",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "barnacle_death")));
    public static final RegistryObject<SoundEvent> BARNACLE_FLOP = SOUNDS.register("barnacle_flop",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "barnacle_flop")));

    public static final RegistryObject<SoundEvent> MEERKAT_AMBIENT = SOUNDS.register("meerkat_ambient",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "meerkat_ambient")));
    public static final RegistryObject<SoundEvent> MEERKAT_HURT = SOUNDS.register("meerkat_hurt",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "meerkat_hurt")));
    public static final RegistryObject<SoundEvent> MEERKAT_DEATH = SOUNDS.register("meerkat_death",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "meerkat_death")));

    public static final RegistryObject<SoundEvent> OSTRICH_AMBIENT = SOUNDS.register("ostrich_ambient",
            () -> new SoundEvent(new ResourceLocation(RpgcraftMod.MOD_ID, "ostrich_ambient")));

    public static final RegistryObject<SoundEvent> BESTIARY_PAGE = createSoundEvent("bestiary_page");

    public static final RegistryObject<SoundEvent> EGG_HATCH = createSoundEvent("egg_hatch");

    public static final RegistryObject<SoundEvent> FIREDRAGON_BREATH = createSoundEvent("firedragon_breath");

    public static final RegistryObject<SoundEvent> ICEDRAGON_BREATH = createSoundEvent("icedragon_breath");

    public static final RegistryObject<SoundEvent> FIREDRAGON_CHILD_IDLE = createSoundEvent("firedragon_child_idle");

    public static final RegistryObject<SoundEvent> FIREDRAGON_CHILD_HURT = createSoundEvent("firedragon_child_hurt");

    public static final RegistryObject<SoundEvent> FIREDRAGON_CHILD_DEATH = createSoundEvent("firedragon_child_death");

    public static final RegistryObject<SoundEvent> FIREDRAGON_CHILD_ROAR = createSoundEvent("firedragon_child_roar");

    public static final RegistryObject<SoundEvent> FIREDRAGON_TEEN_ROAR = createSoundEvent("firedragon_teen_roar");

    public static final RegistryObject<SoundEvent> FIREDRAGON_TEEN_IDLE = createSoundEvent("firedragon_teen_idle");

    public static final RegistryObject<SoundEvent> FIREDRAGON_TEEN_DEATH = createSoundEvent("firedragon_teen_death");

    public static final RegistryObject<SoundEvent> FIREDRAGON_TEEN_HURT = createSoundEvent("firedragon_teen_hurt");

    public static final RegistryObject<SoundEvent> FIREDRAGON_ADULT_ROAR = createSoundEvent("firedragon_adult_roar");

    public static final RegistryObject<SoundEvent> FIREDRAGON_ADULT_IDLE = createSoundEvent("firedragon_adult_idle");

    public static final RegistryObject<SoundEvent> FIREDRAGON_ADULT_DEATH = createSoundEvent("firedragon_adult_death");

    public static final RegistryObject<SoundEvent> FIREDRAGON_ADULT_HURT = createSoundEvent("firedragon_adult_hurt");

    public static final RegistryObject<SoundEvent> ICEDRAGON_CHILD_IDLE = createSoundEvent("icedragon_child_idle");

    public static final RegistryObject<SoundEvent> ICEDRAGON_CHILD_HURT = createSoundEvent("icedragon_child_hurt");

    public static final RegistryObject<SoundEvent> ICEDRAGON_CHILD_DEATH = createSoundEvent("icedragon_child_death");

    public static final RegistryObject<SoundEvent> ICEDRAGON_CHILD_ROAR = createSoundEvent("icedragon_child_roar");

    public static final RegistryObject<SoundEvent> ICEDRAGON_TEEN_ROAR = createSoundEvent("icedragon_teen_roar");

    public static final RegistryObject<SoundEvent> ICEDRAGON_TEEN_IDLE = createSoundEvent("icedragon_teen_idle");

    public static final RegistryObject<SoundEvent> ICEDRAGON_TEEN_DEATH = createSoundEvent("icedragon_teen_death");

    public static final RegistryObject<SoundEvent> ICEDRAGON_TEEN_HURT = createSoundEvent("icedragon_teen_hurt");

    public static final RegistryObject<SoundEvent> ICEDRAGON_ADULT_ROAR = createSoundEvent("icedragon_adult_roar");

    public static final RegistryObject<SoundEvent> ICEDRAGON_ADULT_IDLE = createSoundEvent("icedragon_adult_idle");

    public static final RegistryObject<SoundEvent> ICEDRAGON_ADULT_DEATH = createSoundEvent("icedragon_adult_death");

    public static final RegistryObject<SoundEvent> ICEDRAGON_ADULT_HURT = createSoundEvent("icedragon_adult_hurt");

    public static final RegistryObject<SoundEvent> DRAGONFLUTE = createSoundEvent("dragonflute");

    public static final RegistryObject<SoundEvent> HIPPOGRYPH_IDLE = createSoundEvent("hippogryph_idle");

    public static final RegistryObject<SoundEvent> HIPPOGRYPH_HURT = createSoundEvent("hippogryph_hurt");

    public static final RegistryObject<SoundEvent> HIPPOGRYPH_DIE = createSoundEvent("hippogryph_die");

    public static final RegistryObject<SoundEvent> GORGON_IDLE = createSoundEvent("gorgon_idle");

    public static final RegistryObject<SoundEvent> GORGON_HURT = createSoundEvent("gorgon_hurt");

    public static final RegistryObject<SoundEvent> GORGON_DIE = createSoundEvent("gorgon_die");

    public static final RegistryObject<SoundEvent> GORGON_ATTACK = createSoundEvent("gorgon_attack");

    public static final RegistryObject<SoundEvent> GORGON_PETRIFY = createSoundEvent("gorgon_petrify");

    public static final RegistryObject<SoundEvent> TURN_STONE = createSoundEvent("turn_stone");

    public static final RegistryObject<SoundEvent> PIXIE_IDLE = createSoundEvent("pixie_idle");

    public static final RegistryObject<SoundEvent> PIXIE_HURT = createSoundEvent("pixie_hurt");

    public static final RegistryObject<SoundEvent> PIXIE_DIE = createSoundEvent("pixie_die");

    public static final RegistryObject<SoundEvent> PIXIE_TAUNT = createSoundEvent("pixie_taunt");

    public static final RegistryObject<SoundEvent> GOLD_PILE_STEP = createSoundEvent("gold_pile_step");

    public static final RegistryObject<SoundEvent> GOLD_PILE_BREAK = createSoundEvent("gold_pile_break");

    public static final RegistryObject<SoundEvent> DRAGON_FLIGHT = createSoundEvent("dragon_flight");

    public static final RegistryObject<SoundEvent> CYCLOPS_IDLE = createSoundEvent("cyclops_idle");

    public static final RegistryObject<SoundEvent> CYCLOPS_HURT = createSoundEvent("cyclops_hurt");

    public static final RegistryObject<SoundEvent> CYCLOPS_DIE = createSoundEvent("cyclops_die");

    public static final RegistryObject<SoundEvent> CYCLOPS_BITE = createSoundEvent("cyclops_bite");

    public static final RegistryObject<SoundEvent> CYCLOPS_BLINDED = createSoundEvent("cyclops_blinded");

    public static final RegistryObject<SoundEvent> HIPPOCAMPUS_IDLE = createSoundEvent("hippocampus_idle");

    public static final RegistryObject<SoundEvent> HIPPOCAMPUS_HURT = createSoundEvent("hippocampus_hurt");

    public static final RegistryObject<SoundEvent> HIPPOCAMPUS_DIE = createSoundEvent("hippocampus_die");

    public static final RegistryObject<SoundEvent> DEATHWORM_IDLE = createSoundEvent("deathworm_idle");

    public static final RegistryObject<SoundEvent> DEATHWORM_ATTACK = createSoundEvent("deathworm_attack");

    public static final RegistryObject<SoundEvent> DEATHWORM_HURT = createSoundEvent("deathworm_hurt");

    public static final RegistryObject<SoundEvent> DEATHWORM_DIE = createSoundEvent("deathworm_die");

    public static final RegistryObject<SoundEvent> DEATHWORM_GIANT_IDLE = createSoundEvent("deathworm_giant_idle");

    public static final RegistryObject<SoundEvent> DEATHWORM_GIANT_ATTACK = createSoundEvent("deathworm_giant_attack");

    public static final RegistryObject<SoundEvent> DEATHWORM_GIANT_HURT = createSoundEvent("deathworm_giant_hurt");

    public static final RegistryObject<SoundEvent> DEATHWORM_GIANT_DIE = createSoundEvent("deathworm_giant_die");

    public static final RegistryObject<SoundEvent> NAGA_IDLE = createSoundEvent("naga_idle");

    public static final RegistryObject<SoundEvent> NAGA_ATTACK = createSoundEvent("naga_attack");

    public static final RegistryObject<SoundEvent> NAGA_HURT = createSoundEvent("naga_hurt");

    public static final RegistryObject<SoundEvent> NAGA_DIE = createSoundEvent("naga_die");

    public static final RegistryObject<SoundEvent> MERMAID_IDLE = createSoundEvent("mermaid_idle");

    public static final RegistryObject<SoundEvent> MERMAID_HURT = createSoundEvent("mermaid_hurt");

    public static final RegistryObject<SoundEvent> MERMAID_DIE = createSoundEvent("mermaid_die");

    public static final RegistryObject<SoundEvent> SIREN_SONG = createSoundEvent("siren_song");

    public static final RegistryObject<SoundEvent> TROLL_DIE = createSoundEvent("troll_die");

    public static final RegistryObject<SoundEvent> TROLL_IDLE = createSoundEvent("troll_idle");

    public static final RegistryObject<SoundEvent> TROLL_HURT = createSoundEvent("troll_hurt");

    public static final RegistryObject<SoundEvent> TROLL_ROAR = createSoundEvent("troll_roar");

    public static final RegistryObject<SoundEvent> COCKATRICE_DIE = createSoundEvent("cockatrice_die");

    public static final RegistryObject<SoundEvent> COCKATRICE_IDLE = createSoundEvent("cockatrice_idle");

    public static final RegistryObject<SoundEvent> COCKATRICE_HURT = createSoundEvent("cockatrice_hurt");

    public static final RegistryObject<SoundEvent> COCKATRICE_CRY = createSoundEvent("cockatrice_cry");

    public static final RegistryObject<SoundEvent> STYMPHALIAN_BIRD_DIE = createSoundEvent("stymphalian_bird_die");

    public static final RegistryObject<SoundEvent> STYMPHALIAN_BIRD_IDLE = createSoundEvent("stymphalian_bird_idle");

    public static final RegistryObject<SoundEvent> STYMPHALIAN_BIRD_HURT = createSoundEvent("stymphalian_bird_hurt");

    public static final RegistryObject<SoundEvent> STYMPHALIAN_BIRD_ATTACK = createSoundEvent("stymphalian_bird_attack");

    public static final RegistryObject<SoundEvent> MYRMEX_DIE = createSoundEvent("myrmex_die");

    public static final RegistryObject<SoundEvent> MYRMEX_IDLE = createSoundEvent("myrmex_idle");

    public static final RegistryObject<SoundEvent> MYRMEX_HURT = createSoundEvent("myrmex_hurt");

    public static final RegistryObject<SoundEvent> MYRMEX_WALK = createSoundEvent("myrmex_walk");

    public static final RegistryObject<SoundEvent> MYRMEX_BITE = createSoundEvent("myrmex_bite");

    public static final RegistryObject<SoundEvent> MYRMEX_STING = createSoundEvent("myrmex_sting");

    public static final RegistryObject<SoundEvent> AMPHITHERE_DIE = createSoundEvent("amphithere_die");

    public static final RegistryObject<SoundEvent> AMPHITHERE_IDLE = createSoundEvent("amphithere_idle");

    public static final RegistryObject<SoundEvent> AMPHITHERE_HURT = createSoundEvent("amphithere_hurt");

    public static final RegistryObject<SoundEvent> AMPHITHERE_BITE = createSoundEvent("amphithere_bite");

    public static final RegistryObject<SoundEvent> AMPHITHERE_GUST = createSoundEvent("amphithere_gust");

    public static final RegistryObject<SoundEvent> SEA_SERPENT_DIE = createSoundEvent("sea_serpent_die");

    public static final RegistryObject<SoundEvent> SEA_SERPENT_IDLE = createSoundEvent("sea_serpent_idle");

    public static final RegistryObject<SoundEvent> SEA_SERPENT_HURT = createSoundEvent("sea_serpent_hurt");

    public static final RegistryObject<SoundEvent> SEA_SERPENT_BITE = createSoundEvent("sea_serpent_bite");

    public static final RegistryObject<SoundEvent> SEA_SERPENT_ROAR = createSoundEvent("sea_serpent_roar");

    public static final RegistryObject<SoundEvent> SEA_SERPENT_BREATH = createSoundEvent("sea_serpent_breath");

    public static final RegistryObject<SoundEvent> SEA_SERPENT_SPLASH = createSoundEvent("sea_serpent_splash");

    public static final RegistryObject<SoundEvent> HYDRA_DIE = createSoundEvent("hydra_die");

    public static final RegistryObject<SoundEvent> HYDRA_IDLE = createSoundEvent("hydra_idle");

    public static final RegistryObject<SoundEvent> HYDRA_HURT = createSoundEvent("hydra_hurt");

    public static final RegistryObject<SoundEvent> HYDRA_SPIT = createSoundEvent("hydra_spit");

    public static final RegistryObject<SoundEvent> HYDRA_REGEN_HEAD = createSoundEvent("hydra_regen_head");

    public static final RegistryObject<SoundEvent> PIXIE_WAND = createSoundEvent("pixie_wand");

    public static final RegistryObject<SoundEvent> DREAD_LICH_SUMMON = createSoundEvent("dread_lich_summon");

    public static final RegistryObject<SoundEvent> DREAD_GHOUL_IDLE = createSoundEvent("dread_ghoul_idle");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_CHILD_IDLE = createSoundEvent("lightningdragon_child_idle");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_CHILD_HURT = createSoundEvent("lightningdragon_child_hurt");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_CHILD_DEATH = createSoundEvent("lightningdragon_child_death");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_CHILD_ROAR = createSoundEvent("lightningdragon_child_roar");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_TEEN_ROAR = createSoundEvent("lightningdragon_teen_roar");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_TEEN_IDLE = createSoundEvent("lightningdragon_teen_idle");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_TEEN_DEATH = createSoundEvent("lightningdragon_teen_death");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_TEEN_HURT = createSoundEvent("lightningdragon_teen_hurt");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_ADULT_ROAR = createSoundEvent("lightningdragon_adult_roar");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_ADULT_IDLE = createSoundEvent("lightningdragon_adult_idle");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_ADULT_DEATH = createSoundEvent("lightningdragon_adult_death");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_ADULT_HURT = createSoundEvent("lightningdragon_adult_hurt");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_BREATH = createSoundEvent("lightningdragon_breath");

    public static final RegistryObject<SoundEvent> LIGHTNINGDRAGON_BREATH_CRACKLE = createSoundEvent("lightningdragon_breath_crackle");

    public static final RegistryObject<SoundEvent> GHOST_IDLE = createSoundEvent("ghost_idle");

    public static final RegistryObject<SoundEvent> GHOST_HURT = createSoundEvent("ghost_hurt");

    public static final RegistryObject<SoundEvent> GHOST_DIE = createSoundEvent("ghost_die");

    public static final RegistryObject<SoundEvent> GHOST_ATTACK = createSoundEvent("ghost_attack");

    public static final RegistryObject<SoundEvent> GHOST_JUMPSCARE = createSoundEvent("ghost_jumpscare");

    private static RegistryObject<SoundEvent> createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(MODID, soundName);
        return SOUNDS.register(soundName, () -> new SoundEvent(soundID));
    }
}
