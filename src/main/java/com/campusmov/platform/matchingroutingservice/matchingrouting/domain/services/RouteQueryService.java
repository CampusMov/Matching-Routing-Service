package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Route;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetAllWayPointsByRouteIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetRouteByCarpoolId;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetRouteByIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities.WayPoint;

import java.util.Collection;
import java.util.Optional;

public interface RouteQueryService {
    Optional<Route> handle(GetRouteByIdQuery query);
    Optional<Route> handle(GetRouteByCarpoolId query);
    Collection<WayPoint> handle(GetAllWayPointsByRouteIdQuery query);
}
