package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetAllAvailableCarpoolsByScheduleIdAndPickUpLocationQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateLocationResource;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.ScheduleId;

public class GetAllAvailableCarpoolsByScheduleIdAndPickUpLocationQueryFromResourceAssembler {
    public static GetAllAvailableCarpoolsByScheduleIdAndPickUpLocationQuery toQueryFromResource(String scheduleId, CreateLocationResource pickUpLocation) {
        return GetAllAvailableCarpoolsByScheduleIdAndPickUpLocationQuery.builder()
                .scheduleId(new ScheduleId(scheduleId))
                .pickUpLocation(CreateLocationFromResourceAssembler.toCommandFromResource(pickUpLocation))
                .build();
    }
}
