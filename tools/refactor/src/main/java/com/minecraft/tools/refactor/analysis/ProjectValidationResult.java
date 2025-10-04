package com.minecraft.tools.refactor.analysis;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Outcome of validating a Minecraft Forge project structure.
 */
public final class ProjectValidationResult {

    private final Path projectRoot;
    private final boolean forgeDependencyDeclared;
    private final boolean modsTomlPresent;
    private final boolean multiModule;
    private final List<Path> moduleRoots;
    private final List<String> warnings;

    public ProjectValidationResult(Path projectRoot,
                                   boolean forgeDependencyDeclared,
                                   boolean modsTomlPresent,
                                   boolean multiModule,
                                   List<Path> moduleRoots,
                                   List<String> warnings) {
        this.projectRoot = projectRoot;
        this.forgeDependencyDeclared = forgeDependencyDeclared;
        this.modsTomlPresent = modsTomlPresent;
        this.multiModule = multiModule;
        this.moduleRoots = List.copyOf(moduleRoots);
        this.warnings = List.copyOf(warnings);
    }

    public Path projectRoot() {
        return projectRoot;
    }

    public boolean forgeDependencyDeclared() {
        return forgeDependencyDeclared;
    }

    public boolean modsTomlPresent() {
        return modsTomlPresent;
    }

    public boolean multiModule() {
        return multiModule;
    }

    public List<Path> moduleRoots() {
        return Collections.unmodifiableList(moduleRoots);
    }

    public List<String> warnings() {
        return Collections.unmodifiableList(warnings);
    }
}
