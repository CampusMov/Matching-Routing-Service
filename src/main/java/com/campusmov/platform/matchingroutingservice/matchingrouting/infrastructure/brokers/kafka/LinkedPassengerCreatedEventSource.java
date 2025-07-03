package com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.brokers.kafka;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.LinkedPassengerCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

@Configuration
public class LinkedPassengerCreatedEventSource {
    private final Queue<Message<?>> eventQueue = new LinkedList<>();

    @Bean
    public Supplier<Message<?>> linkedPassengerCreatedSupplier() {
        return this.eventQueue::poll;
    }

    public void publishCreatedEvent(LinkedPassengerCreatedEvent event) {
        this.eventQueue.add(MessageBuilder.withPayload(event).build());
    }
}
