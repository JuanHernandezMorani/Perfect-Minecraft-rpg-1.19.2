package com.minecraft.tools.refactor;

import java.nio.file.Path;

/**
 * Immutable configuration for the refactor tool.
 */
public final class ToolConfiguration {

    private final Path projectRoot;
    private final int lineThreshold;
    private final boolean dryRun;

    public ToolConfiguration(Path projectRoot, int lineThreshold, boolean dryRun) {
        this.projectRoot = projectRoot;
        this.lineThreshold = lineThreshold;
        this.dryRun = dryRun;
    }

    public Path projectRoot() {
        return projectRoot;
    }

    public int lineThreshold() {
        return lineThreshold;
    }

    public boolean dryRun() {
        return dryRun;
    }
}
