package com.minecraft.tools.refactor.planning;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.minecraft.tools.refactor.ToolConfiguration;
import com.minecraft.tools.refactor.analysis.ForgeAnnotationCatalog;
import com.minecraft.tools.refactor.analysis.ProjectAnalysis;
import com.minecraft.tools.refactor.analysis.ProjectMetadata;
import com.minecraft.tools.refactor.logging.ToolLog;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Generates a detailed refactor plan for a Java file using Forge-aware heuristics.
 */
public final class RefactorPlanner {

    private static final Logger LOGGER = ToolLog.getLogger(RefactorPlanner.class);

    private final ToolConfiguration configuration;
    private final ProjectAnalysis analysis;
    private final ProjectMetadata metadata;

    public RefactorPlanner(ToolConfiguration configuration, ProjectAnalysis analysis) {
        this.configuration = configuration;
        this.analysis = analysis;
        this.metadata = analysis.metadata();
    }

    public Optional<RefactorPlan> plan(Path javaFile, int effectiveLines) {
        List<String> notes = new ArrayList<>();
        try {
            CompilationUnit unit = StaticJavaParser.parse(javaFile);
            Optional<TypeDeclaration<?>> primaryType = unit.getPrimaryType();
            if (primaryType.isEmpty() || !(primaryType.get() instanceof ClassOrInterfaceDeclaration outerClass)) {
                notes.add("Skipping planning for " + javaFile + " because no primary class declaration was found");
                return Optional.of(new RefactorPlan(javaFile, List.of(), Map.of(), notes, false, false, PlanStatus.SKIPPED));
            }

            Path normalized = javaFile.toAbsolutePath().normalize();
            boolean isModEntryClass = analysis.modEntryClasses().contains(normalized);
            boolean isEventHandlerClass = analysis.eventHandlerClasses().contains(normalized);

            Optional<ProjectMetadata.ModuleMetadata> moduleContext = metadata != null
                    ? metadata.moduleFor(javaFile)
                    : Optional.empty();
            moduleContext.ifPresent(module -> {
                notes.add("Module context: " + module.moduleRoot()
                        + " provides " + module.modClassCount() + " @Mod classes and "
                        + module.eventHandlerCount() + " Forge event handlers");
                if (module.eventHandlerDensity() > 0.25d) {
                    notes.add("Module is event-heavy (density "
                            + String.format(Locale.ROOT, "%.2f", module.eventHandlerDensity())
                            + ") - planner prioritises event grouping");
                }
                if (module.modClassDensity() > 0.10d) {
                    notes.add("Module contains multiple @Mod classes (density "
                            + String.format(Locale.ROOT, "%.2f", module.modClassDensity())
                            + ") - lifecycle preservation tightened");
                }
            });

            if (effectiveLines > configuration.lineThreshold() * 2L) {
                notes.add("File exceeds threshold by a large margin (" + effectiveLines + " lines) - planner prioritising deeper splits");
            }

            List<String> nestedClasses = determineNestedClassPlan(unit, notes);
            Map<String, List<String>> methodGroups = determineMethodGroupPlan(outerClass, notes, isModEntryClass,
                    isEventHandlerClass, moduleContext);

            PlanStatus status = PlanStatus.ANALYSED;

            if (!configuration.dryRun() && !nestedClasses.isEmpty()) {
                notes.add("Scheduled extraction of " + nestedClasses.size() + " nested classes");
            }
            if (!configuration.dryRun() && methodGroups.values().stream().anyMatch(list -> !list.isEmpty())) {
                notes.add("Scheduled extraction of " + methodGroups.values().stream().mapToInt(List::size).sum()
                        + " static methods grouped across " + methodGroups.size() + " helper classes");
            }

            boolean hasWork = !nestedClasses.isEmpty() || methodGroups.values().stream().anyMatch(list -> !list.isEmpty());
            if (hasWork) {
                status = PlanStatus.PLANNED;
            }

            RefactorPlan plan = new RefactorPlan(javaFile, nestedClasses, methodGroups, notes, isModEntryClass,
                    isEventHandlerClass, status);
            return Optional.of(plan);
        } catch (IOException ex) {
            LOGGER.error("Planner failed to read {}: {}", javaFile, ex.getMessage());
            return Optional.empty();
        }
    }

    private List<String> determineNestedClassPlan(CompilationUnit unit, List<String> notes) {
        List<String> extractable = new ArrayList<>();
        unit.findAll(ClassOrInterfaceDeclaration.class).forEach(clazz -> {
            if (clazz.isNestedType() && clazz.isStatic()) {
                if (referencesOuterState(clazz)) {
                    notes.add("Nested class " + clazz.getNameAsString() + " retains references to outer scope - kept inline");
                } else {
                    extractable.add(clazz.getNameAsString());
                    notes.add("Nested class " + clazz.getNameAsString() + " marked for extraction");
                }
            }
        });
        return extractable;
    }

