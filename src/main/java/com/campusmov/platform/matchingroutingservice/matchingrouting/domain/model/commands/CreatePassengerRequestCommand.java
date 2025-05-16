package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import lombok.Builder;

@Builder
public record CreatePassengerRequestCommand(
        CarpoolId carpoolId,
        PassengerId passengerId,
        Location pickupLocation,
        Integer requestedSeats
) {
}
