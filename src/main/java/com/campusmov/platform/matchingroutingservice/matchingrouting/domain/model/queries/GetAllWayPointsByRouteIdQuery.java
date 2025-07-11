package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries;

public record GetAllWayPointsByRouteIdQuery(
        String routeId
) {
    public GetAllWayPointsByRouteIdQuery {
        if (routeId == null || routeId.isBlank()) {
            throw new IllegalArgumentException("Route ID cannot be null or blank");
        }
    }
}
