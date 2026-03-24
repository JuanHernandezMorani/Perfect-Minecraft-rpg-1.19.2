package net.cheto97.rpgcraftmod.item.prefabs;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.resources.ResourceLocation;

public record WingDefinition(
        String id,
        ResourceLocation geoModel,
        ResourceLocation animationFile,
        ResourceLocation texture,
        FlightTuning flightTuning
) {
    public static WingDefinition of(String id) {
        return new WingDefinition(
                id,
                new ResourceLocation(RpgcraftMod.MOD_ID, "geo/item/wing_curio.geo.json"),
                new ResourceLocation(RpgcraftMod.MOD_ID, "animations/item/wing_curio.animation.json"),
                new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/" + id + ".png"),
                FlightTuning.DEFAULT
        );
    }

    public record FlightTuning(
            double targetHorizontalSpeed,
            double maxHorizontalSpeed,
            double acceleration,
            double verticalControl,
            double drag,
            double boostAcceleration,
            int loopTicks,
            int durabilityTickInterval
    ) {
        public static final FlightTuning DEFAULT = new FlightTuning(
                0.26D,
                0.42D,
                0.085D,
                0.22D,
                0.985D,
                0.02D,
                8,
                40
        );
    }
}
