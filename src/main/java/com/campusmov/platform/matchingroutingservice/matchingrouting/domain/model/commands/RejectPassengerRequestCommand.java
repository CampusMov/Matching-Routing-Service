package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;

import lombok.Builder;

@Builder
public record RejectPassengerRequestCommand(
        String passengerRequestId
) {
}
