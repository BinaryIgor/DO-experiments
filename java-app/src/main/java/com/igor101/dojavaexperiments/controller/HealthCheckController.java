package com.igor101.dojavaexperiments.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @PostConstruct
    public void init() {
        System.out.println("Checking all env variables...");
        System.getenv().forEach((k, v) -> System.out.printf("%s - %s\n", k, v));
    }

    @GetMapping
    public void healthCheck() {
        System.out.println("Checking system health...");
    }
}
