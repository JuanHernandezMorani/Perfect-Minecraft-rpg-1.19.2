package com.minecraft.tools.refactor.analysis;

import com.minecraft.tools.refactor.logging.ToolLog;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates that the target directory is a Minecraft Forge project.
 */
public final class ProjectValidator {

    private static final Logger LOGGER = ToolLog.getLogger(ProjectValidator.class);
    private static final Pattern INCLUDE_PATTERN = Pattern.compile("include\\s+['\"](:?[A-Za-z0-9:_-]+)['\"]");

    private final Path projectRoot;

    public ProjectValidator(Path projectRoot) {
        this.projectRoot = projectRoot;
    }

    public ProjectValidationResult validate() {
        Path buildGradle = projectRoot.resolve("build.gradle");
        if (!Files.exists(buildGradle)) {
            throw new IllegalStateException("No build.gradle found at " + buildGradle);
        }

        boolean forgeDependency = false;
        try {
            List<String> content = Files.readAllLines(buildGradle);
            forgeDependency = content.stream()
                    .map(line -> line.toLowerCase(Locale.ROOT))
                    .anyMatch(line -> line.contains("net.minecraftforge") || line.contains("forgegradle"));
            if (!forgeDependency) {
                LOGGER.warn("build.gradle does not reference net.minecraftforge. The project might not be a Forge project.");
            } else {
                LOGGER.info("Forge dependency detected in build.gradle");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read build.gradle", e);
        }

        List<Path> moduleRoots = new ArrayList<>();
        moduleRoots.add(projectRoot);

        Path settings = projectRoot.resolve("settings.gradle");
        boolean multiModule = false;
        if (Files.exists(settings)) {
            try {
                List<String> content = Files.readAllLines(settings);
                Set<Path> discovered = new HashSet<>();
                for (String line : content) {
                    Matcher matcher = INCLUDE_PATTERN.matcher(line);
                    while (matcher.find()) {
                        String include = matcher.group(1);
                        String normalized = include.replace(":", "/");
                        Path moduleRoot = projectRoot.resolve(normalized).normalize();
                        if (Files.exists(moduleRoot)) {
                            if (discovered.add(moduleRoot)) {
                                moduleRoots.add(moduleRoot);
                            }
                        } else {
                            LOGGER.warn("Declared Gradle module {} not found on disk at {}", include, moduleRoot);
                        }
                    }
                }
                multiModule = discovered.size() > 0;
            } catch (IOException e) {
                throw new IllegalStateException("Failed to parse settings.gradle", e);
            }
        }

        List<String> warnings = new ArrayList<>();
        boolean modsTomlFound = false;

        for (Path moduleRoot : moduleRoots) {
            Path src = moduleRoot.resolve("src");
            if (!Files.exists(src)) {
                String warning = "Module " + moduleRoot + " is missing src directory";
                warnings.add(warning);
                LOGGER.warn(warning);
                continue;
            }

            Path mainJava = src.resolve(Path.of("main", "java"));
            if (!Files.exists(mainJava)) {
                String warning = "Module " + moduleRoot + " does not contain src/main/java";
                warnings.add(warning);
                LOGGER.warn(warning);
            }

            Path modsToml = src.resolve(Path.of("main", "resources", "META-INF", "mods.toml"));
            if (Files.exists(modsToml)) {
                modsTomlFound = true;
            }
        }

        if (!modsTomlFound) {
            warnings.add("No META-INF/mods.toml descriptor found. Ensure the project is a Forge mod.");
            LOGGER.warn("mods.toml descriptor could not be located under any module");
        }

        LOGGER.info("Project structure validated successfully at {}", projectRoot);

        return new ProjectValidationResult(projectRoot, forgeDependency, modsTomlFound, multiModule, moduleRoots, warnings);
    }
}
