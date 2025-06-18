package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EDay;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record SearchAvailableCarpoolsQuery(
        LocalTime startedClassTime,
        EDay classDay,
        Location origin,
        Location destination,
        Integer requestedSeats
) {
}
