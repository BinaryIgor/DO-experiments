package com.igor101.dojavamonitor.logs;

import com.igor101.dojavamonitor.logs.model.*;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public class LogsConverter {

    private final Collection<ApplicationLogMapping> applicationsLogMappings;
    private final LogMapping defaultLogMapping;

    public LogsConverter(Collection<ApplicationLogMapping> applicationsLogMappings,
                         LogMapping defaultLogMapping) {
        this.applicationsLogMappings = applicationsLogMappings;
        this.defaultLogMapping = defaultLogMapping;
    }


    public LogRecord converted(LogData log, Instant receivedTimestamp) {
        return new LogRecord(log.source(), log.application(), logLevel(log), log.log(),
                receivedTimestamp, Instant.ofEpochSecond(log.from()), Instant.ofEpochSecond(log.to()));
    }

    private LogLevel logLevel(LogData log) {
        var mapping = defaultLogMapping;

        for (var m : applicationsLogMappings) {
            if (containsAny(log.application(), m.supportedApplicationsKeywords())) {
                mapping = m.mapping();
                break;
            }
        }

        var logMessage = log.log();

        if (containsAny(logMessage, mapping.errorKeywords())) {
            return containsAny(logMessage, mapping.messagesToSwallow()) ? LogLevel.INFO : LogLevel.ERROR;
        }
        if (containsAny(logMessage, mapping.warningKeywords())) {
            return containsAny(logMessage, mapping.messagesToSwallow()) ? LogLevel.INFO : LogLevel.WARNING;
        }

        return LogLevel.INFO;
    }

    private boolean containsAny(String string, List<String> keywords) {
        return keywords.stream().anyMatch(string::contains);
    }

}
