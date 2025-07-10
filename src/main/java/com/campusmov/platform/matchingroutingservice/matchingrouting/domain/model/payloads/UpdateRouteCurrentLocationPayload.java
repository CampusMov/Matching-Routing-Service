package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads;

import lombok.Builder;
import org.joda.time.DateTime;

@Builder
public record UpdateRouteCurrentLocationPayload(
        String id,
        String carpoolId,
        DateTime realStartedTime,
        DateTime estimatedEndedTime,
        Double estimatedDurationMinutes,
        Double estimatedDistanceKm,
        String originName,
        String originAddress,
        Double originLongitude,
        Double originLatitude,
        String destinationName,
        String destinationAddress,
        Double destinationLongitude,
        Double destinationLatitude,
        String carpoolCurrentName,
        String carpoolCurrentAddress,
        Double carpoolCurrentLongitude,
        Double carpoolCurrentLatitude
) {
}
