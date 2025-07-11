package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

public record GetRouteByCarpoolId(
        String carpoolId
) {
    public GetRouteByCarpoolId {
        if (carpoolId == null || carpoolId.isBlank()) {
            throw new IllegalArgumentException("Carpool ID cannot be null or blank");
        }
    }
}
