package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events;

import lombok.Builder;

@Builder
public record PassengerRequestAcceptedEvent(
        String passengerRequestId,
        String carpoolId,
        String passengerId,
        String pickupLocationName,
        String pickupLocationAddress,
        Double pickupLocationLongitude,
        Double pickupLocationLatitude,
        Integer requestedSeats,
        String status
) implements PassengerRequestEvent {
}