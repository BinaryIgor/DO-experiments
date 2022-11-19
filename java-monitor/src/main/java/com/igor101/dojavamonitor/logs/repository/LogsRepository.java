package com.igor101.dojavamonitor.logs.repository;

import com.igor101.dojavamonitor.logs.model.LogRecord;

import java.util.List;

public interface LogsRepository {
    void store(List<LogRecord> logs);
}
