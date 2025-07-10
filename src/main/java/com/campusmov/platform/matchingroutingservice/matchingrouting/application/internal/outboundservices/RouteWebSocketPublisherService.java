package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads.UpdateRouteCurrentLocationPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteWebSocketPublisherService {
    private final SimpMessagingTemplate messagingTemplate;

    public void handleRouteUpdateCurrentLocation(UpdateRouteCurrentLocationPayload routeUpdatePayload) {
        String destination = "/topic/carpool/" + routeUpdatePayload.carpoolId() + "/route";
        messagingTemplate.convertAndSend(destination, routeUpdatePayload);
    }
}