    private Map<String, List<String>> determineMethodGroupPlan(ClassOrInterfaceDeclaration outerClass,
                                                               List<String> notes,
                                                               boolean modEntryClass,
                                                               boolean eventHandlerClass,
                                                               Optional<ProjectMetadata.ModuleMetadata> moduleContext) {
        Map<String, List<String>> groups = new HashMap<>();
        boolean eventHeavyModule = moduleContext.map(ProjectMetadata.ModuleMetadata::eventHandlerDensity)
                .map(density -> density > 0.25d)
                .orElse(false);
        boolean lifecycleDenseModule = moduleContext.map(ProjectMetadata.ModuleMetadata::modClassDensity)
                .map(density -> density > 0.10d)
                .orElse(false);

        for (MethodDeclaration method : outerClass.getMethods()) {
            if (!method.isStatic() || method.getBody().isEmpty()) {
                continue;
            }

            if (referencesPrivateMembers(method, outerClass)) {
                notes.add("Static method " + method.getNameAsString() + " uses private members - cannot move");
                continue;
            }

            boolean hasEventAnnotation = hasEventAnnotation(method);
            if (modEntryClass && !hasEventAnnotation && lifecycleDenseModule) {
                notes.add("Mod entry class method " + method.getNameAsString() + " kept inline to preserve lifecycle");
                continue;
            }

            if (ForgeAnnotationCatalog.isLifecycleMethod(method.getNameAsString())) {
                notes.add("Lifecycle method " + method.getNameAsString() + " retained to protect Forge startup sequence");
                continue;
            }

            if (eventHandlerClass && !hasEventAnnotation) {
                if (eventHeavyModule && looksLikeEventUtility(method.getNameAsString())) {
                    notes.add("Event-heavy module allows extraction of supporting method " + method.getNameAsString());
                } else {
                    notes.add("Event subscriber utility method " + method.getNameAsString()
                            + " has no event annotation - deferring extraction");
                    continue;
                }
            }

            String group = determineGroupName(method, hasEventAnnotation);
            groups.computeIfAbsent(group, key -> new ArrayList<>()).add(method.getNameAsString());
        }

        if (groups.isEmpty()) {
            notes.add("No safe static method groups detected for extraction");
        }

        return groups;
    }

    private boolean looksLikeEventUtility(String methodName) {
        String lower = methodName.toLowerCase(Locale.ROOT);
        return lower.contains("event") || lower.contains("handle") || lower.contains("on") || lower.contains("listener");
    }

    private boolean referencesOuterState(ClassOrInterfaceDeclaration declaration) {
        OuterStateDetector detector = new OuterStateDetector();
        declaration.accept(detector, null);
        return detector.referencesOuterState();
    }

    private boolean referencesPrivateMembers(MethodDeclaration method, ClassOrInterfaceDeclaration outerClass) {
        Set<String> privateMembers = new HashSet<>();
        outerClass.getFields().forEach(field -> {
            if (field.isPrivate()) {
                field.getVariables().forEach(var -> privateMembers.add(var.getNameAsString()));
            }
        });
        outerClass.getMethods().forEach(m -> {
            if (m.isPrivate()) {
                privateMembers.add(m.getNameAsString());
            }
        });

        PrivateMemberDetector detector = new PrivateMemberDetector(privateMembers);
        method.accept(detector, null);
        return detector.usesPrivateMembers();
    }

    private boolean hasEventAnnotation(MethodDeclaration method) {
        return method.getAnnotations().stream()
                .map(AnnotationExpr::getNameAsString)
                .anyMatch(ForgeAnnotationCatalog::isEventMethodAnnotation);
    }

    private String determineGroupName(MethodDeclaration method, boolean hasEventAnnotation) {
        if (hasEventAnnotation) {
            return "EventHandlers";
        }

        for (AnnotationExpr annotation : method.getAnnotations()) {
            String name = annotation.getName().getIdentifier().toLowerCase(Locale.ROOT);
            if (name.contains("register")) {
                return "RegistrationGroup";
            }
        }

        String methodName = method.getNameAsString().toLowerCase(Locale.ROOT);
        if (methodName.startsWith("register") || methodName.contains("registry")) {
            return "RegistrationGroup";
        }
        if (methodName.contains("util") || methodName.contains("helper")) {
            return "UtilityGroup";
        }
        if (methodName.contains("capability")) {
            return "Capabilities";
        }

        return "StaticMethods";
    }

    private static final class OuterStateDetector extends VoidVisitorAdapter<Void> {
        private boolean outerState;

        @Override
        public void visit(ThisExpr n, Void arg) {
            super.visit(n, arg);
            outerState = true;
        }

        boolean referencesOuterState() {
            return outerState;
        }
    }

    private static final class PrivateMemberDetector extends VoidVisitorAdapter<Void> {
        private final Set<String> privateMembers;
        private boolean usesPrivateMembers;

        private PrivateMemberDetector(Set<String> privateMembers) {
            this.privateMembers = privateMembers;
        }

        @Override
        public void visit(NameExpr n, Void arg) {
            super.visit(n, arg);
            if (privateMembers.contains(n.getNameAsString())) {
                usesPrivateMembers = true;
            }
        }

        boolean usesPrivateMembers() {
            return usesPrivateMembers;
        }
    }
}
