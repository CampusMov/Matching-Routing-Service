package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.PassengerId;
import lombok.Builder;

@Builder
public record CreateLinkedPassengerCommand(
        CarpoolId carpoolId,
        PassengerId passengerId,
        Integer requestedSeats
) {
}
