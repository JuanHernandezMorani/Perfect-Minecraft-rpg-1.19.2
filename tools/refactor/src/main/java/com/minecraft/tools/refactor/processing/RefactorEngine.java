package com.minecraft.tools.refactor.processing;

import com.minecraft.tools.refactor.ToolConfiguration;
import com.minecraft.tools.refactor.analysis.ProjectAnalysis;
import com.minecraft.tools.refactor.logging.ToolLog;
import com.minecraft.tools.refactor.planning.PlanStatus;
import com.minecraft.tools.refactor.planning.RefactorPlan;
import com.minecraft.tools.refactor.planning.RefactorPlanner;
import com.minecraft.tools.refactor.report.RefactorReport;
import com.minecraft.tools.refactor.report.RefactorTarget;
import com.minecraft.tools.refactor.util.ProgressTracker;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Executes refactor plans for each identified target.
 */
public final class RefactorEngine {

    private static final Logger LOGGER = ToolLog.getLogger(RefactorEngine.class);
    private final ToolConfiguration configuration;
    private final RefactorPlanner planner;
    private final ProjectAnalysis analysis;

    public RefactorEngine(ToolConfiguration configuration, RefactorPlanner planner, ProjectAnalysis analysis) {
        this.configuration = configuration;
        this.planner = planner;
        this.analysis = analysis;
    }

    public EngineResult process(RefactorReport report) {
        Map<Path, FileIntegritySnapshot> beforeSnapshots = snapshotTargets(report);
        ProgressTracker tracker = new ProgressTracker(LOGGER, report.targets().size());
        for (RefactorTarget target : report.targets()) {
            if (target.effectiveLines() <= configuration.lineThreshold()) {
                target.addDecision("Below threshold - no refactor needed");
                target.updatePlanStatus(PlanStatus.SKIPPED);
                tracker.increment(target.file());
                continue;
            }

            try {
                var planOptional = planner.plan(target.file(), target.effectiveLines());
                if (planOptional.isEmpty()) {
                    target.addError("Planner failed to analyse file");
                    target.updatePlanStatus(PlanStatus.FAILED);
                    continue;
                }

                RefactorPlan plan = planOptional.get();
                if (analysis.multiModule()) {
                    target.addDecision("Multi-module project detected - validating inter-module references");
                }
                plan.notes().forEach(target::addDecision);
                target.attachPlan(plan);

                if (!plan.hasWork()) {
                    target.addDecision("Planner did not schedule any refactor actions");
                    continue;
                }

                if (plan.isModEntryClass()) {
                    target.addDecision("Refactoring Forge @Mod entry class - ensuring lifecycle imports remain intact");
                }

                if (plan.isEventHandlerClass()) {
                    target.addDecision("Event handler class detected - preserving subscriber semantics");
                }

                JavaFileRefactorer refactorer = new JavaFileRefactorer(configuration, target, plan);
                refactorer.refactor();
                target.markRefactored();
                target.updatePlanStatus(PlanStatus.EXECUTED);
                if (configuration.dryRun()) {
                    target.addDecision("Dry run - refactor actions simulated only");
                } else {
                    target.addDecision("Refactor completed successfully");
                }
            } catch (Exception ex) {
                LOGGER.error("Failed to refactor {}: {}", target.file(), ex.getMessage(), ex);
                target.addError(ex.getMessage());
                target.updatePlanStatus(PlanStatus.FAILED);
            }
            tracker.increment(target.file());
        }
        Map<Path, FileIntegritySnapshot> afterSnapshots = snapshotTargets(report);
        IntegritySummary summary = verifyIntegrity(report, beforeSnapshots, afterSnapshots);
        return new EngineResult(configuration.dryRun(), report.targets().size(), summary);
    }

