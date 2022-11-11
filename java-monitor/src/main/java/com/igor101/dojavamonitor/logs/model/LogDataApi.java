package com.igor101.dojavamonitor.logs.model;

public record LogDataApi(String containerName,
                         String containerId,
                         long fromTimestamp,
                         long toTimestamp,
                         String log) {
}
