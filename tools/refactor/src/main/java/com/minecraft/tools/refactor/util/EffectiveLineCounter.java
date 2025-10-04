package com.minecraft.tools.refactor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Counts effective lines of Java code excluding comments and blanks.
 */
public final class EffectiveLineCounter {

    private EffectiveLineCounter() {
    }

    public static LineCountResult countEffectiveLines(Path file, int shortCircuitLimit) throws IOException {
        boolean inBlockComment = false;
        int count = 0;
        boolean truncated = false;

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                if (inBlockComment) {
                    if (trimmed.contains("*/")) {
                        inBlockComment = false;
                    }
                    continue;
                }

                if (trimmed.startsWith("//")) {
                    continue;
                }

                if (trimmed.startsWith("/*")) {
                    if (!trimmed.endsWith("*/")) {
                        inBlockComment = true;
                    }
                    continue;
                }

                if (trimmed.contains("/*")) {
                    int start = trimmed.indexOf("/*");
                    int end = trimmed.indexOf("*/", start + 2);
                    if (end == -1) {
                        inBlockComment = true;
                        trimmed = trimmed.substring(0, start).trim();
                    } else {
                        trimmed = (trimmed.substring(0, start) + " " + trimmed.substring(end + 2)).trim();
                    }
                }

                if (!trimmed.isEmpty()) {
                    count++;
                    if (shortCircuitLimit > 0 && count > shortCircuitLimit) {
                        truncated = true;
                        break;
                    }
                }
            }
        }

        return new LineCountResult(count, truncated);
    }
}
