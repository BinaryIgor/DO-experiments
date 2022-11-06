package com.igor101.dojavaexperiments.controller;

import com.igor101.dojavaexperiments.logging.HttpLogAppender;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    private final Counter healthCheckCounter;

    public HealthCheckController(MeterRegistry meterRegistry) {
        healthCheckCounter = meterRegistry.counter("health_check");
        var sendLogsFailureCounter = meterRegistry.counter("send_logs_failure");

        HttpLogAppender.setSendLogsFailureListener(sendLogsFailureCounter::increment);
    }

    @PostConstruct
    public void init() {
        System.out.println("Checking all env variables...");
        System.getenv().forEach((k, v) -> System.out.printf("%s - %s\n", k, v));
    }

    @GetMapping
    public void healthCheck() {
        System.out.println("Checking system health...");
        healthCheckCounter.increment();
    }
}
