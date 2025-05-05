package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

public record CreateCarpoolResource(
        String driverId,
        String vehicleId,
        Integer maxPassengers,
        String scheduleId,
        Integer radius,
        CreateLocationResource origin,
        CreateLocationResource destination
) {
}
