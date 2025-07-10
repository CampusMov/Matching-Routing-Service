package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;
import org.joda.time.DateTime;

@Builder
public record WayPointResource(
        String id,
        String passengerId,
        String locationName,
        String locationAddress,
        Double locationLongitude,
        Double locationLatitude,
        DateTime estimatedArrivalTime,
        DateTime realArrivalTime
) {
}
