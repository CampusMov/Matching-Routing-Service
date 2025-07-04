package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.CarpoolCreatedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.LinkedPassengerCreatedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.brokers.kafka.CarpoolCreatedEventSource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.brokers.kafka.LinkedPassengerCreatedEventSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class CarpoolEventPublisherService {
    private final LinkedPassengerCreatedEventSource linkedPassengerCreatedEventSource;
    private final CarpoolCreatedEventSource carpoolCreatedEventSource;

    @TransactionalEventListener
    public void handleCarpoolCreatedEvent(CarpoolCreatedEvent event) {
        carpoolCreatedEventSource.publishCreatedEvent(event);
    }

    @TransactionalEventListener
    public void handleLinkedPassengerCreatedEvent(LinkedPassengerCreatedEvent event) {
        linkedPassengerCreatedEventSource.publishCreatedEvent(event);
    }
}
