package com.minecraft.tools.refactor.analysis;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Detailed metadata derived from analysing a Forge project. The metadata tracks
 * module specific statistics and global annotation usage so that downstream
 * components can make higher fidelity decisions.
 */
public final class ProjectMetadata {

    private final Map<Path, ModuleMetadata> modules;
    private final Set<String> forgeAnnotations;
    private final long totalJavaFiles;
    private final long totalEventHandlers;
    private final List<Path> javaFiles;

    public ProjectMetadata(Map<Path, ModuleMetadata> modules,
                           Set<String> forgeAnnotations,
                           long totalJavaFiles,
                           long totalEventHandlers,
                           List<Path> javaFiles) {
        Map<Path, ModuleMetadata> ordered = new LinkedHashMap<>();
        modules.forEach((path, metadata) -> ordered.put(path, metadata));
        this.modules = Collections.unmodifiableMap(ordered);
        this.forgeAnnotations = Set.copyOf(forgeAnnotations);
        this.totalJavaFiles = totalJavaFiles;
        this.totalEventHandlers = totalEventHandlers;
        List<Path> orderedFiles = new ArrayList<>(javaFiles.size());
        javaFiles.forEach(path -> orderedFiles.add(path.toAbsolutePath().normalize()));
        this.javaFiles = Collections.unmodifiableList(orderedFiles);
    }

    public Map<Path, ModuleMetadata> modules() {
        return modules;
    }

    public Set<String> forgeAnnotations() {
        return forgeAnnotations;
    }

    public long totalJavaFiles() {
        return totalJavaFiles;
    }

    public long totalEventHandlers() {
        return totalEventHandlers;
    }

    public List<Path> javaFiles() {
        return javaFiles;
    }

    public Optional<ModuleMetadata> moduleFor(Path file) {
        Path normalized = file.toAbsolutePath().normalize();
        return modules.values().stream()
                .filter(module -> module.javaFiles.contains(normalized))
                .findFirst();
    }

    /**
     * Metadata describing a single Gradle module within the project.
     */
    public static final class ModuleMetadata {
        private final Path moduleRoot;
        private final List<Path> sourceRoots;
        private final List<Path> resourceRoots;
        private final int modClassCount;
        private final int eventHandlerCount;
        private final List<Path> javaFiles;

        public ModuleMetadata(Path moduleRoot,
                              List<Path> sourceRoots,
                              List<Path> resourceRoots,
                              int modClassCount,
                              int eventHandlerCount,
                              List<Path> javaFiles) {
            this.moduleRoot = moduleRoot;
            this.sourceRoots = List.copyOf(sourceRoots);
            this.resourceRoots = List.copyOf(resourceRoots);
            this.modClassCount = modClassCount;
            this.eventHandlerCount = eventHandlerCount;
            List<Path> orderedFiles = new ArrayList<>(javaFiles.size());
            javaFiles.forEach(path -> orderedFiles.add(path.toAbsolutePath().normalize()));
            this.javaFiles = Collections.unmodifiableList(orderedFiles);
        }

        public Path moduleRoot() {
            return moduleRoot;
        }

        public List<Path> sourceRoots() {
            return Collections.unmodifiableList(sourceRoots);
        }

        public List<Path> resourceRoots() {
            return Collections.unmodifiableList(resourceRoots);
        }

        public int modClassCount() {
            return modClassCount;
        }

        public int eventHandlerCount() {
            return eventHandlerCount;
        }

        public List<Path> javaFiles() {
            return javaFiles;
        }

        public double eventHandlerDensity() {
            if (javaFiles.isEmpty()) {
                return 0.0d;
            }
            return (double) eventHandlerCount / (double) javaFiles.size();
        }

        public double modClassDensity() {
            if (javaFiles.isEmpty()) {
                return 0.0d;
            }
            return (double) modClassCount / (double) javaFiles.size();
        }
    }
}
