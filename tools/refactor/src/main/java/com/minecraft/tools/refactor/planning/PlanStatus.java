package com.minecraft.tools.refactor.planning;

/**
 * Enumerates the lifecycle of a refactor plan for a given Java source file.
 */
public enum PlanStatus {
    ANALYSED,
    SKIPPED,
    PLANNED,
    EXECUTED,
    FAILED
}
