package com.igor101.dojavamonitor.logs;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logs")
public class LogsController {

    private final Logger LOG = LoggerFactory.getLogger(LogsController.class);

    private final ElasticsearchClient elasticsearchClient;

    public LogsController(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @PostMapping
    public void append(@RequestBody LogsDataApi logs) {
        LOG.info("Getting logs!");
        System.out.println(logs);
    }
}
