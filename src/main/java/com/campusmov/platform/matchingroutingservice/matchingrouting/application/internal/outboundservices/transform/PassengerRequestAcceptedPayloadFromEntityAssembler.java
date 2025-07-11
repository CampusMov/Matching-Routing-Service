package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads.PassengerRequestAcceptedPayload;

public class PassengerRequestAcceptedPayloadFromEntityAssembler {
    public static PassengerRequestAcceptedPayload fromEntity(PassengerRequest passengerRequest) {
        return PassengerRequestAcceptedPayload.builder()
                .id(passengerRequest.getId())
                .carpoolId(passengerRequest.getCarpoolId().carpoolId())
                .passengerId(passengerRequest.getPassengerId().passengerId())
                .pickupLocationName(passengerRequest.getPickupLocation().getName())
                .pickupLocationAddress(passengerRequest.getPickupLocation().getAddress())
                .status(passengerRequest.getStatus().name())
                .requestedSeats(passengerRequest.getRequestedSeats())
                .build();
    }
}
