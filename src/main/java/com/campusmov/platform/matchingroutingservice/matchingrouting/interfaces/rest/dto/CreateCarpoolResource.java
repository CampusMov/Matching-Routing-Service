package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

@Builder
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
