package com.igor101.dojavaexperiments.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HttpLogAppender extends AppenderBase<ILoggingEvent> {

    private final Queue<ILoggingEvent> toSendEvents = new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService executor;
    private String endpoint;

    public HttpLogAppender() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(this::sendAllEvents, 10, 10, TimeUnit.SECONDS);
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    protected void append(ILoggingEvent event) {
        toSendEvents.add(event);
    }

    private void sendAllEvents() {
        try {
            var events = new ArrayList<ILoggingEvent>();
            while (!toSendEvents.isEmpty()) {
                events.add(toSendEvents.poll());
            }
            System.out.printf("Sending %d events to %s...\n", events.size(), endpoint);
        } catch (Exception e) {
            System.err.printf("Fail to send events to %s...\n", endpoint);
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.out.println("Stopping appender...");
        executor.shutdown();
        sendAllEvents();
        System.out.println("Appender stopped");
    }
}
