package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record SearchAvailableCarpoolsResource(
        LocalTime startedClassTime,
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