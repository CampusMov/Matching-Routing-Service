package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads.PassengerRequestAcceptedPayload;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads.PassengerRequestRejectedPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerRequestWebSocketPublisherService {
    private final SimpMessagingTemplate messagingTemplate;
    public void handleAcceptPassengerRequest(PassengerRequestAcceptedPayload passengerRequestAcceptedPayload) {
        String destination = "/topic/passenger-request/" + passengerRequestAcceptedPayload.id()+ "/status";
        messagingTemplate.convertAndSend(destination, passengerRequestAcceptedPayload);
    }

    public void handleRejectPassengerRequest(PassengerRequestRejectedPayload passengerRequestRejectedPayload) {
        String destination = "/topic/passenger-request/" + passengerRequestRejectedPayload.id() + "/status";
        messagingTemplate.convertAndSend(destination, passengerRequestRejectedPayload);
    }
}
