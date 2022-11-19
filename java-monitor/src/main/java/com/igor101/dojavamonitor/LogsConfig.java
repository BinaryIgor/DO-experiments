package com.igor101.dojavamonitor;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.igor101.dojavamonitor.logs.LogsConverter;
import com.igor101.dojavamonitor.logs.LogsService;
import com.igor101.dojavamonitor.logs.repository.ElasticLogsRepository;
import com.igor101.dojavamonitor.logs.repository.FileLogsRepository;
import com.igor101.dojavamonitor.logs.repository.LogsRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties({LogMappingsConfig.class, LogsStorageConfig.class})
public class LogsConfig {

    @Bean
    public LogsConverter logsConverter(LogMappingsConfig config) {
        return new LogsConverter(config.applications(), config.defaultMapping());
    }

    @Bean
    public List<LogsRepository> logsRepositories(LogsStorageConfig config,
                                                 ElasticsearchClient elasticsearchClient) {
        var repositories = new ArrayList<LogsRepository>();

        if (config.file().enabled()) {
            repositories.add(new FileLogsRepository(new File(config.file().path()), 5_000_000, 3));
        }
        if (config.elasticsearch().enabled()) {
            repositories.add(new ElasticLogsRepository(elasticsearchClient));
        }

        System.out.println("Config..." + config);

        return repositories;
    }

    @Bean
    public LogsService logsService(LogsConverter logsConverter,
                                   List<LogsRepository> logsRepositories,
                                   MeterRegistry meterRegistry) {
        return new LogsService(logsConverter, logsRepositories, meterRegistry);
    }
}
