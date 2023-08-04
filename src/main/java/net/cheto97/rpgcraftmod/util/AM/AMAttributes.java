package net.cheto97.rpgcraftmod.util.AM;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMAttributes {
    DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, RpgcraftMod.MOD_ID);

    RegistryObject<Attribute> MAX_MANA      = registerRanged("max_mana", 0d, 0d, Short.MAX_VALUE, true);
    RegistryObject<Attribute> MAX_BURNOUT   = registerRanged("max_burnout", 0d, 0d, Short.MAX_VALUE, true);
    RegistryObject<Attribute> MANA_REGEN    = registerRanged("mana_regen", 0.1d, 0d, Short.MAX_VALUE, false);
    RegistryObject<Attribute> BURNOUT_REGEN = registerRanged("burnout_regen", 0.2d, 0d, Short.MAX_VALUE, false);
    RegistryObject<Attribute> MAGIC_VISION  = registerBool("magic_vision");
    RegistryObject<Attribute> SCALE         = registerRanged("scale", 1d, 0.01d, 100d, true);

    private static RegistryObject<Attribute> registerRanged(String id, double defaultValue, double minValue, double maxValue, boolean syncable) {
        String key = Util.makeDescriptionId("attribute", new ResourceLocation(RpgcraftMod.MOD_ID, id));
        assert ATTRIBUTES != null;
        return ATTRIBUTES.register(id, () -> new RangedAttribute(key, defaultValue, minValue, maxValue).setSyncable(syncable));
    }

    private static RegistryObject<Attribute> registerBool(String id) {
        String key = Util.makeDescriptionId("attribute", new ResourceLocation(RpgcraftMod.MOD_ID, id));
        assert ATTRIBUTES != null;
        return ATTRIBUTES.register(id, () -> new BooleanAttribute(key, false).setSyncable(true));
    }

    class BooleanAttribute extends RangedAttribute {
        public BooleanAttribute(String id, boolean defaultValue) {
            super(id, defaultValue ? 1 : 0, 0, 1);
        }

        @Override
        public double sanitizeValue(double value) {
            return ((int) value) > 0 ? 1 : 0;
        }
    }

    @Internal
    static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
