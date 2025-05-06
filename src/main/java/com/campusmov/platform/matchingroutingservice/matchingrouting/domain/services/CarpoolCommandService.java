package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Carpool;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateCarpoolCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.StartCarpoolCommand;

import java.util.Optional;

public interface CarpoolCommandService {
    Optional<Carpool> handle(CreateCarpoolCommand command);
    Optional<Carpool> handle(StartCarpoolCommand command);
}
