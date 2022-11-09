package com.igor101.dojavamonitor.logs.model;

public record LogData(String application,
                     long from,
                     long to,
                     String log) {
}
