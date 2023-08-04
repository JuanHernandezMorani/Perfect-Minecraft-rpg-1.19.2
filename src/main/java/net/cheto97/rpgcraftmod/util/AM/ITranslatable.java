package net.cheto97.rpgcraftmod.util.AM;

import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface ITranslatable {
    default ResourceLocation getId(RegistryAccess access) {
        return getId();
    }
    default ResourceLocation getId() {
        return getId(RegistryAccess.BUILTIN.get());
    }
    String getType();
    default String getTranslationKey(RegistryAccess access) {
        return Util.makeDescriptionId(getType(), getId(access));
    }
    default String getTranslationKey() {
        return Util.makeDescriptionId(getType(), getId());
    }
    default Component getDisplayName(RegistryAccess access) {
        return Component.translatable(getTranslationKey(access));
    }
    default Component getDisplayName() {
        return Component.translatable(getTranslationKey());
    }
    interface WithDescription extends ITranslatable {
        @Override
        default Component getDisplayName(RegistryAccess access) {
            return Component.translatable(getNameTranslationKey(access));
        }
        @Override
        default Component getDisplayName() {
            return Component.translatable(getNameTranslationKey());
        }
        default Component getDescription(RegistryAccess access) {
            return Component.translatable(getDescriptionTranslationKey(access));
        }
        default Component getDescription() {
            return Component.translatable(getDescriptionTranslationKey());
        }
        default String getNameTranslationKey(RegistryAccess access) {
            return ITranslatable.super.getTranslationKey(access) + ".name";
        }
        default String getNameTranslationKey() {
            return ITranslatable.super.getTranslationKey() + ".name";
        }
        default String getDescriptionTranslationKey(RegistryAccess access) {
            return ITranslatable.super.getTranslationKey(access) + ".description";
        }
        default String getDescriptionTranslationKey() {
            return ITranslatable.super.getTranslationKey() + ".description";
        }
    }
}