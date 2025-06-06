package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.PassengerRequest;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.AcceptPassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreatePassengerRequestCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.RejectPassengerRequestCommand;

import java.util.Optional;

public interface PassengerRequestCommandService {
    Optional<PassengerRequest> handle(CreatePassengerRequestCommand command);
    Optional<PassengerRequest> handle(AcceptPassengerRequestCommand command);
    Optional<PassengerRequest> handle(RejectPassengerRequestCommand command);
}
