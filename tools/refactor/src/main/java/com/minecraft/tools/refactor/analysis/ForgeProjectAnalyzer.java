package com.minecraft.tools.refactor.analysis;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.minecraft.tools.refactor.ToolConfiguration;
import com.minecraft.tools.refactor.logging.ToolLog;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

/**
 * Analyses Forge-specific structures to support the refactor planner.
 */
public final class ForgeProjectAnalyzer {

    private static final Logger LOGGER = ToolLog.getLogger(ForgeProjectAnalyzer.class);

    public ForgeProjectAnalyzer(ToolConfiguration configuration) {
        // configuration reserved for future heuristics requiring threshold awareness
    }

    public ProjectAnalysis analyse(ProjectValidationResult validation) throws IOException {
        List<Path> sourceRoots = new ArrayList<>();
        List<Path> resourceRoots = new ArrayList<>();
        List<String> warnings = new ArrayList<>(validation.warnings());
        Set<Path> modClasses = new HashSet<>();
        Set<Path> eventHandlerClasses = new HashSet<>();
        Map<Path, ProjectMetadata.ModuleMetadata> moduleMetadata = new LinkedHashMap<>();
        Set<String> annotationsSeen = new HashSet<>();
        LongAdder totalJavaFiles = new LongAdder();
        LongAdder totalEventHandlers = new LongAdder();
        List<Path> discoveredJavaFiles = new ArrayList<>();

        for (Path moduleRoot : validation.moduleRoots()) {
            Path srcMainJava = moduleRoot.resolve(Path.of("src", "main", "java"));
            Path srcMainResources = moduleRoot.resolve(Path.of("src", "main", "resources"));

            List<Path> moduleSources = new ArrayList<>();
            List<Path> moduleResources = new ArrayList<>();

            if (Files.isDirectory(srcMainJava)) {
                sourceRoots.add(srcMainJava);
                moduleSources.add(srcMainJava);
            }

            if (Files.isDirectory(srcMainResources)) {
                resourceRoots.add(srcMainResources);
                moduleResources.add(srcMainResources);
            }

            ModuleStats moduleStats = new ModuleStats();

            for (Path sourceRoot : moduleSources) {
                try (Stream<Path> stream = Files.walk(sourceRoot)) {
                    stream.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".java"))
                            .forEach(javaFile -> {
                                totalJavaFiles.increment();
                                Path normalized = javaFile.toAbsolutePath().normalize();
                                discoveredJavaFiles.add(normalized);
                                moduleStats.javaFiles.add(normalized);
                                analyseJavaFile(moduleRoot, javaFile, modClasses, eventHandlerClasses, warnings,
                                        moduleStats, annotationsSeen, totalEventHandlers);
                            });
                }
            }

