package com.minecraft.tools.refactor.processing;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.minecraft.tools.refactor.ToolConfiguration;
import com.minecraft.tools.refactor.logging.ToolLog;
import com.minecraft.tools.refactor.planning.RefactorPlan;
import com.minecraft.tools.refactor.report.RefactorTarget;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Performs refactor operations for a single Java file.
 */
final class JavaFileRefactorer {

    private static final Logger LOGGER = ToolLog.getLogger(JavaFileRefactorer.class);

    private final ToolConfiguration configuration;
    private final RefactorTarget target;
    private final RefactorPlan plan;

    JavaFileRefactorer(ToolConfiguration configuration, RefactorTarget target, RefactorPlan plan) {
        this.configuration = configuration;
        this.target = target;
        this.plan = plan;
    }

    public void refactor() throws IOException {
        if (configuration.dryRun()) {
            target.addDecision("Dry run enabled - skipping modifications");
            return;
        }

        CompilationUnit compilationUnit = StaticJavaParser.parse(target.file());
        Path backup = createBackup(target.file());
        List<Path> createdFiles = new ArrayList<>();
        boolean modified = false;

        try {
            modified |= extractStaticNestedClasses(compilationUnit, createdFiles);
            modified |= extractStaticMethodGroups(compilationUnit, createdFiles);

            if (modified) {
                Files.writeString(target.file(), compilationUnit.toString());
                target.addDecision("File rewritten after extraction");
            } else {
                target.addDecision("No extractable components found - file left untouched");
            }
        } catch (Exception ex) {
            LOGGER.error("Refactor failed for {} - reverting changes", target.file(), ex);
            Files.copy(backup, target.file(), StandardCopyOption.REPLACE_EXISTING);
            for (Path created : createdFiles) {
                Files.deleteIfExists(created);
            }
            throw ex;
        } finally {
            Files.deleteIfExists(backup);
        }
    }

    private Path createBackup(Path file) throws IOException {
        Path backup = Files.createTempFile("refactor-backup", ".java");
        Files.copy(file, backup, StandardCopyOption.REPLACE_EXISTING);
        return backup;
    }

    private boolean extractStaticNestedClasses(CompilationUnit unit, List<Path> createdFiles) throws IOException {
        Set<String> desired = new HashSet<>(plan.nestedClassesToExtract());
        if (desired.isEmpty()) {
            return false;
        }

        String packageName = unit.getPackageDeclaration().map(pd -> pd.getName().asString()).orElse("");
        String baseName = target.file().getFileName().toString().replaceFirst("\\.java$", "");
        Path refFolder = target.file().getParent().resolve("ref_" + baseName);
        Files.createDirectories(refFolder);

        boolean modified = false;
        List<ClassOrInterfaceDeclaration> nestedClasses = unit.findAll(ClassOrInterfaceDeclaration.class);
        for (ClassOrInterfaceDeclaration nestedClass : nestedClasses) {
            if (!nestedClass.isNestedType() || !nestedClass.isStatic()) {
                continue;
            }
            if (!desired.contains(nestedClass.getNameAsString())) {
                continue;
            }
            if (referencesOuterState(nestedClass)) {
                target.addDecision("Skipped nested class " + nestedClass.getNameAsString() + " during execution because outer references were detected");
                continue;
            }

            CompilationUnit newUnit = new CompilationUnit();
            if (!packageName.isEmpty()) {
                newUnit.setPackageDeclaration(packageName);
            }
            for (ImportDeclaration importDecl : unit.getImports()) {
                newUnit.addImport(importDecl);
            }

            ClassOrInterfaceDeclaration copy = nestedClass.clone();
            copy.removeModifier(com.github.javaparser.ast.Modifier.Keyword.STATIC);
            newUnit.addType(copy);

            String newClassName = nestedClass.getNameAsString();
            Path newFile = refFolder.resolve(newClassName + ".java");
            Files.writeString(newFile, newUnit.toString());
            createdFiles.add(newFile);
            modified = true;

            // Replace nested class body with delegation stub extending new implementation
            nestedClass.getMembers().clear();
            String qualifiedName = packageName.isEmpty() ? newClassName : packageName + "." + newClassName;
            nestedClass.setExtendedTypes(new NodeList<>(StaticJavaParser.parseClassOrInterfaceType(qualifiedName)));
            nestedClass.getImplementedTypes().clear();
            nestedClass.getModifiers().removeIf(modifier -> modifier.getKeyword() == com.github.javaparser.ast.Modifier.Keyword.STATIC);
            nestedClass.addModifier(com.github.javaparser.ast.Modifier.Keyword.STATIC);
            target.addDecision("Extracted static nested class " + newClassName);
        }

        return modified;
    }

