package com.igor101.dojavamonitor.logs;

import com.igor101.dojavamonitor.logs.model.LogData;
import com.igor101.dojavamonitor.logs.model.LogLevel;
import com.igor101.dojavamonitor.logs.repository.LogsRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;

public class LogsService {

    private static final Logger log = LoggerFactory.getLogger(LogsController.class);

    private final LogsConverter logsConverter;
    private final List<LogsRepository> logsRepositories;
    private final MeterRegistry meterRegistry;

    public LogsService(LogsConverter logsConverter,
                       List<LogsRepository> logsRepositories,
                       MeterRegistry meterRegistry) {
        this.logsConverter = logsConverter;
        this.logsRepositories = logsRepositories;
        this.meterRegistry = meterRegistry;
    }

    public void handle(List<LogData> logs, Instant receivedTimestamp) {
        var records = logs.stream()
                .map(l -> {
                    var r = logsConverter.converted(l, receivedTimestamp);
                    countApplicationLogs(r.application(), r.level());
                    return r;
                })
                .toList();

        for (var lr : logsRepositories) {
            try {
                log.info("About to store {} log records in {} repo...", records.size(), lr);
                lr.store(records);
                log.info("{} log records stored", records.size());
            } catch (Exception e) {
                log.error("Fail to store logs...", e);
            }
        }
    }

    private void countApplicationLogs(String application, LogLevel logLevel) {
        if (logLevel == LogLevel.ERROR) {
            meterRegistry.counter("application_log_errors_total", "application", application)
                    .increment();
        } else if (logLevel == LogLevel.WARNING) {
            meterRegistry.counter("application_log_warnings_total", "application", application)
                    .increment();
        }
    }
}
