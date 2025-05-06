package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands;

import lombok.Builder;

@Builder
public record AcceptPassengerRequestCommand(
        String passengerRequestId
) {
}