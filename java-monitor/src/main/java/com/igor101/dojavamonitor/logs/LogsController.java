package com.igor101.dojavamonitor.logs;

import com.igor101.dojavamonitor.logs.model.LogData;
import com.igor101.dojavamonitor.logs.model.LogsDataApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("logs")
public class LogsController {

    private final Logger LOG = LoggerFactory.getLogger(LogsController.class);

    private final LogsService service;

    public LogsController(LogsService service) {
        this.service = service;
    }

    @PostMapping
    public void append(@RequestBody LogsDataApi apiLogs) {
        LOG.info("Getting logs {}!", apiLogs.logs().size());

        var logs = apiLogs.logs().stream()
                .map(l -> new LogData(l.containerName(), l.fromTimestamp(), l.toTimestamp(), l.log()))
                .toList();

        service.handle(logs, Instant.now());
    }
}
