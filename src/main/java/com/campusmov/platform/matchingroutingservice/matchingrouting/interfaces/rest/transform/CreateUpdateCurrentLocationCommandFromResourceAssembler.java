package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.UpdateCurrentLocationRouteCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.UpdateCurrentLocationRouteCarpoolResource;

public class CreateUpdateCurrentLocationCommandFromResourceAssembler {
    public static UpdateCurrentLocationRouteCommand toCommandFromResource(String routeId, UpdateCurrentLocationRouteCarpoolResource resource) {
        return UpdateCurrentLocationRouteCommand.builder()
                .routeId(routeId)
                .currentLocation(Location.builder()
                        .longitude(resource.longitude())
                        .latitude(resource.latitude())
                        .build())
                .build();
    }
}
