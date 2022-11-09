package com.igor101.dojavamonitor;

import com.igor101.dojavamonitor.logs.model.ApplicationLogMapping;
import com.igor101.dojavamonitor.logs.model.LogMapping;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Collection;

@ConfigurationProperties(prefix = "log-mappings")
@ConstructorBinding
public record LogMappingsConfig(Collection<ApplicationLogMapping> applications,
                                LogMapping defaultMapping) {
}
