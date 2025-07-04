package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.RouteId;
import lombok.Builder;

@Builder
public record CreateWayPointCommand(
        RouteId routeId,
        PassengerId passengerId,
        Location location
) {
}
