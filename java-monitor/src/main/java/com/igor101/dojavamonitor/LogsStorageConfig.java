package com.igor101.dojavamonitor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "logs-storage")
@ConstructorBinding
public record LogsStorageConfig(FileConfig file,
                                ElasticsearchConfig elasticsearch) {

    public record FileConfig(boolean enabled,
                             String path) {
    }

    public record ElasticsearchConfig(boolean enabled,
                                      String path) {}
}
