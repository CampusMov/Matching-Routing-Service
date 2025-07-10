package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities.WayPoint;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.WayPointResource;

public class WayPointResourceFromEntityAssembler {
    public static WayPointResource toResourceFromEntity(WayPoint wayPoint) {
        return WayPointResource.builder()
                .id(wayPoint.getId())
                .passengerId(wayPoint.getPassengerId().passengerId())
                .locationName(wayPoint.getLocation().getName())
                .locationAddress(wayPoint.getLocation().getAddress())
                .locationLongitude(wayPoint.getLocation().getLongitude())
                .locationLatitude(wayPoint.getLocation().getLatitude())
                .estimatedArrivalTime(wayPoint.getEstimatedArrival())
                .realArrivalTime(wayPoint.getRealArrival())
                .build();
    }
}
