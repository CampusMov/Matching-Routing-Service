package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.events.transforms;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateWayPointCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events.PassengerRequestAcceptedEvent;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;

public class CreateWayPointCommandFromEventAssembler {
    public static CreateWayPointCommand toCommandFromEvent(PassengerRequestAcceptedEvent event) {
        return CreateWayPointCommand.builder()
                .carpoolId(new CarpoolId(event.getCarpoolId()))
                .passengerId(new PassengerId(event.getPassengerId()))
                .location(new Location(
                        event.getPickupLocationName(),
                        event.getPickupLocationAddress(),
                        event.getPickupLocationLongitude(),
                        event.getPickupLocationLatitude()))
                .build();
    }
}
