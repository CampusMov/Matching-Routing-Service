package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads.PassengerRequestRejectedPayload;

public class PassengerRequestRejectedPayloadFromEntityAssembler {
    public static PassengerRequestRejectedPayload fromEntity(PassengerRequest passengerRequest) {
        return PassengerRequestRejectedPayload.builder()
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