    private IntegritySummary verifyIntegrity(RefactorReport report,
                                             Map<Path, FileIntegritySnapshot> before,
                                             Map<Path, FileIntegritySnapshot> after) {
        int violations = 0;
        int verified = 0;
        for (RefactorTarget target : report.targets()) {
            FileIntegritySnapshot pre = before.get(target.file());
            FileIntegritySnapshot post = after.get(target.file());
            boolean changed = !Objects.equals(pre, post);

            if (configuration.dryRun()) {
                if (changed) {
                    violations++;
                    target.addError("Dry run integrity violation detected - file was modified unexpectedly");
                    target.updatePlanStatus(PlanStatus.FAILED);
                    LOGGER.error("Dry run integrity violation for {}", target.file());
                } else {
                    verified++;
                    target.addDecision("Dry run integrity verified - file unchanged");
                }
            } else {
                if (target.refactored()) {
                    if (changed) {
                        verified++;
                        target.addDecision("Post-refactor integrity verified - file updated");
                    } else {
                        violations++;
                        report.addWarning("integrity", "Expected modifications for " + target.file()
                                + " but no changes were detected");
                        LOGGER.warn("Integrity mismatch - {} expected to change but remained identical", target.file());
                    }
                } else {
                    if (changed) {
                        violations++;
                        target.addError("File changed despite no refactor being applied");
                        target.updatePlanStatus(PlanStatus.FAILED);
                        LOGGER.error("Integrity violation - {} changed without refactor", target.file());
                    } else {
                        verified++;
                        target.addDecision("Integrity verified - file untouched");
                    }
                }
            }
        }
        return new IntegritySummary(verified, violations);
    }

    private Map<Path, FileIntegritySnapshot> snapshotTargets(RefactorReport report) {
        Map<Path, FileIntegritySnapshot> snapshots = new HashMap<>();
        for (RefactorTarget target : report.targets()) {
            snapshots.put(target.file(), FileIntegritySnapshot.capture(target.file()));
        }
        return snapshots;
    }

    public static final class EngineResult {
        private final boolean dryRun;
        private final int targetsProcessed;
        private final IntegritySummary summary;

        EngineResult(boolean dryRun, int targetsProcessed, IntegritySummary summary) {
            this.dryRun = dryRun;
            this.targetsProcessed = targetsProcessed;
            this.summary = summary;
        }

        public boolean dryRun() {
            return dryRun;
        }

        public int targetsProcessed() {
            return targetsProcessed;
        }

        public int integrityViolations() {
            return summary.violations();
        }

        public boolean integrityVerified() {
            return summary.violations() == 0;
        }
    }

    private static final class IntegritySummary {
        private final int verified;
        private final int violations;

        IntegritySummary(int verified, int violations) {
            this.verified = verified;
            this.violations = violations;
        }

        int verified() {
            return verified;
        }

        int violations() {
            return violations;
        }
    }

    private static final class FileIntegritySnapshot {
        private final long size;
        private final byte[] digest;

        private FileIntegritySnapshot(long size, byte[] digest) {
            this.size = size;
            this.digest = digest;
        }

        static FileIntegritySnapshot capture(Path path) {
            try {
                long size = Files.exists(path) ? Files.size(path) : -1L;
                byte[] digest = Files.exists(path) ? digest(path) : new byte[0];
                return new FileIntegritySnapshot(size, digest);
            } catch (IOException ex) {
                throw new IllegalStateException("Failed to capture integrity snapshot for " + path, ex);
            }
        }

        private static byte[] digest(Path path) throws IOException {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                try (InputStream in = Files.newInputStream(path);
                     DigestInputStream dis = new DigestInputStream(in, digest)) {
                    byte[] buffer = new byte[8192];
                    while (dis.read(buffer) != -1) {
                        // consume stream
                    }
                }
                return digest.digest();
            } catch (NoSuchAlgorithmException ex) {
                throw new IllegalStateException("SHA-256 algorithm not available", ex);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FileIntegritySnapshot snapshot)) {
                return false;
            }
            return size == snapshot.size && Arrays.equals(digest, snapshot.digest);
        }

        @Override
        public int hashCode() {
            int result = Long.hashCode(size);
            result = 31 * result + Arrays.hashCode(digest);
            return result;
        }
    }
}
