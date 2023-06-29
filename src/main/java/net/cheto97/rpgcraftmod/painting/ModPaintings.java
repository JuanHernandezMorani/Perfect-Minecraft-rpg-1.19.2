package net.cheto97.rpgcraftmod.painting;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, RpgcraftMod.MOD_ID);

    public static final RegistryObject<PaintingVariant> MOD_PLANTS = PAINTING_VARIANTS.register("mod_plants",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> MOD_WASTELANDS = PAINTING_VARIANTS.register("mod_wastelands",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> MOD_AZTEC = PAINTING_VARIANTS.register("mod_aztec",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> MOD_WANDERER = PAINTING_VARIANTS.register("mod_wanderer",
            () -> new PaintingVariant(16,32));

    public static final RegistryObject<PaintingVariant> MOD_SUNSET = PAINTING_VARIANTS.register("mod_sunset",
            () -> new PaintingVariant(32,16));

    public static final RegistryObject<PaintingVariant> MOD_FIGHTERS = PAINTING_VARIANTS.register("mod_fighters",
            () -> new PaintingVariant(32,16));

    public static final RegistryObject<PaintingVariant> MOD_BUST = PAINTING_VARIANTS.register("mod_bust",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> MOD_POINTER = PAINTING_VARIANTS.register("mod_pointer",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_ALONE = PAINTING_VARIANTS.register("mod_alone",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> MOD_ART = PAINTING_VARIANTS.register("mod_art",
            () -> new PaintingVariant(16,32));

    public static final RegistryObject<PaintingVariant> MOD_BATTLE = PAINTING_VARIANTS.register("mod_battle",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_CANUT = PAINTING_VARIANTS.register("mod_canut",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_DEATHKING = PAINTING_VARIANTS.register("mod_deathking",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_DRAGON = PAINTING_VARIANTS.register("mod_dragon",
            () -> new PaintingVariant(64,64));

    public static final RegistryObject<PaintingVariant> MOD_DUNGEONS = PAINTING_VARIANTS.register("mod_dungeons",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_ELEMENT = PAINTING_VARIANTS.register("mod_element",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_GAMING = PAINTING_VARIANTS.register("mod_gaming",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_NEZUKO = PAINTING_VARIANTS.register("mod_nezuko",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_PEACE = PAINTING_VARIANTS.register("mod_peace",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_SAD = PAINTING_VARIANTS.register("mod_sad",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_SIDE = PAINTING_VARIANTS.register("mod_side",
            () -> new PaintingVariant(32,16));

    public static final RegistryObject<PaintingVariant> MOD_SQUID = PAINTING_VARIANTS.register("mod_squid",
            () -> new PaintingVariant(16,32));

    public static final RegistryObject<PaintingVariant> MOD_VANILLA = PAINTING_VARIANTS.register("mod_vanilla",
            () -> new PaintingVariant(16,32));

    public static final RegistryObject<PaintingVariant> MOD_WANTED = PAINTING_VARIANTS.register("mod_wanted",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> MOD_WAVE = PAINTING_VARIANTS.register("mod_wave",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> chilling_isles = PAINTING_VARIANTS.register("chilling_isles",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> da_salt = PAINTING_VARIANTS.register("da_salt",
            () -> new PaintingVariant(32,16));

    public static final RegistryObject<PaintingVariant> glow_like_helly = PAINTING_VARIANTS.register("glow_like_helly",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> glowstone_canyon_painting = PAINTING_VARIANTS.register("glowstone_canyon_painting",
            () -> new PaintingVariant(64,64));

    public static final RegistryObject<PaintingVariant> infernal_back = PAINTING_VARIANTS.register("infernal_back",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> pigs_greed = PAINTING_VARIANTS.register("pigs_greed",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> pineapple_under_the_lava_sea = PAINTING_VARIANTS.register("pineapple_under_the_lava_sea",
            () -> new PaintingVariant(64,64));

    public static final RegistryObject<PaintingVariant> shroom_duality = PAINTING_VARIANTS.register("shroom_duality",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> soul_by_soul = PAINTING_VARIANTS.register("soul_by_soul",
            () -> new PaintingVariant(32,32));

    public static final RegistryObject<PaintingVariant> soul_hole = PAINTING_VARIANTS.register("soul_hole",
            () -> new PaintingVariant(16,16));

    public static final RegistryObject<PaintingVariant> the_fallen_ones = PAINTING_VARIANTS.register("the_fallen_ones",
            () -> new PaintingVariant(64,64));

    public static final RegistryObject<PaintingVariant> voline = PAINTING_VARIANTS.register("voline",
            () -> new PaintingVariant(16,16));

    public static void register(IEventBus eventBus){
        PAINTING_VARIANTS.register(eventBus);
    }
}
