package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CarpoolResource;

public class CarpoolResourceFromEntityAssembler {
    public static CarpoolResource toResourceFromEntity(Carpool entity) {
        return CarpoolResource.builder().
                id(entity.getId()).
                driverId(entity.getDriverId().driverId()).
                vehicleId(entity.getVehicleId().vehicleId()).
                status(entity.getStatus().toString()).
                availableSeats(entity.getAvailableSeats()).
                maxPassengers(entity.getMaxPassengers()).
                scheduleId(entity.getScheduleId().scheduleId()).
                radius(entity.getRadius()).
                originName(entity.getOrigin().getName()).
                originAddress(entity.getOrigin().getAddress()).
                originLatitude(entity.getOrigin().getLatitude()).
                originLongitude(entity.getOrigin().getLongitude()).
                destinationName(entity.getDestination().getName()).
                destinationAddress(entity.getDestination().getAddress()).
                destinationLatitude(entity.getDestination().getLatitude()).
                destinationLongitude(entity.getDestination().getLongitude()).
                startedClassTime(entity.getStartedClassTime()).
                endedClassTime(entity.getEndedClassTime()).
                classDay(entity.getClassDay().toString()).
                isVisible(entity.getIsVisible()).
                build();
    }
}
