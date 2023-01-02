package me.lenglet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventConsumerOrchestrator {

    private final EventConsumer eventConsumer;


    public EventConsumerOrchestrator(
            EventConsumer eventConsumer
    ) {
        this.eventConsumer = eventConsumer;
    }

    public void start() {

        ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleWithFixedDelay(() -> {
                    while (this.eventConsumer.consume());
                },
                0L,
                30L,
                TimeUnit.SECONDS);

    }
}
