package com.minecraft.tools.refactor.analysis;

import com.minecraft.tools.refactor.ToolConfiguration;
import com.minecraft.tools.refactor.logging.ToolLog;
import com.minecraft.tools.refactor.report.RefactorReport;
import com.minecraft.tools.refactor.report.RefactorTarget;
import com.minecraft.tools.refactor.util.LineCountCache;
import com.minecraft.tools.refactor.util.LineCountResult;
import com.minecraft.tools.refactor.util.ProgressTracker;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Scans the project for Java files exceeding the configured threshold.
 */
public final class JavaProjectScanner {

    private static final Logger LOGGER = ToolLog.getLogger(JavaProjectScanner.class);

    private final ToolConfiguration configuration;
    private final ProjectAnalysis analysis;
    private final LineCountCache lineCountCache = new LineCountCache();
    private long lineCountingNanos;

    public JavaProjectScanner(ToolConfiguration configuration, ProjectAnalysis analysis) {
        this.configuration = configuration;
        this.analysis = analysis;
    }

    public RefactorReport scan() throws IOException {
        RefactorReport report = new RefactorReport(configuration.lineThreshold(), analysis.metadata());

        List<Path> javaFiles = gatherJavaFiles();
        if (javaFiles.isEmpty()) {
            throw new IOException("No Java sources discovered for scanning");
        }

        lineCountingNanos = 0L;
        ProgressTracker tracker = new ProgressTracker(LOGGER, javaFiles.size());
        Instant scanStart = Instant.now();

        for (Path javaFile : javaFiles) {
            analyse(report, javaFile);
            tracker.increment(javaFile);
        }

        Instant scanEnd = Instant.now();
        Duration scanDuration = Duration.between(scanStart, scanEnd);
        Duration lineCountingDuration = Duration.ofNanos(lineCountingNanos);

        report.recordPerformance(scanDuration, lineCountingDuration, lineCountCache.hits(), lineCountCache.misses());

        LOGGER.info("Completed scan of {} Java files in {} ms", javaFiles.size(), scanDuration.toMillis());
        if (lineCountingDuration.toMillis() > 0) {
            double seconds = lineCountingDuration.toMillis() / 1000.0d;
            double throughput = report.filesScanned() / Math.max(seconds, 0.001d);
            String throughputValue = String.format(Locale.ROOT, "%.2f", throughput);
            LOGGER.info("Line counting throughput: {} files/sec across {} analysed files",
                    throughputValue, report.filesScanned());
        }

        return report;
    }

    private void analyse(RefactorReport report, Path javaFile) {
        try {
            int shortCircuitLimit = Math.max(configuration.lineThreshold() * 2, 1000);
            long start = System.nanoTime();
            LineCountResult lineCount = lineCountCache.count(javaFile, shortCircuitLimit);
            lineCountingNanos += System.nanoTime() - start;
            boolean overThreshold = lineCount.exceeds(configuration.lineThreshold());
            report.registerScan(overThreshold);
            if (overThreshold) {
                if (lineCount.truncated()) {
                    LOGGER.info("File {} exceeds threshold with at least {} lines (truncated)", javaFile, lineCount.lines());
                } else {
                    LOGGER.info("File {} exceeds threshold with {} lines", javaFile, lineCount.lines());
                }
            }
            RefactorTarget target = new RefactorTarget(javaFile, lineCount);
            if (lineCount.truncated()) {
                target.addDecision("Line counting stopped early after " + lineCount.lines()
                        + " effective lines to accelerate large file analysis");
                report.addWarning("scanner", "Line count truncated for " + javaFile
                        + " after exceeding performance threshold");
            }
            report.addTarget(target);
        } catch (IOException ex) {
            LOGGER.error("Failed to analyse {}: {}", javaFile, ex.getMessage());
            report.registerScan(false);
            report.addWarning("scanner", "Failed to analyse " + javaFile + ": " + ex.getMessage());
        }
    }

    private List<Path> gatherJavaFiles() throws IOException {
        if (analysis.metadata() != null && !analysis.metadata().javaFiles().isEmpty()) {
            Set<Path> ordered = new LinkedHashSet<>(analysis.metadata().javaFiles());
            return ordered.stream()
                    .filter(Files::exists)
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        List<Path> discovered = new ArrayList<>();
        for (Path root : analysis.sourceRoots()) {
            if (!Files.exists(root)) {
                LOGGER.warn("Skipping missing source root {}", root);
                continue;
            }
            try (Stream<Path> stream = Files.walk(root)) {
                stream.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".java"))
                        .sorted(Comparator.naturalOrder())
                        .forEach(discovered::add);
            }
        }
        return discovered;
    }
}
