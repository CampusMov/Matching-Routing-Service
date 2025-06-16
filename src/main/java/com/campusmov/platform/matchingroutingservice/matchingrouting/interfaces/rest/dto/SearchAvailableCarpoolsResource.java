package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SearchAvailableCarpoolsResource(
        LocalDateTime startedClassTime,
        String originName,
        String originAddress,
        Double originLongitude,
        Double originLatitude,
        String destinationName,
        String destinationAddress,
        Double destinationLongitude,
        Double destinationLatitude,
        Integer requestedSeats,
        String classDay
) {
}