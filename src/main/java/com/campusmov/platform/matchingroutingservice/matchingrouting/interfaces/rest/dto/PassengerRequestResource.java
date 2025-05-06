package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record PassengerRequestResource(
        String id,
        String carpoolId,
        String passengerId,
        String pickupLocationName,
        String pickupLocationAddress,
        String status,
        Integer requestedSeats
) {
}
