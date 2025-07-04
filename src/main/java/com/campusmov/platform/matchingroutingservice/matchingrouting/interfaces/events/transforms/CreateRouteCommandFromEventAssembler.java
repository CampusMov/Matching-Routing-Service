package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.events.transforms;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateRouteCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.CarpoolCreatedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;

public class CreateRouteCommandFromEventAssembler {
    public static CreateRouteCommand toCommandFromEvent(CarpoolCreatedEvent event) {
        return CreateRouteCommand.builder()
                .carpoolId(new CarpoolId(event.getCarpoolId()))
                .origin(new Location(
                        event.getOrigin().getName(),
                        event.getOrigin().getAddress(),
                        event.getOrigin().getLongitude(),
                        event.getOrigin().getLatitude()))
                .destination(new Location(
                        event.getDestination().getName(),
                        event.getDestination().getAddress(),
                        event.getDestination().getLongitude(),
                        event.getDestination().getLatitude()))
                .build();
    }
}
