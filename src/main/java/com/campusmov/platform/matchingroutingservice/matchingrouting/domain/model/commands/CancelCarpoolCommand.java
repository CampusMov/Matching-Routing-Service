package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;

import lombok.Builder;

@Builder
public record CancelCarpoolCommand(
    String carpoolId
) {
    public CancelCarpoolCommand {
        if (carpoolId == null || carpoolId.isBlank()) {
            throw new IllegalArgumentException("Carpool ID cannot be null or blank");
        }
    }
}
