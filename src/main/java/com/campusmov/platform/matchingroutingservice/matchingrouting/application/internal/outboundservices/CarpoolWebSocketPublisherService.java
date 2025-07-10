package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads.CarpoolCompletedPayload;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads.CarpoolStartedPayload;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads.CarpoolCancelledPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarpoolWebSocketPublisherService {
    private final SimpMessagingTemplate messagingTemplate;
    public void handleStartedCarpool(CarpoolStartedPayload carpoolStartedPayload) {
        String destination = "/topic/carpool/" + carpoolStartedPayload.id() + "/status";
        messagingTemplate.convertAndSend(destination, carpoolStartedPayload);
    }

    public void handleCancelledCarpool(CarpoolCancelledPayload carpoolCancelledPayload) {
        String destination = "/topic/carpool/" + carpoolCancelledPayload.id() + "/status";
        messagingTemplate.convertAndSend(destination, carpoolCancelledPayload);
    }

    public void handleCompletedCarpool(CarpoolCompletedPayload carpoolCompletedPayload) {
        String destination = "/topic/carpool/" + carpoolCompletedPayload.id() + "/status";
        messagingTemplate.convertAndSend(destination, carpoolCompletedPayload);
    }
}
