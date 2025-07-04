package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.commandservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Route;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.commands.CreateRouteCommand;
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
            throw new RuntimeException("Error saving route", e);
        }
        return Optional.of(newRoute);
    }
}
