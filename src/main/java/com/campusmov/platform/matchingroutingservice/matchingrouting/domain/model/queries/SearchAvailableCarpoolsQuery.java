package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.EDay;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SearchAvailableCarpoolsQuery(
        LocalDateTime startedClassTime,
        EDay classDay,
        Location origin,
        Location destination,
        Integer requestedSeats
) {
}
