package com.minecraft.tools.refactor.util;

/**
 * Result of an effective line counting operation.
 */
public record LineCountResult(int lines, boolean truncated) {

    public boolean exceeds(int threshold) {
        return truncated || lines > threshold;
    }
}
