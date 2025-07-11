package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.commandservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices.RouteWebSocketPublisherService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices.transform.CreateUpdateRouteCurrentLocationPayloadFromEntityAssembler;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Route;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateRouteCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateWayPointCommand;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.UpdateCurrentLocationRouteCommand;
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
    private final RouteWebSocketPublisherService routeWebSocketPublisherService;

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

    @Override
    public Optional<Route> handle(UpdateCurrentLocationRouteCommand command) {
        Route route = routeRepository.findById(command.routeId())
                .orElseThrow(() -> new IllegalArgumentException("Route with ID %s not found".formatted(command.routeId())));
        route.updateCurrentLocation(command);
        try {
            Route updateRoute = routeRepository.save(route);
            var updateRoutePayload = CreateUpdateRouteCurrentLocationPayloadFromEntityAssembler.toResourceFromEntity(updateRoute);
            routeWebSocketPublisherService.handleRouteUpdateCurrentLocation(updateRoutePayload);
            return Optional.of(updateRoute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating current location", e);
        }
    }
}
