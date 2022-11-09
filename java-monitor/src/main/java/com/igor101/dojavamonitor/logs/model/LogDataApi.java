package com.igor101.dojavamonitor.logs.model;

public record LogDataApi(String container_name,
                         String container_id,
                         long from,
                         long to,
                         String log) {
}
