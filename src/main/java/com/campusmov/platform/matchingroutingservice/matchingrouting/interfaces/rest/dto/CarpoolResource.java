package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

@Builder
public record CarpoolResource(
        String id,
        String driverId,
        String vehicleId,
        String status,
        Integer availableSeats,
        Integer maxPassengers,
        String scheduleId,
        Integer radius,
        String originName,
        String originAddress,
        String destinationName,
        String destinationAddress,
        Boolean isVisible
) {
}
