package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

import lombok.Builder;

import java.util.Collection;

@Builder
public record RouteResource(
    Collection<IntersectionResource> intersections,
    Double totalDistance,
    Double totalDuration
) {
}
