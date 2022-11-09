package com.igor101.dojavamonitor.logs.model;

import java.time.Instant;

public record LogRecord(String application,
                        LogLevel level,
                        String log,
                        Instant from,
                        Instant to) {
}
