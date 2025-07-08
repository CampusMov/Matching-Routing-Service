package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads;

import lombok.Builder;

@Builder
public record PassengerRequestCreatedPayload(
        String id,
        String carpoolId,
        String passengerId,
        String pickupLocationName,
        String pickupLocationAddress,
        String status,
        Integer requestedSeats
) {
}
