package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

import java.time.LocalDateTime;

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
        Double originLongitude,
        Double originLatitude,
        String destinationName,
        String destinationAddress,
        Double destinationLongitude,
        Double destinationLatitude,
        LocalDateTime startedClassTime,
        LocalDateTime endedClassTime,
        String classDay,
        Boolean isVisible
) {
}
