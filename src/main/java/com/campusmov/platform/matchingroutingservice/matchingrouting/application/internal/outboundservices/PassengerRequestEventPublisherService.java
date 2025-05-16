package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.PassengerRequestAcceptedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.brokers.kafka.PassengerRequestEventSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class PassengerRequestEventPublisherService {
    private final PassengerRequestEventSource passengerRequestEventSource;

    @TransactionalEventListener
    public void handlePassengerRequestEvent(PassengerRequestAcceptedEvent event) {
        passengerRequestEventSource.publishEvent(event);
    }
}
