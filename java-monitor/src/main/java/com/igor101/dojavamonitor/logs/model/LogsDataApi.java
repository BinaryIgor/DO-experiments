package com.igor101.dojavamonitor.logs.model;

import java.util.List;

public record LogsDataApi(String source,
                          List<LogDataApi> logs) {
}
