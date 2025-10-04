package com.minecraft.tools.refactor;

import com.minecraft.tools.refactor.analysis.ForgeProjectAnalyzer;
import com.minecraft.tools.refactor.analysis.JavaProjectScanner;
import com.minecraft.tools.refactor.analysis.ProjectAnalysis;
import com.minecraft.tools.refactor.analysis.ProjectValidationResult;
import com.minecraft.tools.refactor.analysis.ProjectValidator;
import com.minecraft.tools.refactor.logging.ToolLog;
import com.minecraft.tools.refactor.planning.RefactorPlanner;
import com.minecraft.tools.refactor.processing.RefactorEngine;
import com.minecraft.tools.refactor.processing.RefactorEngine.EngineResult;
import com.minecraft.tools.refactor.report.RefactorReport;
import com.minecraft.tools.refactor.planning.PlanStatus;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

/**
 * Entry point for the Minecraft Forge refactor tool.
 */
public final class RefactorTool {

    private static final Logger LOGGER = ToolLog.getLogger(RefactorTool.class);

    private RefactorTool() {
    }

    public static void main(String[] args) {
        if (hasArg(args, "--help", "-h")) {
            printUsage();
            return;
        }

        try {
            ToolConfiguration configuration = buildConfiguration(args);
            ProjectValidationResult validation = validateProject(configuration.projectRoot());

            ForgeProjectAnalyzer analyzer = new ForgeProjectAnalyzer(configuration);
            ProjectAnalysis analysis = analyzer.analyse(validation);

            JavaProjectScanner scanner = new JavaProjectScanner(configuration, analysis);
            RefactorPlanner planner = new RefactorPlanner(configuration, analysis);
            RefactorEngine engine = new RefactorEngine(configuration, planner, analysis);

            LOGGER.info("Starting refactor for project at {}", configuration.projectRoot());
            Instant start = Instant.now();

            RefactorReport report = scanner.scan();
            EngineResult engineResult = engine.process(report);

            analysis.warnings().forEach(warning -> report.addWarning("analysis", warning));

            Instant end = Instant.now();
            LOGGER.info("Refactor completed in {} seconds", Duration.between(start, end).toSeconds());

            LOGGER.info("Files scanned: {} ({} above threshold)", report.filesScanned(), report.filesOverThreshold());
            LOGGER.info("Files refactored: {}", report.filesRefactored());
            LOGGER.info("Integrity verification: {} (violations={}, targets={}, dryRun={})",
                    engineResult.integrityVerified(), engineResult.integrityViolations(),
                    engineResult.targetsProcessed(), configuration.dryRun());
            LOGGER.info("Scan performance: {} ms total, {} ms counting (cache hits/misses {} / {})",
                    report.scanDurationMillis(), report.lineCountingDurationMillis(),
                    report.lineCountCacheHits(), report.lineCountCacheMisses());
            for (Map.Entry<PlanStatus, Long> entry : report.planStatusBreakdown().entrySet()) {
                LOGGER.info("Plan status {} -> {}", entry.getKey(), entry.getValue());
            }

            report.warnings().forEach(warning -> LOGGER.warn("Analysis warning: {}", warning));

            if (!engineResult.integrityVerified()) {
                throw new IllegalStateException("Integrity verification failed - see log for details");
            }

            if (!configuration.dryRun()) {
                Path reportPath = configuration.projectRoot().resolve("refactor-report.log");
                Files.writeString(reportPath, report.asText());
                LOGGER.info("Detailed report written to {}", reportPath);
            } else {
                LOGGER.warn("Dry run enabled - no files were modified. Report: \n{}", report.asText());
            }
        } catch (Exception ex) {
            LOGGER.error("Refactor tool failed: {}", ex.getMessage(), ex);
            System.exit(1);
        }
    }

    private static ToolConfiguration buildConfiguration(String[] args) throws IOException {
        Path projectRoot = Paths.get("..", "..").normalize().toAbsolutePath();
        int threshold = 500;
        boolean dryRun = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--project":
                case "-p":
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("Missing value for --project option");
                    }
                    projectRoot = Paths.get(args[++i]).toAbsolutePath();
                    break;
                case "--threshold":
                case "-t":
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("Missing value for --threshold option");
                    }
                    threshold = Integer.parseInt(args[++i]);
                    break;
                case "--dry-run":
                    dryRun = true;
                    break;
                default:
                    // ignore unknown args but warn
                    ToolLog.getLogger(RefactorTool.class)
                            .warn("Unknown argument '{}' ignored", args[i]);
            }
        }

        if (!Files.exists(projectRoot)) {
            throw new IOException("Project root does not exist: " + projectRoot);
        }

        LOGGER.info("Configuration: projectRoot={}, threshold={}, dryRun={}", projectRoot, threshold, dryRun);
        return new ToolConfiguration(projectRoot, threshold, dryRun);
    }

    private static ProjectValidationResult validateProject(Path projectRoot) {
        ProjectValidator validator = new ProjectValidator(projectRoot);
        return validator.validate();
    }

    private static boolean hasArg(String[] args, String... options) {
        return Arrays.stream(args)
                .map(arg -> arg.toLowerCase(Locale.ROOT))
                .anyMatch(arg -> Arrays.stream(options)
                        .map(opt -> opt.toLowerCase(Locale.ROOT))
                        .anyMatch(arg::equals));
    }

    private static void printUsage() {
        System.out.println("Minecraft Forge automatic refactor tool");
        System.out.println("Usage: ./gradlew run --args=\"[options]\"");
        System.out.println("Options:");
        System.out.println("  --project, -p <path>    Path to the Forge project root");
        System.out.println("  --threshold, -t <num>   Maximum effective lines before refactor (default 500)");
        System.out.println("  --dry-run               Analyze only, do not modify files");
        System.out.println("  --help, -h              Show this help message");
    }
}
