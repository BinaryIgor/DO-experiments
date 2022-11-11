package com.igor101.dojavamonitor.logs.model;

public record LogData(String source,
                      String application,
                      long from,
                      long to,
                      String log) {
}
