package com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.brokers.kafka;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.PassengerRequestEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

@Configuration
public class PassengerRequestEventSource {
    private final Queue<Message<?>> eventQueue = new LinkedList<>();

    @Bean
    public Supplier<Message<?>> passengerRequestSupplier() {
        return this.eventQueue::poll;
    }

    public void publishEvent(PassengerRequestEvent event) {
        this.eventQueue.add(MessageBuilder.withPayload(event).build());
    }
}
