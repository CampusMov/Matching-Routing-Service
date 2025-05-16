package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

public record GetAllPassengerRequestsByCarpoolIdAndStatusIsPendingQuery(
        String carpoolId
) {
    public GetAllPassengerRequestsByCarpoolIdAndStatusIsPendingQuery {
        if (carpoolId.isEmpty() || carpoolId.isBlank()) {
            throw new IllegalArgumentException("Carpool ID cannot be null or empty");
        }
    }
}
