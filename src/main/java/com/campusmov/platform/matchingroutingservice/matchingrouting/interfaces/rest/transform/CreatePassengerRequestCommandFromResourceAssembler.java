package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreatePassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreatePassengerRequestResource;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;

public class CreatePassengerRequestCommandFromResourceAssembler {
    public static CreatePassengerRequestCommand toCommandFromResource(CreatePassengerRequestResource resource) {
        return CreatePassengerRequestCommand.builder()
                .carpoolId(new CarpoolId(resource.carpoolId()))
                .passengerId(new PassengerId(resource.passengerId()))
                .pickupLocation(CreateLocationFromResourceAssembler.toCommandFromResource(resource.pickupLocation()))
                .requestedSeats(resource.requestedSeats())
                .build();
    }
}
