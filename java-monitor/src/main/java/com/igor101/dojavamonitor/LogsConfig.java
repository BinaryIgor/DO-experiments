package com.igor101.dojavamonitor;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.igor101.dojavamonitor.logs.LogsConverter;
import com.igor101.dojavamonitor.logs.LogsService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LogMappingsConfig.class)
public class LogsConfig {

    @Bean
    public LogsConverter logsConverter(LogMappingsConfig config) {
        return new LogsConverter(config.applications(), config.defaultMapping());
    }

    @Bean
    public LogsService logsService(LogsConverter logsConverter,
                                   ElasticsearchClient elasticsearchClient) {
        return new LogsService(logsConverter, elasticsearchClient);
    }
}
