package com.minecraft.tools.refactor.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Simple cache keyed by file attributes that avoids counting the same file
 * multiple times within a single tool execution.
 */
public final class LineCountCache {

    private final Map<Path, CacheEntry> cache = new ConcurrentHashMap<>();
    private final LongAdder hits = new LongAdder();
    private final LongAdder misses = new LongAdder();

    public LineCountResult count(Path file, int shortCircuitLimit) throws IOException {
        Path normalized = file.toAbsolutePath().normalize();
        BasicFileAttributes attrs = Files.readAttributes(normalized, BasicFileAttributes.class);
        CacheKey key = new CacheKey(attrs.lastModifiedTime().toMillis(), attrs.size());
        CacheEntry cached = cache.get(normalized);
        if (cached != null && cached.key.equals(key)) {
            hits.increment();
            return cached.result;
        }

        misses.increment();
        LineCountResult result = EffectiveLineCounter.countEffectiveLines(normalized, shortCircuitLimit);
        cache.put(normalized, new CacheEntry(key, result));
        return result;
    }

    public long hits() {
        return hits.longValue();
    }

    public long misses() {
        return misses.longValue();
    }

    private record CacheKey(long lastModified, long size) {
    }

    private record CacheEntry(CacheKey key, LineCountResult result) {
    }
}
