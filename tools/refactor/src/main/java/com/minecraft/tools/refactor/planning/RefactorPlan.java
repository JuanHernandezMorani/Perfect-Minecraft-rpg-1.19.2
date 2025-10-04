package com.minecraft.tools.refactor.planning;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates the plan that will be executed by the refactor engine for a file.
 */
public final class RefactorPlan {

    private final Path file;
    private final List<String> nestedClassesToExtract;
    private final Map<String, List<String>> methodGroups;
    private final List<String> notes;
    private final boolean modEntryClass;
    private final boolean eventHandlerClass;
    private final PlanStatus status;

    public RefactorPlan(Path file,
                        List<String> nestedClassesToExtract,
                        Map<String, List<String>> methodGroups,
                        List<String> notes,
                        boolean modEntryClass,
                        boolean eventHandlerClass,
                        PlanStatus status) {
        this.file = file;
        this.nestedClassesToExtract = List.copyOf(nestedClassesToExtract);
        Map<String, List<String>> ordered = new LinkedHashMap<>();
        methodGroups.forEach((key, value) -> ordered.put(key, List.copyOf(value)));
        this.methodGroups = Collections.unmodifiableMap(ordered);
        this.notes = List.copyOf(notes);
        this.modEntryClass = modEntryClass;
        this.eventHandlerClass = eventHandlerClass;
        this.status = status;
    }

    public Path file() {
        return file;
    }

    public List<String> nestedClassesToExtract() {
        return Collections.unmodifiableList(nestedClassesToExtract);
    }

    public Map<String, List<String>> methodGroups() {
        return methodGroups;
    }

    public List<String> notes() {
        return Collections.unmodifiableList(notes);
    }

    public boolean hasWork() {
        return !nestedClassesToExtract.isEmpty() || methodGroups.values().stream().anyMatch(list -> !list.isEmpty());
    }

    public boolean isModEntryClass() {
        return modEntryClass;
    }

    public boolean isEventHandlerClass() {
        return eventHandlerClass;
    }

    public PlanStatus status() {
        return status;
    }
}
