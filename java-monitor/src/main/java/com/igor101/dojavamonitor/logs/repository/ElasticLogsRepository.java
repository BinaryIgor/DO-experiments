package com.igor101.dojavamonitor.logs.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import com.igor101.dojavamonitor.logs.model.LogRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ElasticLogsRepository implements LogsRepository {

    private static final Logger log = LoggerFactory.getLogger(ElasticLogsRepository.class);
    private final ElasticsearchClient elasticsearchClient;

    public ElasticLogsRepository(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public void store(List<LogRecord> logs) {
        try {
            var bulkRequest = new BulkRequest.Builder();

            //TODO: improve index
            for (var l : logs) {
                bulkRequest.operations(op -> op.index(idx -> idx.index("logs").document(l)));
            }

            var result = elasticsearchClient.bulk(bulkRequest.build());

            if (result.errors()) {
                log.error("Errors during sending to elastic...");
                result.items().forEach(i -> System.out.println(i.error()));
            }
        } catch (Exception e) {
            log.error("Fail to send logs...", e);
            throw new RuntimeException(e);
        }
    }
}
