package com.minecraft.tools.refactor.analysis;

import java.util.Locale;
import java.util.Set;

/**
 * Central registry of Forge related annotations to ensure consistent
 * detection across validators, analysis and planning stages.
 */
public final class ForgeAnnotationCatalog {

    private static final Set<String> MOD_ANNOTATIONS = Set.of(
            "mod",
            "net.minecraftforge.fml.common.mod",
            "net.minecraftforge.fml.common.mod.eventhandler"
    );

    private static final Set<String> EVENT_CLASS_ANNOTATIONS = Set.of(
            "eventbussubscriber",
            "net.minecraftforge.fml.common.eventhandler.eventbussubscriber",
            "mod.eventbussubscriber"
    );

    private static final Set<String> EVENT_METHOD_ANNOTATIONS = Set.of(
            "subscribeevent",
            "eventhandler",
            "mod.eventhandler"
    );

    private static final Set<String> LIFECYCLE_METHODS = Set.of(
            "setup",
            "clientsetup",
            "serversetup"
    );

    private ForgeAnnotationCatalog() {
    }

    public static boolean isModAnnotation(String name) {
        String lower = lower(name);
        return MOD_ANNOTATIONS.contains(lower)
                || MOD_ANNOTATIONS.contains(simple(lower))
                || lower.endsWith(".mod");
    }

    public static boolean isEventClassAnnotation(String name) {
        String lower = lower(name);
        String simple = simple(lower);
        return EVENT_CLASS_ANNOTATIONS.contains(lower)
                || EVENT_CLASS_ANNOTATIONS.contains(simple)
                || lower.contains("eventbus")
                || lower.contains("eventsubscriber");
    }

    public static boolean isEventMethodAnnotation(String name) {
        String lower = lower(name);
        String simple = simple(lower);
        return EVENT_METHOD_ANNOTATIONS.contains(lower)
                || EVENT_METHOD_ANNOTATIONS.contains(simple)
                || lower.contains("subscribeevent")
                || lower.contains("eventhandler")
                || simple.endsWith("event");
    }

    public static boolean isLifecycleMethod(String methodName) {
        return LIFECYCLE_METHODS.contains(methodName.toLowerCase(Locale.ROOT));
    }

    public static String canonicalName(String name) {
        return simple(lower(name));
    }

    private static String lower(String name) {
        return name.trim().toLowerCase(Locale.ROOT);
    }

    private static String simple(String lowerName) {
        int lastDot = lowerName.lastIndexOf('.') + 1;
        return lowerName.substring(lastDot);
    }
}
