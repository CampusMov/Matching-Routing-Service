package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.PassengerRequestResource;

public class PassengerRequestResourceFromEntityAssembler {
    public static PassengerRequestResource toResourceFromEntity(PassengerRequest passengerRequest) {
        return PassengerRequestResource.builder()
                .id(passengerRequest.getId())
                .carpoolId(passengerRequest.getCarpoolId().carpoolId())
                .passengerId(passengerRequest.getPassengerId().passengerId())
                .pickupLocationName(passengerRequest.getPickupLocation().getName())
                .pickupLocationAddress(passengerRequest.getPickupLocation().getAddress())
                .status(passengerRequest.getStatus().toString())
                .requestedSeats(passengerRequest.getRequestedSeats())
                .build();
    }
}
