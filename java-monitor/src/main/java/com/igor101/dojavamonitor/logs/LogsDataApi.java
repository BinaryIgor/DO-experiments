package com.igor101.dojavamonitor.logs;

import java.util.List;

public record LogsDataApi(String source,
                          List<LogDataApi> logs) {
}
