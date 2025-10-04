package com.minecraft.tools.refactor.analysis;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Rich analysis of the Forge project used to drive planning decisions.
 */
public final class ProjectAnalysis {

    private final List<Path> sourceRoots;
    private final List<Path> resourceRoots;
    private final Set<Path> modEntryClasses;
    private final Set<Path> eventHandlerClasses;
    private final List<String> warnings;
    private final boolean multiModule;
    private final ProjectMetadata metadata;

    public ProjectAnalysis(List<Path> sourceRoots,
                           List<Path> resourceRoots,
                           Set<Path> modEntryClasses,
                           Set<Path> eventHandlerClasses,
                           List<String> warnings,
                           boolean multiModule,
                           ProjectMetadata metadata) {
        this.sourceRoots = List.copyOf(sourceRoots);
        this.resourceRoots = List.copyOf(resourceRoots);
        this.modEntryClasses = Set.copyOf(modEntryClasses);
        this.eventHandlerClasses = Set.copyOf(eventHandlerClasses);
        this.warnings = List.copyOf(warnings);
        this.multiModule = multiModule;
        this.metadata = metadata;
    }

    public List<Path> sourceRoots() {
        return Collections.unmodifiableList(sourceRoots);
    }

    public List<Path> resourceRoots() {
        return Collections.unmodifiableList(resourceRoots);
    }

    public Set<Path> modEntryClasses() {
        return Collections.unmodifiableSet(modEntryClasses);
    }

    public Set<Path> eventHandlerClasses() {
        return Collections.unmodifiableSet(eventHandlerClasses);
    }

    public List<String> warnings() {
        return Collections.unmodifiableList(warnings);
    }

    public boolean multiModule() {
        return multiModule;
    }

    public ProjectMetadata metadata() {
        return metadata;
    }
}
