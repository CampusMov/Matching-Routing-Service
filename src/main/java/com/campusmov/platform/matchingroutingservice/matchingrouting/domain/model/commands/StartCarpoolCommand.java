package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import lombok.Builder;

@Builder
public record StartCarpoolCommand(
        String carpoolId,
        Location currentLocation
) {
    public StartCarpoolCommand {
        if (carpoolId.isBlank() || carpoolId.isEmpty()) {
            throw new IllegalArgumentException("Carpool ID cannot be null or empty");
        }
        if (currentLocation == null) {
            throw new IllegalArgumentException("Current location cannot be null");
        }
    }
}
