package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.queryservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Route;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetAllWayPointsByRouteIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetRouteByCarpoolId;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetRouteByIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities.WayPoint;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.RouteQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.jpa.repositories.RouteRepository;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.CarpoolId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RouteQueryServiceImpl implements RouteQueryService {
    private final RouteRepository routeRepository;

    @Override
    public Optional<Route> handle(GetRouteByIdQuery query) {
        return routeRepository.findById(query.routeId());
    }

    @Override
    public Optional<Route> handle(GetRouteByCarpoolId query) {
        return routeRepository.findByCarpoolId(new CarpoolId(query.carpoolId()));
    }

    @Override
    public Collection<WayPoint> handle(GetAllWayPointsByRouteIdQuery query) {
        Route route = routeRepository.findById(query.routeId())
                .orElseThrow(() -> new IllegalArgumentException("Route with ID %s not found".formatted(query.routeId())));
        return route.getWayPoints();
    }
}
