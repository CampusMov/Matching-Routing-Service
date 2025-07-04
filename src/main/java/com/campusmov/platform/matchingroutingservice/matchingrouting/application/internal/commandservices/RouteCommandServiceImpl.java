package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.commandservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Route;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateRouteCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateWayPointCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities.WayPoint;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.RouteCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RouteCommandServiceImpl implements RouteCommandService {
    private final RouteRepository routeRepository;

    @Override
    public Optional<Route> handle(CreateRouteCommand command) {
        Route newRoute = new Route(command);
        try {
            routeRepository.save(newRoute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving route", e);
        }
        return Optional.of(newRoute);
    }

    @Override
    public Optional<WayPoint> handle(CreateWayPointCommand command) {
        Route route = routeRepository.findByCarpoolId(command.carpoolId())
                .orElseThrow(() -> new IllegalArgumentException("Route with carpool ID %s not found".formatted(command.carpoolId())));
        WayPoint wayPoint = route.addWayPoint(command);
        try {
            routeRepository.save(route);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving way point", e);
        }
        return Optional.of(wayPoint);
    }
}
