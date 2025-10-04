package com.minecraft.tools.refactor.util;

import org.slf4j.Logger;

import java.nio.file.Path;

/**
 * Emits lightweight progress updates for long running operations such as
 * scanning or refactor execution.
 */
public final class ProgressTracker {

    private final Logger logger;
    private final long total;
    private final int step;

    private long processed;
    private long nextThreshold;

    public ProgressTracker(Logger logger, long total) {
        this(logger, total, 5);
    }

    public ProgressTracker(Logger logger, long total, int stepPercent) {
        this.logger = logger;
        this.total = total;
        this.step = Math.max(1, stepPercent);
        this.nextThreshold = step;
    }

    public void increment(Path current) {
        processed++;
        if (total > 0) {
            long percent = processed * 100 / total;
            if (percent >= nextThreshold) {
                logger.info("Progress: {}% ({} / {}) - {}", percent, processed, total, current);
                nextThreshold = Math.min(100, percent + step);
            }
        } else if (processed % 250 == 0) {
            logger.info("Processed {} items so far - last {}", processed, current);
        }
    }
}
