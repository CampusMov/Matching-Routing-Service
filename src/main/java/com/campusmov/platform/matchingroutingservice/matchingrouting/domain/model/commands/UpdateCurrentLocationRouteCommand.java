package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import lombok.Builder;

@Builder
public record UpdateCurrentLocationRouteCommand(
        String routeId,
        Location currentLocation
) {
}
