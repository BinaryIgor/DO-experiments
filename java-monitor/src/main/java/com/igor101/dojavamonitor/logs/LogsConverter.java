package com.igor101.dojavamonitor.logs;

import com.igor101.dojavamonitor.logs.model.LogLevel;
import com.igor101.dojavamonitor.logs.model.LogRecord;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class LogsConverter {

    //TODO
    public LogRecord converted(String application,
                               String log,
                               long from,
                               long to) {
        return new LogRecord(application, LogLevel.ERROR, log,
                Instant.ofEpochSecond(from), Instant.ofEpochSecond(to));
    }
}
