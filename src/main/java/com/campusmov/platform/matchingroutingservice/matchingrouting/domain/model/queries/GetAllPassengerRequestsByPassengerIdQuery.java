package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

public record GetAllPassengerRequestsByPassengerIdQuery(
        String passengerId
) {
    public GetAllPassengerRequestsByPassengerIdQuery {
        if (passengerId.isEmpty() || passengerId.isBlank()) {
            throw new IllegalArgumentException("Passenger ID cannot be null or empty");
        }
    }
}