    private boolean referencesOuterState(ClassOrInterfaceDeclaration declaration) {
        AtomicBoolean found = new AtomicBoolean(false);
        declaration.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ThisExpr n, Void arg) {
                super.visit(n, arg);
                found.set(true);
            }
        }, null);
        return found.get();
    }

    private boolean extractStaticMethodGroups(CompilationUnit unit, List<Path> createdFiles) throws IOException {
        Optional<TypeDeclaration<?>> primaryType = unit.getPrimaryType();
        if (primaryType.isEmpty()) {
            return false;
        }

        if (!(primaryType.get() instanceof ClassOrInterfaceDeclaration outerClass)) {
            return false;
        }

        Map<String, List<String>> planGroups = plan.methodGroups();
        if (planGroups.isEmpty()) {
            return false;
        }
        boolean modified = false;
        String packageName = unit.getPackageDeclaration().map(pd -> pd.getName().asString()).orElse("");
        String baseName = target.file().getFileName().toString().replaceFirst("\\.java$", "");
        Path refFolder = target.file().getParent().resolve("ref_" + baseName);
        Files.createDirectories(refFolder);

        for (Map.Entry<String, List<String>> entry : planGroups.entrySet()) {
            String group = entry.getKey();
            List<String> methodNames = entry.getValue();
            if (methodNames.isEmpty()) {
                continue;
            }

            List<MethodDeclaration> methods = new ArrayList<>();
            for (String methodName : methodNames) {
                Optional<MethodDeclaration> method = outerClass.getMethodsByName(methodName).stream()
                        .filter(MethodDeclaration::isStatic)
                        .filter(m -> m.getBody().isPresent())
                        .findFirst();
                if (method.isEmpty()) {
                    target.addDecision("Planned method " + methodName + " missing or unsuitable - skipping");
                    continue;
                }
                MethodDeclaration actual = method.get();
                if (referencesPrivateMembers(actual, outerClass)) {
                    target.addDecision("Skipping static method " + methodName + " due to private member usage detected during execution");
                    continue;
                }
                methods.add(actual);
            }

            if (methods.isEmpty()) {
                continue;
            }

            CompilationUnit newUnit = new CompilationUnit();
            if (!packageName.isEmpty()) {
                newUnit.setPackageDeclaration(packageName);
            }
            for (ImportDeclaration importDecl : unit.getImports()) {
                newUnit.addImport(importDecl);
            }

            ClassOrInterfaceDeclaration helperClass = new ClassOrInterfaceDeclaration();
            helperClass.setName(group);
            helperClass.addModifier(com.github.javaparser.ast.Modifier.Keyword.PUBLIC);

            for (MethodDeclaration method : methods) {
                MethodDeclaration copy = method.clone();
                helperClass.addMember(copy);

                BlockStmt delegation = new BlockStmt();
                if (method.getType().isVoidType()) {
                    delegation.addStatement(new ExpressionStmt(StaticJavaParser.parseExpression(group + "." + method.getNameAsString() + "(" + buildArgumentList(method) + ")")));
                } else {
                    delegation.addStatement(new ReturnStmt(StaticJavaParser.parseExpression(group + "." + method.getNameAsString() + "(" + buildArgumentList(method) + ")")));
                }
                method.setBody(delegation);
                target.addDecision("Moved static method " + method.getNameAsString() + " to group " + group);
            }

            newUnit.addType(helperClass);
            Path newFile = refFolder.resolve(group + ".java");
            Files.writeString(newFile, newUnit.toString());
            createdFiles.add(newFile);
            modified = true;
        }

        return modified;
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

        AtomicBoolean found = new AtomicBoolean(false);
        method.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(NameExpr n, Void arg) {
                super.visit(n, arg);
                if (privateMembers.contains(n.getNameAsString())) {
                    found.set(true);
                }
            }
        }, null);
        return found.get();
    }

    private String buildArgumentList(MethodDeclaration method) {
        return String.join(", ", method.getParameters().stream()
                .map(p -> p.getName().asString())
                .toList());
    }
}