            moduleMetadata.put(moduleRoot, new ProjectMetadata.ModuleMetadata(moduleRoot, moduleSources, moduleResources,
                    moduleStats.modClassCount, moduleStats.eventHandlerCount, moduleStats.javaFiles));
        }

        if (sourceRoots.isEmpty()) {
            throw new IOException("No Java source roots discovered in project " + validation.projectRoot());
        }

        ProjectMetadata metadata = new ProjectMetadata(moduleMetadata, annotationsSeen,
                totalJavaFiles.longValue(), totalEventHandlers.longValue(), discoveredJavaFiles);

        return new ProjectAnalysis(sourceRoots, resourceRoots, modClasses, eventHandlerClasses, warnings,
                validation.multiModule(), metadata);
    }

    private void analyseJavaFile(Path moduleRoot,
                                 Path javaFile,
                                 Set<Path> modClasses,
                                 Set<Path> eventHandlerClasses,
                                 List<String> warnings,
                                 ModuleStats moduleStats,
                                 Set<String> annotationsSeen,
                                 LongAdder totalEventHandlers) {
        try {
            CompilationUnit unit = StaticJavaParser.parse(javaFile);
            Optional<String> packageName = unit.getPackageDeclaration().map(pd -> pd.getName().asString());

            for (ClassOrInterfaceDeclaration declaration : unit.findAll(ClassOrInterfaceDeclaration.class)) {
                if (isModClass(declaration)) {
                    modClasses.add(javaFile.toAbsolutePath().normalize());
                    moduleStats.modClassCount++;
                    LOGGER.debug("Detected Forge @Mod class {} in {}", declaration.getNameAsString(), javaFile);
                }

                if (isEventSubscriber(declaration)) {
                    eventHandlerClasses.add(javaFile.toAbsolutePath().normalize());
                    moduleStats.eventHandlerCount++;
                    LOGGER.debug("Detected Forge event subscriber {} in {}", declaration.getNameAsString(), javaFile);
                }

                declaration.getAnnotations()
                        .forEach(annotation -> annotationsSeen.add(ForgeAnnotationCatalog.canonicalName(annotation.getNameAsString())));

                declaration.getMethods().forEach(method -> method.getAnnotations()
                        .forEach(annotation -> annotationsSeen.add(ForgeAnnotationCatalog.canonicalName(annotation.getNameAsString()))));

                if (packageName.isPresent() && declaration.isPublic()) {
                    checkForLargeAnonymousHandlers(declaration, warnings, packageName.get(), javaFile);
                }

                long eventMethodCount = declaration.getMethods().stream()
                        .filter(this::hasForgeEventAnnotation)
                        .count();
                if (eventMethodCount > 0) {
                    totalEventHandlers.add(eventMethodCount);
                }

            }
        } catch (ParseProblemException ex) {
            String message = "Failed to parse " + javaFile + ": " + ex.getMessage();
            warnings.add(message);
            LOGGER.warn(message);
        } catch (IOException ex) {
            String message = "IOException while reading " + javaFile + ": " + ex.getMessage();
            warnings.add(message);
            LOGGER.warn(message);
        }
    }

    private boolean isModClass(ClassOrInterfaceDeclaration declaration) {
        return declaration.getAnnotations().stream()
                .map(AnnotationExpr::getNameAsString)
                .anyMatch(ForgeAnnotationCatalog::isModAnnotation);
    }

    private boolean isEventSubscriber(ClassOrInterfaceDeclaration declaration) {
        boolean classAnnotated = declaration.getAnnotations().stream()
                .map(AnnotationExpr::getNameAsString)
                .anyMatch(ForgeAnnotationCatalog::isEventClassAnnotation);

        if (classAnnotated) {
            return true;
        }

        return declaration.getMethods().stream()
                .flatMap(method -> method.getAnnotations().stream())
                .map(AnnotationExpr::getNameAsString)
                .anyMatch(ForgeAnnotationCatalog::isEventMethodAnnotation);
    }

    private void checkForLargeAnonymousHandlers(ClassOrInterfaceDeclaration declaration,
                                                List<String> warnings,
                                                String packageName,
                                                Path javaFile) {
        for (MethodDeclaration method : declaration.getMethods()) {
            boolean subscribeEvent = method.getAnnotations().stream()
                    .map(AnnotationExpr::getNameAsString)
                    .anyMatch(ForgeAnnotationCatalog::isEventMethodAnnotation);
            if (subscribeEvent && method.getBody().isPresent() && method.getBody().get().getStatements().size() > 100) {
                warnings.add("Large event handler " + packageName + "." + method.getNameAsString()
                        + " detected in " + javaFile + ". Planner will prioritise splitting supporting utilities.");
            }
        }
    }

    private boolean hasForgeEventAnnotation(MethodDeclaration declaration) {
        return declaration.getAnnotations().stream()
                .map(AnnotationExpr::getNameAsString)
                .anyMatch(ForgeAnnotationCatalog::isEventMethodAnnotation);
    }

    private static final class ModuleStats {
        private int modClassCount;
        private int eventHandlerCount;
        private final List<Path> javaFiles = new ArrayList<>();
    }
}
