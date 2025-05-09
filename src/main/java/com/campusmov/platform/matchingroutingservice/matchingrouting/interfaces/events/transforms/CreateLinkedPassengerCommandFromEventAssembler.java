package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.events.transforms;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateLinkedPassengerCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.PassengerRequestAcceptedEvent;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;

public class CreateLinkedPassengerCommandFromEventAssembler {
    public static CreateLinkedPassengerCommand toCommandFromEvent(PassengerRequestAcceptedEvent event) {
        return CreateLinkedPassengerCommand.builder()
                .passengerId(new PassengerId(event.passengerId()))
                .carpoolId(new CarpoolId(event.carpoolId()))
                .requestedSeats(event.requestedSeats())
                .build();
    }
}
