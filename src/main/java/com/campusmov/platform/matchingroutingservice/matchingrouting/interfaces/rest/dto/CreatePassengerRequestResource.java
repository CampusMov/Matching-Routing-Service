package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record CreatePassengerRequestResource(
        String carpoolId,
        String passengerId,
        CreateLocationResource pickupLocation,
        Integer requestedSeats
) {
}
