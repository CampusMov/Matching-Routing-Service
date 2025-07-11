package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record CarpoolCompletedPayload(
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
        LocalTime startedClassTime,
        LocalTime endedClassTime,
        String classDay,
        Boolean isVisible
) {
}
