package com.igor101.dojavamonitor.logs;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.igor101.dojavamonitor.logs.model.LogData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogsService {

    private final Logger LOG = LoggerFactory.getLogger(LogsController.class);

    private final LogsConverter logsConverter;
    private final ElasticsearchClient elasticsearchClient;

    public LogsService(LogsConverter logsConverter,
                       ElasticsearchClient elasticsearchClient) {
        this.logsConverter = logsConverter;
        this.elasticsearchClient = elasticsearchClient;
    }

    public void handle(List<LogData> logs) {

    }
}
