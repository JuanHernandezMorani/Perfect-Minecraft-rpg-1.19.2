package com.minecraft.tools.refactor.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralised logger factory.
 */
public final class ToolLog {

    private ToolLog() {
    }

    public static Logger getLogger(Class<?> type) {
        return LoggerFactory.getLogger(type);
    }
}
