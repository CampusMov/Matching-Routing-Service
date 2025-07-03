package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.LinkedPassengerCreatedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.brokers.kafka.LinkedPassengerCreatedEventSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class CarpoolEventPublisherService {
    private final LinkedPassengerCreatedEventSource linkedPassengerCreatedEventSource;

    @TransactionalEventListener
    public void handleLinkedPassengerCreatedEvent(LinkedPassengerCreatedEvent event) {
        linkedPassengerCreatedEventSource.publishCreatedEvent(event);
    }
}
