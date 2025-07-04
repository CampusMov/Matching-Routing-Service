package com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects;

public record RouteId(String routeId) {
    public RouteId {
        if (routeId.isBlank() || routeId.isEmpty()) {
            throw new IllegalArgumentException("Route ID cannot be null or empty");
        }
    }
}
