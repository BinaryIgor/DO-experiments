package com.igor101.dojavamonitor.logs;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import com.igor101.dojavamonitor.logs.model.LogData;
import com.igor101.dojavamonitor.logs.model.LogLevel;
import com.igor101.dojavamonitor.logs.model.LogRecord;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;

public class LogsService {

    private final Logger LOG = LoggerFactory.getLogger(LogsController.class);

    private final LogsConverter logsConverter;
    private final ElasticsearchClient elasticsearchClient;
    private final MeterRegistry meterRegistry;

    public LogsService(LogsConverter logsConverter,
                       ElasticsearchClient elasticsearchClient,
                       MeterRegistry meterRegistry) {
        this.logsConverter = logsConverter;
        this.elasticsearchClient = elasticsearchClient;
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
        try {
            LOG.info("About to send {} log records...", records.size());
            sendLogs(records);
            LOG.info("{} log records sent", records.size());
        } catch (Exception e) {
            LOG.error("Fail to send logs...", e);
        }
    }

    private void sendLogs(List<LogRecord> logs) throws Exception {
        var bulkRequest = new BulkRequest.Builder();

        //TODO: improve index
        for (var l : logs) {
            bulkRequest.operations(op -> op.index(idx -> idx.index("logs").document(l)));
        }

        var result = elasticsearchClient.bulk(bulkRequest.build());

        if (result.errors()) {
            LOG.error("Errors during sending to elastic...");
            result.items().forEach(i -> System.out.println(i.error()));
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
