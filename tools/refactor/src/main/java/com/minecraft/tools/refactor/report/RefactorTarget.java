package com.minecraft.tools.refactor.report;

import com.minecraft.tools.refactor.planning.PlanStatus;
import com.minecraft.tools.refactor.planning.RefactorPlan;
import com.minecraft.tools.refactor.util.LineCountResult;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents a Java file analysed for refactoring.
 */
public final class RefactorTarget {

    private final Path file;
    private final LineCountResult lineCount;
    private final List<String> decisions = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();
    private boolean refactored;
    private RefactorPlan plan;
    private PlanStatus planStatus = PlanStatus.ANALYSED;

    public RefactorTarget(Path file, LineCountResult lineCount) {
        this.file = file;
        this.lineCount = lineCount;
    }

    public Path file() {
        return file;
    }

    public int effectiveLines() {
        return lineCount.lines();
    }

    public LineCountResult lineCount() {
        return lineCount;
    }

    public boolean lineCountTruncated() {
        return lineCount.truncated();
    }

    public void addDecision(String decision) {
        decisions.add(decision);
    }

    public void addError(String error) {
        errors.add(error);
    }

    public void attachPlan(RefactorPlan plan) {
        this.plan = plan;
        this.planStatus = plan.status();
    }

    public List<String> decisions() {
        return Collections.unmodifiableList(decisions);
    }

    public List<String> errors() {
        return Collections.unmodifiableList(errors);
    }

    public boolean refactored() {
        return refactored;
    }

    public void markRefactored() {
        this.refactored = true;
    }

    public Optional<RefactorPlan> plan() {
        return Optional.ofNullable(plan);
    }

    public PlanStatus planStatus() {
        return planStatus;
    }

    public void updatePlanStatus(PlanStatus status) {
        this.planStatus = status;
    }
}
