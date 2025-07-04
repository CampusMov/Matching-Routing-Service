package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.events;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.PassengerRequestAcceptedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.CarpoolCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.RouteCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.events.transforms.CreateLinkedPassengerCommandFromEventAssembler;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.events.transforms.CreateWayPointCommandFromEventAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerRequestEventHandler {
    private final CarpoolCommandService carpoolCommandService;
    private final RouteCommandService routeCommandService;

    @EventListener
    public void handlePassengerRequestAcceptedEventToCreatePassengerRequest(PassengerRequestAcceptedEvent event) {
        var command = CreateLinkedPassengerCommandFromEventAssembler.toCommandFromEvent(event);
        carpoolCommandService.handle(command);
    }

    @EventListener
    public void handlePassengerRequestAcceptedEventToCreateWayPoint(PassengerRequestAcceptedEvent event) {
        var command = CreateWayPointCommandFromEventAssembler.toCommandFromEvent(event);
        routeCommandService.handle(command);
    }
}
