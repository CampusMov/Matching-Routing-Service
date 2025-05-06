package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

public record GetPassengerRequestByIdQuery(
        String passengerRequestId
) {
    public GetPassengerRequestByIdQuery {
        if (passengerRequestId.isEmpty() || passengerRequestId.isBlank()) {
            throw new IllegalArgumentException("Passenger request ID cannot be null or empty");
        }
    }
}
