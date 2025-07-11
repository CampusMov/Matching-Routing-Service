package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.PassengerRequestAcceptedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.brokers.kafka.PassengerRequestAcceptedEventSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class PassengerRequestEventPublisherService {
    private final PassengerRequestAcceptedEventSource passengerRequestAcceptedEventSource;

    @TransactionalEventListener
    public void handlePassengerRequestEvent(PassengerRequestAcceptedEvent event) {
        passengerRequestAcceptedEventSource.publishAcceptedEvent(event);
    }
}
