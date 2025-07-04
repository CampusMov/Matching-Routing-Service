package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.events;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.CarpoolCreatedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.RouteCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.events.transforms.CreateRouteCommandFromEventAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarpoolEventHandler {
    private final RouteCommandService routeCommandService;

    @EventListener
    public void handleCarpoolCreatedEvent(CarpoolCreatedEvent event) {
        var command = CreateRouteCommandFromEventAssembler.toCommandFromEvent(event);
        routeCommandService.handle(command);
    }
}
