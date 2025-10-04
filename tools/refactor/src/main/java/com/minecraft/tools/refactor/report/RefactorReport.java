package com.minecraft.tools.refactor.report;

import com.minecraft.tools.refactor.analysis.ProjectMetadata;
import com.minecraft.tools.refactor.planning.PlanStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregated report for a refactor session.
 */
public final class RefactorReport {

    private final List<RefactorTarget> targets = new ArrayList<>();
    private final LocalDateTime created = LocalDateTime.now();
    private final int threshold;
    private final ProjectMetadata metadata;
    private final List<String> warnings = new ArrayList<>();
    private long filesScanned;
    private long filesOverThreshold;
    private Duration scanDuration = Duration.ZERO;
    private Duration lineCountingDuration = Duration.ZERO;
    private long cacheHits;
    private long cacheMisses;

    public RefactorReport(int threshold, ProjectMetadata metadata) {
        this.threshold = threshold;
        this.metadata = metadata;
    }

    public void addTarget(RefactorTarget target) {
        targets.add(target);
    }

    public List<RefactorTarget> targets() {
        return Collections.unmodifiableList(targets);
    }

    public void registerScan(boolean overThreshold) {
        filesScanned++;
        if (overThreshold) {
            filesOverThreshold++;
        }
    }

    public void addWarning(String warning) {
        addWarning("general", warning);
    }

    public void addWarning(String stage, String warning) {
        warnings.add("[" + stage + "] " + warning);
    }

    public List<String> warnings() {
        return Collections.unmodifiableList(warnings);
    }

    public long filesScanned() {
        return filesScanned;
    }

    public long filesOverThreshold() {
        return filesOverThreshold;
    }

    public long filesRefactored() {
        return targets.stream().filter(RefactorTarget::refactored).count();
    }

    public void recordPerformance(Duration scanDuration, Duration lineCountingDuration,
                                  long cacheHits, long cacheMisses) {
        this.scanDuration = scanDuration;
        this.lineCountingDuration = lineCountingDuration;
        this.cacheHits = cacheHits;
        this.cacheMisses = cacheMisses;
    }

    public long scanDurationMillis() {
        return scanDuration.toMillis();
    }

    public long lineCountingDurationMillis() {
        return lineCountingDuration.toMillis();
    }

    public long lineCountCacheHits() {
        return cacheHits;
    }

    public long lineCountCacheMisses() {
        return cacheMisses;
    }

    public Map<PlanStatus, Long> planStatusBreakdown() {
        Map<PlanStatus, Long> breakdown = new EnumMap<>(PlanStatus.class);
        for (PlanStatus status : PlanStatus.values()) {
            breakdown.put(status, 0L);
        }
        for (RefactorTarget target : targets) {
            breakdown.computeIfPresent(target.planStatus(), (status, count) -> count + 1);
        }
        return breakdown;
    }

    public ProjectMetadata metadata() {
        return metadata;
    }

    public int threshold() {
        return threshold;
    }

    public String asText() {
        StringBuilder builder = new StringBuilder();
        builder.append("Minecraft Forge Refactor Report\n");
        builder.append("Generated at ")
                .append(created.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .append("\n\n");

        builder.append("Configuration\n");
        builder.append("  Threshold: ").append(threshold).append(" effective lines\n");
        if (metadata != null) {
            builder.append("  Modules analysed: ").append(metadata.modules().size()).append("\n");
            builder.append("  Java files inspected: ").append(metadata.totalJavaFiles()).append("\n");
            builder.append("  Forge event handlers detected: ").append(metadata.totalEventHandlers()).append("\n");
            builder.append("  Distinct Forge annotations observed: ").append(metadata.forgeAnnotations().size()).append("\n\n");

            builder.append("Module breakdown:\n");
            metadata.modules().values().forEach(module -> builder
                    .append("  - ").append(module.moduleRoot()).append('\n')
                    .append("      Source roots: ").append(module.sourceRoots().size()).append(", resources: ")
                    .append(module.resourceRoots().size()).append('\n')
                    .append("      @Mod classes: ").append(module.modClassCount())
                    .append(", event handlers: ").append(module.eventHandlerCount()).append('\n'));
            builder.append('\n');
        }

        builder.append("Scan statistics\n");
        builder.append("  Files scanned: ").append(filesScanned).append('\n');
        builder.append("  Files above threshold: ").append(filesOverThreshold).append('\n');
        builder.append("  Files refactored: ").append(filesRefactored()).append('\n');
        long truncatedCounts = targets.stream().filter(RefactorTarget::lineCountTruncated).count();
        builder.append("  Truncated line counts: ").append(truncatedCounts).append('\n');
        builder.append("  Plan status breakdown:\n");
        planStatusBreakdown().forEach((status, count) -> builder.append("    ")
                .append(status.name()).append(": ").append(count).append('\n'));
        builder.append('\n');

        builder.append("Performance\n");
        builder.append("  Scan duration: ").append(scanDuration.toMillis()).append(" ms\n");
        builder.append("  Line counting duration: ").append(lineCountingDuration.toMillis()).append(" ms\n");
        builder.append("  Line count cache hits/misses: ").append(cacheHits).append("/").append(cacheMisses).append('\n');
        builder.append('\n');

        if (!warnings.isEmpty()) {
            builder.append("Warnings\n");
            warnings.forEach(warning -> builder.append("  - ").append(warning).append('\n'));
            builder.append('\n');
        }

        for (RefactorTarget target : targets) {
            builder.append(target.file()).append("\n");
            String lineDescriptor = target.lineCountTruncated()
                    ? ">= " + target.effectiveLines()
                    : Integer.toString(target.effectiveLines());
            builder.append("  Effective lines: ").append(lineDescriptor).append("\n");
            builder.append("  Refactored: ").append(target.refactored()).append("\n");
            builder.append("  Plan status: ").append(target.planStatus()).append("\n");

            if (!target.decisions().isEmpty()) {
                builder.append("  Decisions:\n");
                target.decisions().forEach(decision -> builder.append("    - ").append(decision).append("\n"));
            }
            if (!target.errors().isEmpty()) {
                builder.append("  Errors:\n");
                target.errors().forEach(error -> builder.append("    - ").append(error).append("\n"));
            }
            builder.append('\n');
        }

        return builder.toString();
    }
}
