package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.ScheduleId;
import lombok.Builder;

@Builder
public record GetAllAvailableCarpoolsByScheduleIdAndPickUpLocationQuery(
        ScheduleId scheduleId,
        Location pickUpLocation
) {
}
