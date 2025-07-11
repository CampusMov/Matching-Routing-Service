package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import lombok.Builder;

@Builder
public record CreateRouteCommand(
        CarpoolId carpoolId,
        Location origin,
        Location destination
) {
}
