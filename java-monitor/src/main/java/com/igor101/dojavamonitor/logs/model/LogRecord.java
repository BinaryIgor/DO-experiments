package com.igor101.dojavamonitor.logs.model;

import java.time.Instant;

public record LogRecord(String source,
                        String application,
                        LogLevel level,
                        String log,
                        Instant receivedTimestamp,
                        Instant fromTimestamp,
                        Instant toTimestamp) {
}
