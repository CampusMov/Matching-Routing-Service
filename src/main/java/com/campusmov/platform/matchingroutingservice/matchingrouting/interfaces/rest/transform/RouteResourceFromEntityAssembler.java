package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Intersection;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.RouteResource;

import java.util.Collection;

public class RouteResourceFromEntityAssembler {
    public static RouteResource toResourceFromEntity(Collection<Intersection> intersections,
                                                     Double totalDistance,
                                                     Double totalDuration) {
        return RouteResource.builder()
                .intersections(intersections.stream()
                        .map(IntersectionResourceFromEntityAssembler::toResourceFromEntity)
                        .toList())
                .totalDistance(totalDistance)
                .totalDuration(totalDuration)
                .build();
    }
}
