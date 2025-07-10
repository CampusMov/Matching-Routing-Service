package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Route;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateRouteCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateWayPointCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.UpdateCurrentLocationRouteCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities.WayPoint;

import java.util.Optional;

public interface RouteCommandService {
    Optional<Route> handle(CreateRouteCommand command);
    Optional<WayPoint> handle(CreateWayPointCommand command);
    Optional<Route> handle(UpdateCurrentLocationRouteCommand command);
}
